package kr.me.seesaw.service;

import kr.me.seesaw.core.authentication.PrincipalProvider;
import kr.me.seesaw.domain.Category;
import kr.me.seesaw.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DefaultCategoryPermissionService implements CategoryPermissionService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PrincipalProvider principalProvider;

    private final CategoryRepository categoryRepository;

    private final PermissionEvaluator permissionEvaluator;

    @Override
    public boolean hasPermission(String categoryId, Permission permission) {
        logger.debug("인가를 검증합니다. categoryId: {}, permission: {}", categoryId, permission);
        Category category = categoryRepository.getReferenceById(categoryId);
        return permissionEvaluator.hasPermission(principalProvider.getAuthentication(), category.getId(), Category.class.getCanonicalName(), permission);
    }

}
