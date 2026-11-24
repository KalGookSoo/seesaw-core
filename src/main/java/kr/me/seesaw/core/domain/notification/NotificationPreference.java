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

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"site", "user"}, callSuper = true)
@ToString(exclude = {"site", "user"})
@Entity
@Table(name = "tb_notification_preference", indexes = {
        @Index(name = "idx_notification_preference_user", columnList = "site_id, user_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_notification_preference_user_type", columnNames = {"site_id", "user_id", "type"})
})
@Comment("알림 수신 설정")
@DynamicInsert
@DynamicUpdate
public class NotificationPreference extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "site_id", nullable = false)
    @Comment("사이트")
    private Site site;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("계정")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    @Comment("알림 유형")
    private NotificationType type;

    @Column(name = "in_app_enabled", nullable = false)
    @Comment("서비스 내부 알림함·알림 벨 활성화 여부")
    private boolean inAppEnabled = true;

    @Column(name = "web_push_enabled", nullable = false)
    @Comment("웹 푸시 알림 활성화 여부")
    private boolean webPushEnabled;

    @Column(name = "email_enabled", nullable = false)
    @Comment("이메일 알림 활성화 여부")
    private boolean emailEnabled;

}
