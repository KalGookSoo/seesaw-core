package kr.me.seesaw.core.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kr.me.seesaw.core.domain.BaseEntity;
import kr.me.seesaw.core.domain.mapping.RoleMapping;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"roleMappings"}, callSuper = true)
@ToString(exclude = {"roleMappings"})

@Entity
@Table(name = "tb_user")
@Comment("계정")
@DynamicInsert
@DynamicUpdate
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    @Comment("계정명")
    private String username;

    @JsonIgnore
    @Comment("패스워드")
    private String password;

    @Comment("이름")
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "email_id")),
            @AttributeOverride(name = "domain", column = @Column(name = "email_domain"))
    })
    private Email email;

    @Comment("연락처")
    private String contactNumber;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<RoleMapping> roleMappings = new ArrayList<>();

    @Comment("만료 일시")
    private LocalDateTime expiredDate;

    @Comment("잠금 일시")
    private LocalDateTime lockedDate;

    @Comment("패스워드 만료 일시")
    private LocalDateTime credentialsExpiredDate;

    /**
     * 패스워드를 변경합니다.
     *
     * @param password 패스워드
     */
    public void changePassword(String password) {
        Assert.notNull(password, "패스워드는 NULL이 될 수 없습니다.");
        this.password = password;
        this.credentialsExpiredDate = LocalDate.now().atTime(LocalTime.MIDNIGHT).plusDays(180L);
    }

    /**
     * 만료 일시는 금일(00:00)로부터 2년 후 까지로 설정합니다.
     * 패스워드 만료 일시는 생성일(00:00)로부터 180일 후 까지로 설정합니다.
     */
    public void initializeAccountPolicy() {
        expiredDate = LocalDate.now().atTime(LocalTime.MIDNIGHT).plusYears(2L);
        credentialsExpiredDate = LocalDate.now().atTime(LocalTime.MIDNIGHT).plusDays(180L);
    }

    /**
     * 계정이 만료되지 않았는지 여부를 반환합니다.
     *
     * @return 계정이 만료되지 않았는지 여부
     */
    public boolean isAccountNonExpired() {
        return expiredDate == null || expiredDate.isAfter(LocalDateTime.now());
    }

    /**
     * 계정이 잠겨있지 않은지 여부를 반환합니다.
     *
     * @return 계정이 잠겨있지 않은지 여부
     */
    public boolean isAccountNonLocked() {
        return lockedDate == null || lockedDate.isBefore(LocalDateTime.now());
    }

    /**
     * 계정의 패스워드가 만료되지 않았는지 여부를 반환합니다.
     *
     * @return 계정의 패스워드가 만료되지 않았는지 여부
     */
    public boolean isCredentialsNonExpired() {
        return credentialsExpiredDate == null || credentialsExpiredDate.isAfter(LocalDateTime.now());
    }

    public Email getEmail() {
        return email == null ? Email.empty() : email;
    }

    public void addRole(RoleMapping roleMapping) {
        roleMappings.add(roleMapping);
        roleMapping.setUser(this);
    }

}
