package kr.me.seesaw.service;

import kr.me.seesaw.domain.Article;
import kr.me.seesaw.domain.Attachment;
import kr.me.seesaw.repository.ArticleRepository;
import kr.me.seesaw.repository.AttachmentRepository;
import kr.me.seesaw.repository.PermissionRepository;
import kr.me.seesaw.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DefaultAttachmentPermissionService implements AttachmentPermissionService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ArticleRepository articleRepository;

    private final AttachmentRepository attachmentRepository;

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    @Override
    public boolean hasPermission(String id, Collection<String> authorities, Permission requiredPermission) {
        logger.debug("인가를 검증합니다. attachmentId: {}, permission: {}", id, requiredPermission);

        return Optional.ofNullable(authorities)
                .filter(auths -> !auths.isEmpty() && requiredPermission != null)
                .map(auths -> {
                    Attachment attachment = attachmentRepository.getReferenceById(id);
                    Article article = articleRepository.getReferenceById(attachment.getReferenceId());
                    String targetId = article.getCategory().getId();
                    int requiredMask = requiredPermission.getMask();

                    return auths.stream().anyMatch(roleName -> roleRepository.findByName(roleName)
                            .flatMap(role -> permissionRepository.findByRoleIdAndTargetId(role.getId(), targetId))
                            .map(permission -> (permission.getMask() & requiredMask) == requiredMask)
                            .orElse(false));
                })
                .orElse(false);
    }

}
