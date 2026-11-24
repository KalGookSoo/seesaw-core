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
import kr.me.seesaw.core.domain.site.Site;
import kr.me.seesaw.core.domain.user.User;
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
@EqualsAndHashCode(exclude = {"site", "recipient"}, callSuper = true)
@ToString(exclude = {"site", "recipient"})
@Entity
@Table(name = "tb_notification", indexes = {
        @Index(name = "idx_notification_inbox", columnList = "site_id, recipient_id, read_at, created_date"),
        @Index(name = "idx_notification_expiry", columnList = "expires_at")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_notification_source_recipient_type", columnNames = {"recipient_id", "source_event_id", "type"})
})
@Comment("인앱 알림")
@DynamicInsert
@DynamicUpdate
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "site_id", nullable = false)
    @Comment("사이트")
    private Site site;

    @Column(name = "site_id", insertable = false, updatable = false)
    private String siteId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipient_id", nullable = false)
    @Comment("수신자")
    private User recipient;

    @Column(name = "recipient_id", insertable = false, updatable = false)
    private String recipientId;

    @Column(length = 255)
    @Comment("발생자 계정명")
    private String actorUsername;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    @Comment("알림 유형")
    private NotificationType type;

    @Column(nullable = false, length = 255)
    @Comment("알림 제목")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Comment("알림 내용")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", length = 50)
    @Comment("이동 대상 유형")
    private NotificationTargetType targetType;

    @Column(name = "target_id", length = 36)
    @Comment("이동 대상 식별자")
    private String targetId;

    @Column(name = "target_url", length = 1000)
    @Comment("이동 URL")
    private String targetUrl;

    @Column(name = "source_event_id", nullable = false, length = 100)
    @Comment("원천 이벤트 식별자")
    private String sourceEventId;

    @Column(name = "read_at")
    @Comment("읽은 일시")
    private LocalDateTime readAt;

    @Column(name = "expires_at")
    @Comment("만료 일시")
    private LocalDateTime expiresAt;

    public void markAsRead() {
        this.readAt = LocalDateTime.now();
    }

    public boolean isRead() {
        return readAt != null;
    }

}
