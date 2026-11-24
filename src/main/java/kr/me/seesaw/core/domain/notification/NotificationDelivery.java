package kr.me.seesaw.core.domain.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kr.me.seesaw.core.domain.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "notification", callSuper = true)
@ToString(exclude = "notification")
@Entity
@Table(name = "tb_notification_delivery", indexes = {
        @Index(name = "idx_notification_delivery_status", columnList = "status, next_attempt_at")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_notification_delivery_channel", columnNames = {"notification_id", "channel"})
})
@Comment("알림 전달 기록")
@DynamicInsert
@DynamicUpdate
public class NotificationDelivery extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "notification_id", nullable = false)
    @Comment("알림")
    private Notification notification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Comment("전달 채널")
    private NotificationChannel channel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Comment("전달 상태")
    private NotificationDeliveryStatus status = NotificationDeliveryStatus.PENDING;

    @Column(name = "attempt_count", nullable = false)
    @Comment("전달 시도 횟수")
    private int attemptCount;

    @Column(name = "delivered_at")
    @Comment("전달 완료 일시")
    private LocalDateTime deliveredAt;

    @Column(name = "next_attempt_at")
    @Comment("다음 전달 시도 일시")
    private LocalDateTime nextAttemptAt;

    @Column(name = "failure_reason", length = 1000)
    @Comment("전달 실패 사유")
    private String failureReason;

}
