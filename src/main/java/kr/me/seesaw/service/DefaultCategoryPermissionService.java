package kr.me.seesaw.service;

import org.springframework.security.acls.model.Permission;

public class DefaultCategoryPermissionService implements CategoryPermissionService {

    @Override
    public boolean hasPermission(String id, Permission permission) {
        return false;
    }

}
