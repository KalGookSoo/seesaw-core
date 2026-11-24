package kr.me.seesaw.core.domain.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface NotificationRepository {

    Notification save(Notification notification);

    Optional<Notification> findById(String id);

    Optional<Notification> findByRecipientUsernameAndSourceEventIdAndType(String username, String sourceEventId, NotificationType type);

    Page<Notification> findInbox(String siteId, String username, LocalDateTime now, Pageable pageable);

    long countUnread(String siteId, String username, LocalDateTime now);

}
