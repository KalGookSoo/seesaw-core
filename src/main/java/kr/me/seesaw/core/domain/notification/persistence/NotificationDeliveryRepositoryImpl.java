package kr.me.seesaw.core.domain.notification.persistence;

import kr.me.seesaw.core.domain.notification.NotificationChannel;
import kr.me.seesaw.core.domain.notification.NotificationDelivery;
import kr.me.seesaw.core.domain.notification.NotificationDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificationDeliveryRepositoryImpl implements NotificationDeliveryRepository {

    private final JpaNotificationDeliveryRepository jpaNotificationDeliveryRepository;

    @Override
    public NotificationDelivery save(NotificationDelivery delivery) {
        return jpaNotificationDeliveryRepository.save(delivery);
    }

    @Override
    public Optional<NotificationDelivery> findByNotificationIdAndChannel(String notificationId, NotificationChannel channel) {
        return jpaNotificationDeliveryRepository.findByNotificationIdAndChannel(notificationId, channel);
    }

}
