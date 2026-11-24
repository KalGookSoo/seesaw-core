package kr.me.seesaw.core.domain.notification.persistence;

import kr.me.seesaw.core.domain.notification.NotificationChannel;
import kr.me.seesaw.core.domain.notification.NotificationDelivery;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface JpaNotificationDeliveryRepository extends Repository<NotificationDelivery, String> {

    NotificationDelivery save(NotificationDelivery delivery);

    Optional<NotificationDelivery> findByNotificationIdAndChannel(String notificationId, NotificationChannel channel);

}
