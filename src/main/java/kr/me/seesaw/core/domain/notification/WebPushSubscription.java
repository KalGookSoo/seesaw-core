package kr.me.seesaw.core.domain.notification;

import jakarta.persistence.*;
import kr.me.seesaw.core.domain.BaseEntity;
import kr.me.seesaw.core.domain.site.Site;
import kr.me.seesaw.core.domain.user.User;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"site", "user"}, callSuper = true)
@ToString(exclude = {"site", "user"})

@Entity
@Table(name = "tb_web_push_subscription",
        indexes = {
                @Index(name = "idx_web_push_subscription_site", columnList = "site_id"),
                @Index(name = "idx_web_push_subscription_user", columnList = "user_id"),
                @Index(name = "idx_web_push_subscription_site_user", columnList = "site_id,user_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_web_push_subscription_endpoint", columnNames = "endpoint")
        })
@Comment("웹 푸시 구독")
@DynamicInsert
@DynamicUpdate
public class WebPushSubscription extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "site_id", nullable = false)
    @Comment("사이트")
    private Site site;

    @Column(name = "site_id", insertable = false, updatable = false)
    @Comment("사이트 식별자")
    private String siteId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @Comment("구독 사용자")
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    @Comment("구독 사용자 식별자")
    private String userId;

    @Column(nullable = false, length = 512)
    @Comment("PushSubscription endpoint")
    private String endpoint;

    @Column(nullable = false, length = 256)
    @Comment("PushSubscription p256dh 공개 키")
    private String p256dh;

    @Column(nullable = false, length = 128)
    @Comment("PushSubscription auth secret")
    private String auth;

    @Column(length = 512)
    @Comment("브라우저 User-Agent")
    private String userAgent;

    @Column(nullable = false)
    @Comment("활성 여부")
    private boolean enabled = true;

    @Comment("마지막 사용 일시")
    private LocalDateTime lastUsedAt;

    public WebPushSubscription(Site site, User user, String endpoint, String p256dh, String auth, String userAgent) {
        update(site, user, endpoint, p256dh, auth, userAgent);
    }

    public void update(Site site, User user, String endpoint, String p256dh, String auth, String userAgent) {
        Assert.notNull(site, "사이트는 NULL이 될 수 없습니다.");
        Assert.notNull(user, "계정은 NULL이 될 수 없습니다.");
        Assert.hasText(endpoint, "Endpoint는 비어 있을 수 없습니다.");
        Assert.hasText(p256dh, "p256dh는 비어 있을 수 없습니다.");
        Assert.hasText(auth, "auth는 비어 있을 수 없습니다.");

        this.site = site;
        this.siteId = site.getId();
        this.user = user;
        this.userId = user.getId();
        this.endpoint = endpoint;
        this.p256dh = p256dh;
        this.auth = auth;
        this.userAgent = userAgent;
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public void markUsed() {
        this.lastUsedAt = LocalDateTime.now();
    }

}
