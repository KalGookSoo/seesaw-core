package kr.me.seesaw.repository;

import kr.me.seesaw.domain.WebPushSubscription;

import java.util.List;
import java.util.Optional;

public interface WebPushSubscriptionRepository {

    WebPushSubscription save(WebPushSubscription subscription);

    Optional<WebPushSubscription> findByEndpoint(String endpoint);

    Optional<WebPushSubscription> findBySiteIdAndUserUsernameAndEndpoint(String siteId, String username, String endpoint);

    List<WebPushSubscription> findAllBySiteIdAndEnabledTrue(String siteId);

    List<WebPushSubscription> findAllBySiteIdAndUserUsernameAndEnabledTrue(String siteId, String username);

}
