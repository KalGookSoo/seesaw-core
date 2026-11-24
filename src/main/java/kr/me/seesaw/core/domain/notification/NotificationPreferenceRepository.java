package kr.me.seesaw.core.domain.notification;

import java.util.Optional;

public interface NotificationPreferenceRepository {

    NotificationPreference save(NotificationPreference preference);

    Optional<NotificationPreference> findBySiteIdAndUserUsernameAndType(String siteId, String username, NotificationType type);

}
