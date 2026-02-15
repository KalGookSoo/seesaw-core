package kr.me.seesaw.service;

import kr.me.seesaw.command.CreateArticleCommand;
import kr.me.seesaw.command.UpdateArticleCommand;
import kr.me.seesaw.core.authentication.PrincipalProvider;
import kr.me.seesaw.core.file.FileIOService;
import kr.me.seesaw.domain.*;
import kr.me.seesaw.model.ArticleModel;
import kr.me.seesaw.model.AttachmentModel;
import kr.me.seesaw.model.ReplyModel;
import kr.me.seesaw.model.ViewModel;
import kr.me.seesaw.repository.*;
import kr.me.seesaw.search.ArticleSearch;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Transactional
@Service
public class DefaultArticleService implements ArticleService {

    @Value("${kr.me.seesaw.filepath}")
    private final String filepath;

    private final ArticleRepository articleRepository;

    private final ArticleQueryRepository articleQueryRepository;

    private final ReplyRepository replyRepository;

    private final ViewRepository viewRepository;

    private final AttachmentRepository attachmentRepository;

    private final PrincipalProvider principalProvider;

    public DefaultArticleService(
            @Value("${kr.me.seesaw.filepath}") String filepath,
            ArticleRepository articleRepository,
            ArticleQueryRepository articleQueryRepository,
            ReplyRepository replyRepository,
            ViewRepository viewRepository,
            AttachmentRepository attachmentRepository,
            PrincipalProvider principalProvider
    ) {
        this.filepath = filepath;
        this.articleRepository = articleRepository;
        this.articleQueryRepository = articleQueryRepository;
        this.replyRepository = replyRepository;
        this.viewRepository = viewRepository;
        this.attachmentRepository = attachmentRepository;
        this.principalProvider = principalProvider;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ArticleModel> findAll(Pageable pageable, ArticleSearch search) {
        String sort = null;
        if (pageable.getSort().isSorted()) {
            sort = pageable.getSort().toString().replace(":", "");
        }

        List<Article> articles = articleQueryRepository.search((int) pageable.getOffset(), pageable.getPageSize(), sort, search);
        long count = articleQueryRepository.count(search);

        Page<Article> entityPage = new org.springframework.data.domain.PageImpl<>(articles, pageable, count);
        Page<ArticleModel> page = entityPage.map(ArticleModel::new);

        // 페이지 요청이 아닐 경우 조인하지 않는다.
        if (pageable.isUnpaged()) {
            return page;
        }

        List<String> articleIds = page.getContent()
                .stream()
                .map(ArticleModel::getId)
                .toList();

        List<ReplyModel> replies = replyRepository.findAllByArticleIdIn(articleIds)
                .stream()
                .sorted(Comparator.comparing(BaseEntity::getCreatedDate))
                .map(ReplyModel::new)
                .toList();
        page.getContent()
                .forEach(article -> article.joinReplies(replies));

        List<ViewModel> views = viewRepository.findAllByArticleIdIn(articleIds)
                .stream()
                .sorted(Comparator.comparing(BaseEntity::getCreatedDate))
                .map(ViewModel::new)
                .toList();
        page.getContent()
                .forEach(article -> article.joinViews(views));

        List<AttachmentModel> attachments = attachmentRepository.findAllByReferenceIdIn(articleIds)
                .stream()
                .sorted(Comparator.comparing(BaseEntity::getCreatedDate))
                .map(AttachmentModel::new)
                .toList();
        page.getContent()
                .forEach(article -> article.joinAttachments(attachments));
        return page;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ArticleModel> findAllByCategoryId(String categoryId, Pageable pageable) {
        Page<Article> entityPage = articleRepository.findAllByCategoryId(categoryId, pageable);
        Page<ArticleModel> page = entityPage.map(ArticleModel::new);

        // 페이지 요청이 아닐 경우 조인하지 않는다.
        if (pageable.isUnpaged()) {
            return page;
        }
        List<String> articleIds = page.getContent().stream().map(ArticleModel::getId).toList();

        List<ReplyModel> replies = replyRepository.findAllByArticleIdIn(articleIds)
                .stream()
                .sorted(Comparator.comparing(BaseEntity::getCreatedDate))
                .map(ReplyModel::new)
                .toList();
        page.getContent().forEach(article -> article.joinReplies(replies));

        List<ViewModel> views = viewRepository.findAllByArticleIdIn(articleIds)
                .stream()
                .sorted(Comparator.comparing(BaseEntity::getCreatedDate))
                .map(ViewModel::new)
                .toList();
        page.getContent().forEach(article -> article.joinViews(views));

        List<AttachmentModel> attachments = attachmentRepository.findAllByReferenceIdIn(articleIds)
                .stream()
                .sorted(Comparator.comparing(BaseEntity::getCreatedDate))
                .map(AttachmentModel::new)
                .toList();
        page.getContent().forEach(article -> article.joinAttachments(attachments));
        return page;
    }

    @Transactional(readOnly = true)
    @Override
    public ArticleModel find(String id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 게시글이 존재하지 않습니다."));
        return new ArticleModel(article);
    }

    @Transactional(readOnly = true)
    public ArticleModel getArticleAggregation(String id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 게시글이 존재하지 않습니다."));
        ArticleModel model = new ArticleModel(article);

        List<ReplyModel> replies = replyRepository.findAllByArticleIdIn(Collections.singletonList(id))
                .stream()
                .sorted(Comparator.comparing(BaseEntity::getCreatedDate))
                .map(ReplyModel::new)
                .toList();
        model.joinReplies(replies);

        List<ViewModel> views = viewRepository.findAllByArticleIdIn(Collections.singletonList(id))
                .stream()
                .sorted(Comparator.comparing(BaseEntity::getCreatedDate))
                .map(ViewModel::new)
                .toList();
        model.joinViews(views);

        List<AttachmentModel> attachments = attachmentRepository.findAllByReferenceIdIn(Collections.singletonList(id))
                .stream()
                .sorted(Comparator.comparing(BaseEntity::getCreatedDate))
                .map(AttachmentModel::new)
                .toList();
        model.joinAttachments(attachments);
        return model;
    }

    @Override
    public ArticleModel create(CreateArticleCommand command) throws IOException {

        // 생성될 게시글의 식별자를 참조하기위해 먼저 게시글을 저장한다.
        Article article = new Article();
        Category category = new Category();
        category.setId(command.getCategoryId());
        article.setCategory(category);
        article.setType(command.getType());
        article.setFixed(command.isFixed());
        article.setFixedOrder(command.getFixedOrder());
        article.setTitle(command.getTitle());
        article.setContent(command.getContent());

        Article savedArticle = articleRepository.save(article);

        Document document = Jsoup.parse(command.getContent());
        Iterator<Element> iterator = document.select("img[src*=\"blob:\"]").iterator();

        for (MultipartFile multipartFile : command.getInlineImages()) {
            Attachment attachment = new Attachment();
            attachment.setReferenceId(savedArticle.getId());
            attachment.setOriginalName(multipartFile.getOriginalFilename());
            attachment.setName(UUID.randomUUID() + "_" + attachment.getOriginalName());
            attachment.setPathName(Attachment.Type.INLINE_IMAGE.getPath());
            attachment.setMimeType(multipartFile.getContentType());
            attachment.setSize(multipartFile.getSize());

            writeFile(filepath + attachment.getPathName() + File.separator + attachment.getName(), multipartFile.getBytes());
            attachmentRepository.save(attachment);

            String url = "/api/attachments/" + attachment.getId();

            // images의 src를 첨부파일을 생성 후 "/api/attachments/{id}"로 치환한다.
            if (iterator.hasNext()) {
                Element element = iterator.next();
                element.attr("src", url);
            }
        }

        // 첨부파일
        for (MultipartFile multipartFile : command.getMultipartFiles()) {
            Attachment attachment = new Attachment();
            attachment.setReferenceId(savedArticle.getId());
            attachment.setOriginalName(multipartFile.getOriginalFilename());
            attachment.setName(UUID.randomUUID() + "_" + attachment.getOriginalName());
            attachment.setPathName(Attachment.Type.ATTACHMENT.getPath());
            attachment.setMimeType(multipartFile.getContentType());
            attachment.setSize(multipartFile.getSize());

            writeFile(filepath + attachment.getPathName() + File.separator + attachment.getName(), multipartFile.getBytes());
            attachmentRepository.save(attachment);
        }

        // 인라인이미지 링크를 첨부파일 API로 치환한 본문으로 재할당한다
        Safelist safelist = Safelist.relaxed().preserveRelativeLinks(true);
        article.setContent(Jsoup.clean(document.body().html(), "http://localhost", safelist));
        return new ArticleModel(articleRepository.save(article));
    }

    @Override
    public ArticleModel update(String id, UpdateArticleCommand command) throws IOException {
        Article article = articleRepository.getReferenceById(id);

        Document existingContent = Jsoup.parse(article.getContent());
        Elements existingImages = existingContent.select("img[src*=\"/api/attachments/\"]");

        List<String> deletedAttachmentIds = new ArrayList<>();

        Document newContent = Jsoup.parse(command.getContent());
        Elements remainingImages = newContent.select("img[src*=\"/api/attachments/\"]");

        // src는 "/"로 스플릿하여 마지막 요소를 uuid4 패턴의 문자열이다.
        for (Element existingImage : existingImages) {
            String existingSrc = existingImage.attr("src");
            boolean isPresentInNewImages = remainingImages.stream()
                    .anyMatch(newImage -> newImage.attr("src").equals(existingSrc));
            if (!isPresentInNewImages) {
                deletedAttachmentIds.add(existingSrc.substring(existingSrc.lastIndexOf("/") + 1));
            }
        }

        // 수정하면서 삭제한 이미지를 삭제
        List<Attachment> attachments = attachmentRepository.findAllByIdIn(deletedAttachmentIds);
        attachmentRepository.deleteAllInBatch(attachments);
        attachments.stream().map(attachment -> filepath + attachment.getPathName() + File.separator + attachment.getName()).forEach(FileIOService::delete);

        Iterator<Element> iterator = newContent.select("img[src*=\"blob:\"]").iterator();

        for (MultipartFile multipartFile : command.getInlineImages()) {
            Attachment attachment = new Attachment();
            attachment.setReferenceId(article.getId());
            attachment.setOriginalName(multipartFile.getOriginalFilename());
            attachment.setName(UUID.randomUUID() + "_" + attachment.getOriginalName());
            attachment.setPathName(Attachment.Type.INLINE_IMAGE.getPath());
            attachment.setMimeType(multipartFile.getContentType());
            attachment.setSize(multipartFile.getSize());

            writeFile(filepath + attachment.getPathName() + File.separator + attachment.getName(), multipartFile.getBytes());
            attachmentRepository.save(attachment);

            String url = "/api/attachments/" + attachment.getId();

            // images의 src를 첨부파일을 생성 후 "/api/attachments/{id}"로 치환한다.
            if (iterator.hasNext()) {
                Element element = iterator.next();
                element.attr("src", url);
            }
        }

        Safelist safelist = Safelist.relaxed().preserveRelativeLinks(true);
        command.setContent(Jsoup.clean(newContent.body().html(), "http://localhost", safelist));
        
        Category category = new Category();
        category.setId(command.getCategoryId());
        article.setCategory(category);
        article.setType(command.getType());
        article.setFixed(command.isFixed());
        article.setFixedOrder(command.getFixedOrder());
        article.setTitle(command.getTitle());
        article.setContent(command.getContent());

        // 첨부파일
        for (MultipartFile multipartFile : command.getMultipartFiles()) {
            Attachment attachment = new Attachment();
            attachment.setReferenceId(article.getId());
            attachment.setOriginalName(multipartFile.getOriginalFilename());
            attachment.setName(UUID.randomUUID() + "_" + attachment.getOriginalName());
            attachment.setPathName(Attachment.Type.ATTACHMENT.getPath());
            attachment.setMimeType(multipartFile.getContentType());
            attachment.setSize(multipartFile.getSize());

            writeFile(filepath + attachment.getPathName() + File.separator + attachment.getName(), multipartFile.getBytes());
            attachmentRepository.save(attachment);
        }

        return new ArticleModel(articleRepository.save(article));
    }

    @Override
    public void delete(String id) {
        Article article = articleRepository.getReferenceById(id);
        List<Reply> replies = replyRepository.findAllByArticleIdIn(Collections.singletonList(article.getId()));
        replyRepository.deleteAllInBatch(replies);
        List<View> views = viewRepository.findAllByArticleIdIn(Collections.singletonList(article.getId()));
        viewRepository.deleteAllInBatch(views);
        List<Attachment> attachments = attachmentRepository.findAllByReferenceIdIn(Collections.singletonList(article.getId()));
        attachmentRepository.deleteAllInBatch(attachments);
        attachments.stream()
                .map(attachment -> filepath + attachment.getPathName() + File.separator + attachment.getName())
                .forEach(FileIOService::delete);

        articleRepository.delete(article);
    }

    @Override
    public void deleteAll(List<String> ids) {
        ids.forEach(this::delete);
    }

    @Override
    public boolean isOwner(String id, String username) {
        ArticleModel article = find(id);
        return article.getCreatedBy().equals(username);
    }

    @Override
    public List<ArticleModel> getFixedArticles(String categoryId, boolean fixed, Sort sort) {
        return articleRepository.findAllByCategoryIdAndFixed(categoryId, fixed, sort)
                .stream()
                .map(ArticleModel::new)
                .toList();
    }

    @Nullable
    @Override
    public ArticleModel getPreviousArticle(ArticleSearch search, LocalDateTime createdDate) {
        return articleQueryRepository.findFirstNext(search, createdDate, "DESC").map(ArticleModel::new).orElse(null);
    }

    @Nullable
    @Override
    public ArticleModel getNextArticle(ArticleSearch search, LocalDateTime createdDate) {
        return articleQueryRepository.findFirstNext(search, createdDate, "ASC").map(ArticleModel::new).orElse(null);
    }

    private void writeFile(String pathname, byte[] bytes) {
        try {
            FileIOService.write(pathname, bytes);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void increaseView(String articleId) {
        View view = new View();
        Article article = new Article();
        article.setId(articleId);
        view.setArticle(article);
        
        Object principal = principalProvider.getAuthentication().getPrincipal();
        // 동일인물 중복 조회수 불허
        List<View> views = viewRepository.findAllByArticleIdIn(Collections.singletonList(articleId));
    }

}
