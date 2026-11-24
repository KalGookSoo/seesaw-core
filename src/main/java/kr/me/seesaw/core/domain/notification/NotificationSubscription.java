package kr.me.seesaw.core.domain.notification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kr.me.seesaw.core.domain.BaseEntity;
import kr.me.seesaw.core.domain.category.Category;
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
@EqualsAndHashCode(exclude = {"site", "user", "category"}, callSuper = true)
@ToString(exclude = {"site", "user", "category"})
@Entity
@Table(name = "tb_notification_subscription", indexes = {
        @Index(name = "idx_notification_subscription_category", columnList = "site_id, category_id, enabled")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_notification_subscription_user_category", columnNames = {"site_id", "user_id", "category_id"})
})
@Comment("게시판 알림 구독")
@DynamicInsert
@DynamicUpdate
public class NotificationSubscription extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private boolean enabled = true;

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

}
