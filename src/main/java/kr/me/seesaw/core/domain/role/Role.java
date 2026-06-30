package kr.me.seesaw.core.domain.role;

import jakarta.persistence.*;
import kr.me.seesaw.core.domain.BaseEntity;
import kr.me.seesaw.core.domain.mapping.MenuRole;
import kr.me.seesaw.core.domain.mapping.RoleMapping;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"roleMappings", "menuRoles"}, callSuper = true)
@ToString(exclude = {"roleMappings", "menuRoles"})

@Entity
@Table(name = "tb_role", uniqueConstraints = {
        @UniqueConstraint(name = "uq_tb_role_name", columnNames = {"name"})
})
@Comment("역할")
@DynamicInsert
@DynamicUpdate
public class Role extends BaseEntity {

    @Comment("이름")
    private String name;

    @Comment("별칭")
    private String alias;

    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<RoleMapping> roleMappings = new ArrayList<>();

    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MenuRole> menuRoles = new ArrayList<>();

    public boolean has(RoleName roleName) {
        return roleName.name().equals(name);
    }

}
