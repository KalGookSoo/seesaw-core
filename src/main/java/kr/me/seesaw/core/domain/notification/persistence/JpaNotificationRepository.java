package kr.me.seesaw.core.domain.notification.persistence;

import kr.me.seesaw.core.domain.notification.Notification;
import kr.me.seesaw.core.domain.notification.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface JpaNotificationRepository extends Repository<Notification, String> {

    Notification save(Notification notification);

    Optional<Notification> findById(String id);

    Optional<Notification> findByRecipientUsernameAndSourceEventIdAndType(String username, String sourceEventId, NotificationType type);

    @Query("""
            select n from Notification n
            where n.siteId = :siteId
              and n.recipient.username = :username
              and (n.expiresAt is null or n.expiresAt > :now)
            order by n.createdDate desc
            """)
    Page<Notification> findInbox(
            @Param("siteId") String siteId,
            @Param("username") String username,
            @Param("now") LocalDateTime now,
            Pageable pageable
    );

    @Query("""
            select count(n) from Notification n
            where n.siteId = :siteId
              and n.recipient.username = :username
              and n.readAt is null
              and (n.expiresAt is null or n.expiresAt > :now)
            """)
    long countUnread(
            @Param("siteId") String siteId,
            @Param("username") String username,
            @Param("now") LocalDateTime now
    );

}
