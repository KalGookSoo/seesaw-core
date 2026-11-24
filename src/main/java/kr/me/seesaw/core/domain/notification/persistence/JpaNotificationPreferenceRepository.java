package kr.me.seesaw.core.domain.notification.persistence;

import kr.me.seesaw.core.domain.notification.NotificationPreference;
import kr.me.seesaw.core.domain.notification.NotificationType;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface JpaNotificationPreferenceRepository extends Repository<NotificationPreference, String> {

    NotificationPreference save(NotificationPreference preference);

    Optional<NotificationPreference> findBySiteIdAndUserUsernameAndType(String siteId, String username, NotificationType type);

}
