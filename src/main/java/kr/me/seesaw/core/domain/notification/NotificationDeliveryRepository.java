package kr.me.seesaw.core.domain.notification;

import java.util.Optional;

public interface NotificationDeliveryRepository {

    NotificationDelivery save(NotificationDelivery delivery);

    Optional<NotificationDelivery> findByNotificationIdAndChannel(String notificationId, NotificationChannel channel);

}
