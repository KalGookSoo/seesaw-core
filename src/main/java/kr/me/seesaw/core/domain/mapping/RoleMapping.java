package kr.me.seesaw.core.domain.mapping;

import jakarta.persistence.*;
import kr.me.seesaw.core.domain.BaseEntity;
import kr.me.seesaw.core.domain.role.Role;
import kr.me.seesaw.core.domain.site.Site;
import kr.me.seesaw.core.domain.user.User;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"role", "user", "site"}, callSuper = true)
@ToString(exclude = {"role", "user", "site"})

@Entity
@Table(name = "tb_role_mapping", indexes = {
        @Index(columnList = "user_id, site_id"),
        @Index(columnList = "role_id")
})
@Comment("역할 매핑")
@DynamicInsert
@DynamicUpdate
public class RoleMapping extends BaseEntity {

    @Column(name = "user_id", insertable = false, updatable = false)
    private String userId;

    @Comment("계정 식별자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "site_id", insertable = false, updatable = false)
    private String siteId;

    @Comment("사이트 식별자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    private Site site;

    @Column(name = "role_id", insertable = false, updatable = false)
    private String roleId;

    @Comment("역할 식별자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

}
