package kr.me.seesaw.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.acls.model.Permission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DefaultCategoryPermissionService implements CategoryPermissionService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public boolean hasPermission(String id, Permission permission) {
        logger.debug("인가를 검증합니다. categoryId: {}", id);
        return false;
    }

}
