package kr.me.seesaw.core.domain.notification.persistence;

import kr.me.seesaw.core.domain.notification.NotificationSubscription;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface JpaNotificationSubscriptionRepository extends Repository<NotificationSubscription, String> {

    NotificationSubscription save(NotificationSubscription subscription);

    Optional<NotificationSubscription> findBySiteIdAndUserUsernameAndCategoryId(String siteId, String username, String categoryId);

    List<NotificationSubscription> findAllBySiteIdAndCategoryIdAndEnabledTrue(String siteId, String categoryId);

}
