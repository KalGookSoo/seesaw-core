package kr.me.seesaw.service;

import org.springframework.security.acls.model.Permission;

import java.util.Collection;

public interface AttachmentPermissionService {

    boolean hasPermission(String id, Collection<String> authorities, Permission permission);

}
