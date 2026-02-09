package kr.me.seesaw.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.me.seesaw.domain.vo.ParticipantRole;
import kr.me.seesaw.domain.vo.ParticipantStatus;
import lombok.*;
import org.hibernate.annotations.Comment;

@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Attendee {

    @Column(name = "attendee_email")
    @Comment("이메일")
    private String email;

    @Column(name = "attendee_cn")
    @Comment("이름(Common Name)")
    private String commonName;

    @Enumerated(EnumType.STRING)
    @Column(name = "attendee_role")
    @Comment("역할")
    private ParticipantRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "attendee_partstat")
    @Comment("참여 상태")
    private ParticipantStatus status;

}
