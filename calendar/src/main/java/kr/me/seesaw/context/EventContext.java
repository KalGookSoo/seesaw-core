package kr.me.seesaw.context;

import org.springframework.security.acls.domain.BasePermission;

public interface EventContext {

    boolean hasPermission(String eventId, BasePermission permission);

    boolean isOwner(String eventId);

}
