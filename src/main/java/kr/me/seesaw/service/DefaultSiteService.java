package kr.me.seesaw.service;

import kr.me.seesaw.command.CreateSiteCommand;
import kr.me.seesaw.core.file.FileIOService;
import kr.me.seesaw.domain.*;
import kr.me.seesaw.model.*;
import kr.me.seesaw.repository.ArticleQueryRepository;
import kr.me.seesaw.repository.AttachmentRepository;
import kr.me.seesaw.repository.SiteRepository;
import kr.me.seesaw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Service
public class DefaultSiteService implements SiteService {

    private final String filepath;

    private final SiteRepository siteRepository;

    private final AttachmentRepository attachmentRepository;

    private final ArticleQueryRepository articleQueryRepository;

    private final UserRepository userRepository;

    public DefaultSiteService(
            @Value("${kr.me.seesaw.filepath}") String filepath,
            SiteRepository siteRepository,
            AttachmentRepository attachmentRepository,
            ArticleQueryRepository articleQueryRepository,
            UserRepository userRepository
    ) {
        this.filepath = filepath;
        this.siteRepository = siteRepository;
        this.attachmentRepository = attachmentRepository;
        this.articleQueryRepository = articleQueryRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public SiteModel getSiteById(String id) {
        return siteRepository.findById(id)
                .map(SiteModel::new)
                .orElseThrow(() -> new NoSuchElementException("사이트를 찾을 수 없습니다. id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public SiteModel getSiteByDomainName(String domainName) {
        return siteRepository.findByDomainName(domainName)
                .map(SiteModel::new)
                .orElseThrow(() -> new NoSuchElementException("사이트를 찾을 수 없습니다. domainName: " + domainName));
    }

    @Transactional(readOnly = true)
    @Override
    public SiteModel getSiteContext(String domainName) {
        Site site = siteRepository.findByDomainName(domainName)
                .orElseThrow(() -> new NoSuchElementException("사이트를 찾을 수 없습니다. domainName: " + domainName));
        SiteModel siteModel = new SiteModel(site);

        // 프로필 이미지, 배경 이미지 조인
        attachmentRepository.findAllByReferenceIdIn(Collections.singletonList(site.getId()))
                .stream()
                .map(AttachmentModel::new)
                .forEach(siteModel::addAttachment);

        // 카테고리 조인
        site.getCategories()
                .stream()
                .filter(Category::isExposed)
                .sorted(Comparator.comparing(Category::getSequence))
                .map(CategoryModel::new)
                .forEach(siteModel::addCategory);

        // 최근 7일 게시글 조인
        List<String> categoryIds = siteModel.getCategories()
                .stream()
                .map(CategoryModel::getId)
                .toList();
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(7);

        List<ArticleModel> articles = articleQueryRepository.findAllByCategoryId(categoryIds, cutoffDate)
                .stream()
                .map(ArticleModel::new)
                .sorted(Comparator.comparing(BaseModel::getCreatedDate))
                .toList();
        siteModel.getCategories()
                .forEach(categoryModel -> categoryModel.joinArticles(articles));

        Map<String, CategoryModel> allCategories = siteModel.getCategories()
                .stream()
                .collect(Collectors.toMap(CategoryModel::getId, Function.identity()));

        // 최근 게시글을 병합하여 상위 카테고리 게시글에 바인딩
        siteModel.getCategories()
                .stream()
                .filter(CategoryModel::isRoot)
                .forEach(categoryModel -> articles.stream()
                        .filter(article -> categoryModel.getId().equals(allCategories.get(article.getCategoryId()).getParentId()))
                        .forEach(categoryModel::addRecentArticle));

        // 최근 게시글을 해당 카테고리에도 바인딩
        articles.forEach(article -> {
            CategoryModel category = allCategories.get(article.getCategoryId());
            if (category != null) {
                category.addRecentArticle(article);
            }
        });

        return siteModel;
    }

    @Transactional(readOnly = true)
    @Override
    public List<SiteModel> getOwnSites(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("계정을 찾을 수 없습니다. username: " + username));

        // 계정 식별자로 roleMappingRepository.findAllByUserId로 계정에 매핑된 siteId 목록 추출
        List<String> siteIds = user.getRoleMappings()
                .stream()
                .map(RoleMapping::getSite)
                .map(Site::getId)
                .toList();

        return siteRepository.findAllByIdIn(siteIds)
                .stream()
                .map(SiteModel::new)
                .toList();
    }

    @Override
    public SiteModel createSite(CreateSiteCommand command) throws IOException {
        Site site = new Site();
        site.setName(command.getName());
        site.setDomainName(command.getDomainName());
        site.setDescription(command.getDescription());
        site.setDistributionCode(command.getDistributionCode());
        site.setSearchEngineExposed(command.isSearchEngineExposed());
        site.setImageExposed(command.isImageExposed());
        site.setTags(command.getTags());
        site.setAddress(command.getAddress());
        site.setContactNumber(command.getContactNumber());
        site.setIntro(command.getIntro());
        site.setContent(command.getContent());

        siteRepository.save(site);

        SiteModel siteModel = new SiteModel(site);

        // 프로필 이미지
        if (command.hasProfileImage()) {
            // 쓰기
            Attachment attachment = new Attachment();
            attachment.setReferenceId(site.getId());
            attachment.setOriginalName(command.getProfileImage().getOriginalFilename());
            attachment.setName(UUID.randomUUID() + "_" + attachment.getOriginalName());
            attachment.setPathName(Attachment.Type.INLINE_IMAGE.getPath());
            attachment.setMimeType(command.getProfileImage().getContentType());
            attachment.setSize(command.getProfileImage().getSize());

            writeFile(filepath + attachment.getPathName() + File.separator + attachment.getName(), command.getProfileImage().getBytes());

            // 영속화
            attachmentRepository.save(attachment);
            siteModel.addAttachment(new AttachmentModel(attachment));
        }

        // 배경 이미지
        if (command.hasBackgroundImage()) {
            // 쓰기
            Attachment attachment = new Attachment();
            attachment.setReferenceId(site.getId());
            attachment.setOriginalName(command.getBackgroundImage().getOriginalFilename());
            attachment.setName(UUID.randomUUID() + "_" + attachment.getOriginalName());
            attachment.setPathName(Attachment.Type.BACKGROUND_IMAGE.getPath());
            attachment.setMimeType(command.getBackgroundImage().getContentType());
            attachment.setSize(command.getBackgroundImage().getSize());

            writeFile(filepath + attachment.getPathName() + File.separator + attachment.getName(), command.getBackgroundImage().getBytes());

            // 영속화
            attachmentRepository.save(attachment);
            siteModel.addAttachment(new AttachmentModel(attachment));
        }
        return siteModel;
    }

    @Override
    public SiteModel updateSite(String id, CreateSiteCommand command) throws IOException {
        Site site = siteRepository.getReferenceById(id);
        site.setName(command.getName());
        site.setDomainName(command.getDomainName());
        site.setDescription(command.getDescription());
        site.setDistributionCode(command.getDistributionCode());
        site.setSearchEngineExposed(command.isSearchEngineExposed());
        site.setImageExposed(command.isImageExposed());
        site.setTags(command.getTags());
        site.setAddress(command.getAddress());
        site.setContactNumber(command.getContactNumber());
        site.setIntro(command.getIntro());
        site.setContent(command.getContent());

        siteRepository.save(site);
        SiteModel siteModel = new SiteModel(site);

        // 기존 파일 조회
        List<Attachment> attachments = attachmentRepository.findAllByReferenceIdIn(Collections.singletonList(site.getId()));

        // 프로필 이미지
        if (command.hasProfileImage()) {
            // 기존 첨부파일 제거
            List<Attachment> profileImages = attachments.stream().filter(attachment -> Attachment.Type.PROFILE.getPath().equals(attachment.getPathName())).toList();
            attachmentRepository.deleteAllInBatch(profileImages);
            attachments.stream().map(attachment -> filepath + attachment.getPathName() + File.separator + attachment.getName()).forEach(FileIOService::delete);

            // 쓰기
            Attachment attachment = new Attachment();
            attachment.setReferenceId(site.getId());
            attachment.setOriginalName(command.getProfileImage().getOriginalFilename());
            attachment.setName(UUID.randomUUID() + "_" + attachment.getOriginalName());
            attachment.setPathName(Attachment.Type.INLINE_IMAGE.getPath());
            attachment.setMimeType(command.getProfileImage().getContentType());
            attachment.setSize(command.getProfileImage().getSize());

            writeFile(filepath + attachment.getPathName() + File.separator + attachment.getName(), command.getProfileImage().getBytes());

            // 영속화
            attachmentRepository.save(attachment);
            siteModel.addAttachment(new AttachmentModel(attachment));
        }

        // 배경 이미지
        if (command.hasBackgroundImage()) {
            // 기존 첨부파일 제거
            List<Attachment> backgroundImages = attachments.stream().filter(attachment -> Attachment.Type.BACKGROUND_IMAGE.getPath().equals(attachment.getPathName())).toList();
            attachmentRepository.deleteAllInBatch(backgroundImages);
            attachments.stream().map(attachment -> filepath + attachment.getPathName() + File.separator + attachment.getName()).forEach(FileIOService::delete);

            // 쓰기
            Attachment attachment = new Attachment();
            attachment.setReferenceId(site.getId());
            attachment.setOriginalName(command.getBackgroundImage().getOriginalFilename());
            attachment.setName(UUID.randomUUID() + "_" + attachment.getOriginalName());
            attachment.setPathName(Attachment.Type.BACKGROUND_IMAGE.getPath());
            attachment.setMimeType(command.getBackgroundImage().getContentType());
            attachment.setSize(command.getBackgroundImage().getSize());

            writeFile(filepath + attachment.getPathName() + File.separator + attachment.getName(), command.getBackgroundImage().getBytes());

            // 영속화
            attachmentRepository.save(attachment);
            siteModel.addAttachment(new AttachmentModel(attachment));
        }
        return siteModel;
    }

    @Override
    public void deleteSite(String id) {
        // 첨부파일 삭제
        List<Attachment> attachments = attachmentRepository.findAllByReferenceIdIn(Collections.singletonList(id));
        attachmentRepository.deleteAllInBatch(attachments);
        attachments.stream().map(attachment -> filepath + attachment.getPathName() + File.separator + attachment.getName()).forEach(FileIOService::delete);

        siteRepository.deleteById(id);
    }

    private void writeFile(String pathname, byte[] bytes) {
        try {
            FileIOService.write(pathname, bytes);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
