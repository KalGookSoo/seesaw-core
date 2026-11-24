package kr.me.seesaw.core.domain.notification.persistence;

import kr.me.seesaw.core.domain.notification.NotificationSubscription;
import kr.me.seesaw.core.domain.notification.NotificationSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificationSubscriptionRepositoryImpl implements NotificationSubscriptionRepository {

    private final JpaNotificationSubscriptionRepository jpaNotificationSubscriptionRepository;

    @Override
    public NotificationSubscription save(NotificationSubscription subscription) {
        return jpaNotificationSubscriptionRepository.save(subscription);
    }

    @Override
    public Optional<NotificationSubscription> findBySiteIdAndUserUsernameAndCategoryId(String siteId, String username, String categoryId) {
        return jpaNotificationSubscriptionRepository.findBySiteIdAndUserUsernameAndCategoryId(siteId, username, categoryId);
    }

    @Override
    public List<NotificationSubscription> findAllBySiteIdAndCategoryIdAndEnabledTrue(String siteId, String categoryId) {
        return jpaNotificationSubscriptionRepository.findAllBySiteIdAndCategoryIdAndEnabledTrue(siteId, categoryId);
    }

}
