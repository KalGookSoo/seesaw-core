package kr.me.seesaw.domain;

import jakarta.persistence.*;
import jakarta.persistence.Index;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"menu", "role"}, callSuper = true)
@ToString(exclude = {"menu", "role"})

@Entity
@Table(name = "tb_menu_role", indexes = {
        @Index(columnList = "menu_id"),
        @Index(columnList = "role_id")
})
@Comment("메뉴 역할 매핑")
@DynamicInsert
@DynamicUpdate
public class MenuRole extends BaseEntity {

    @Column(name = "menu_id", insertable = false, updatable = false)
    private String menuId;

    @Comment("메뉴")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", referencedColumnName = "id")
    private Menu menu;

    @Column(name = "role_id", insertable = false, updatable = false)
    private String roleId;

    @Comment("역할")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

}
