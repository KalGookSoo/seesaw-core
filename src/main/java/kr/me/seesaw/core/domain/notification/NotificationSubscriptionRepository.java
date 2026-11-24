package kr.me.seesaw.core.domain.notification;

import java.util.List;
import java.util.Optional;

public interface NotificationSubscriptionRepository {

    NotificationSubscription save(NotificationSubscription subscription);

    Optional<NotificationSubscription> findBySiteIdAndUserUsernameAndCategoryId(String siteId, String username, String categoryId);

    List<NotificationSubscription> findAllBySiteIdAndCategoryIdAndEnabledTrue(String siteId, String categoryId);

}
