package kr.me.seesaw.repository.jpa;

import kr.me.seesaw.domain.WebPushSubscription;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface JpaWebPushSubscriptionRepository extends Repository<WebPushSubscription, String> {

    WebPushSubscription save(WebPushSubscription subscription);

    Optional<WebPushSubscription> findByEndpoint(String endpoint);

    Optional<WebPushSubscription> findBySiteIdAndUserUsernameAndEndpoint(String siteId, String username, String endpoint);

    List<WebPushSubscription> findAllBySiteIdAndEnabledTrue(String siteId);

}
