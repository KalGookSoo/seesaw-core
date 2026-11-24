package kr.me.seesaw.core.domain.notification.persistence;

import kr.me.seesaw.core.domain.notification.NotificationPreference;
import kr.me.seesaw.core.domain.notification.NotificationPreferenceRepository;
import kr.me.seesaw.core.domain.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificationPreferenceRepositoryImpl implements NotificationPreferenceRepository {

    private final JpaNotificationPreferenceRepository jpaNotificationPreferenceRepository;

    @Override
    public NotificationPreference save(NotificationPreference preference) {
        return jpaNotificationPreferenceRepository.save(preference);
    }

    @Override
    public Optional<NotificationPreference> findBySiteIdAndUserUsernameAndType(String siteId, String username, NotificationType type) {
        return jpaNotificationPreferenceRepository.findBySiteIdAndUserUsernameAndType(siteId, username, type);
    }

}
