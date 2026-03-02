package kr.me.seesaw.service;

import org.springframework.security.acls.domain.BasePermission;

public interface EventPermissionService {

    boolean hasPermission(String eventId, BasePermission permission);

    boolean isOwner(String eventId);

}
