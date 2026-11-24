package kr.me.seesaw.core.domain.notification.persistence;

import kr.me.seesaw.core.domain.notification.Notification;
import kr.me.seesaw.core.domain.notification.NotificationRepository;
import kr.me.seesaw.core.domain.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final JpaNotificationRepository jpaNotificationRepository;

    @Override
    public Notification save(Notification notification) {
        return jpaNotificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> findById(String id) {
        return jpaNotificationRepository.findById(id);
    }

    @Override
    public Optional<Notification> findByRecipientUsernameAndSourceEventIdAndType(String username, String sourceEventId, NotificationType type) {
        return jpaNotificationRepository.findByRecipientUsernameAndSourceEventIdAndType(username, sourceEventId, type);
    }

    @Override
    public Page<Notification> findInbox(String siteId, String username, LocalDateTime now, Pageable pageable) {
        return jpaNotificationRepository.findInbox(siteId, username, now, pageable);
    }

    @Override
    public long countUnread(String siteId, String username, LocalDateTime now) {
        return jpaNotificationRepository.countUnread(siteId, username, now);
    }

}
