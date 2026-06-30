package kr.me.seesaw.core.domain.event;

import jakarta.persistence.*;
import kr.me.seesaw.core.domain.user.Email;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
public class Attendee implements Serializable {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "attendee_email_id", nullable = false)),
            @AttributeOverride(name = "domain", column = @Column(name = "attendee_email_domain", nullable = false))
    })
    @Comment("참석자 캘린더 주소")
    private Email email;

    @Comment("참석자 표시명")
    @Column(name = "attendee_name")
    private String name;

    @Comment("참석자 역할")
    @Enumerated(EnumType.STRING)
    @Column(name = "attendee_role")
    private ParticipantRole role;

    @Comment("참석 상태")
    @Enumerated(EnumType.STRING)
    @Column(name = "attendee_status")
    private ParticipantStatus status;

    public Attendee(Email email, String name, ParticipantRole role, ParticipantStatus status) {
        this.email = Objects.requireNonNull(email, "참석자 이메일은 필수입니다.");
        this.name = name;
        this.role = role;
        this.status = status;
    }

}
