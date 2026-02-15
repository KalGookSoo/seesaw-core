package kr.me.seesaw.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.me.seesaw.domain.vo.ParticipantRole;
import kr.me.seesaw.domain.vo.ParticipantStatus;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Attendee {

    @Column(name = "attendee_email")
    private String email;

    @Column(name = "attendee_cn")
    private String commonName;

    @Enumerated(EnumType.STRING)
    @Column(name = "attendee_role")
    private ParticipantRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "attendee_partstat")
    private ParticipantStatus status;

}
