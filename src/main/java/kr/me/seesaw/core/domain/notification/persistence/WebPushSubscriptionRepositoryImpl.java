package kr.me.seesaw.core.domain.notification.persistence;

import kr.me.seesaw.core.domain.notification.WebPushSubscription;
import kr.me.seesaw.core.domain.notification.WebPushSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WebPushSubscriptionRepositoryImpl implements WebPushSubscriptionRepository {

    private final JpaWebPushSubscriptionRepository jpaWebPushSubscriptionRepository;

    @Override
    public WebPushSubscription save(WebPushSubscription subscription) {
        return jpaWebPushSubscriptionRepository.save(subscription);
    }

    @Override
    public Optional<WebPushSubscription> findByEndpoint(String endpoint) {
        return jpaWebPushSubscriptionRepository.findByEndpoint(endpoint);
    }

    @Override
    public Optional<WebPushSubscription> findBySiteIdAndUserUsernameAndEndpoint(String siteId, String username, String endpoint) {
        return jpaWebPushSubscriptionRepository.findBySiteIdAndUserUsernameAndEndpoint(siteId, username, endpoint);
    }

    @Override
    public List<WebPushSubscription> findAllBySiteIdAndEnabledTrue(String siteId) {
        return jpaWebPushSubscriptionRepository.findAllBySiteIdAndEnabledTrue(siteId);
    }

    @Override
    public List<WebPushSubscription> findAllBySiteIdAndUserUsernameAndEnabledTrue(String siteId, String username) {
        return jpaWebPushSubscriptionRepository.findAllBySiteIdAndUserUsernameAndEnabledTrue(siteId, username);
    }

}
