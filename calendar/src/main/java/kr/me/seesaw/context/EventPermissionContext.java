package kr.me.seesaw.context;

import kr.me.seesaw.core.support.authentication.PrincipalProvider;
import kr.me.seesaw.core.domain.article.Article;
import kr.me.seesaw.core.domain.category.Category;
import kr.me.seesaw.core.domain.event.VEvent;
import kr.me.seesaw.core.domain.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service("eventContext")
public class EventPermissionContext implements EventContext {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PrincipalProvider principalProvider;

    private final PermissionEvaluator permissionEvaluator;

    private final EventRepository eventRepository;

    @Override
    public boolean hasPermission(String eventId, BasePermission permission) {
        logger.debug("인가를 검증합니다. eventId: {}, permission: {}", eventId, permission);
        VEvent event = eventRepository.getReferenceById(eventId);
        Article article = event.getArticle();
        Category category = article.getCategory();
        return permissionEvaluator.hasPermission(principalProvider.getAuthentication(), category.getId(), Category.class.getCanonicalName(), permission);
    }

    @Override
    public boolean isOwner(String eventId) {
        logger.info("이벤트 소유자 확인: eventId={}", eventId);
        Authentication authentication = principalProvider.getAuthentication();
        if (authentication == null) {
            return false;
        }
        String username = authentication.getName();
        VEvent event = eventRepository.getReferenceById(eventId);
        return event.getCreatedBy().equals(username);
    }

}
