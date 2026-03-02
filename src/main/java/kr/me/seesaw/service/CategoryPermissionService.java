package kr.me.seesaw.service;

import org.springframework.security.acls.model.Permission;

public interface CategoryPermissionService {

    boolean hasPermission(String id, Permission permission);

}
