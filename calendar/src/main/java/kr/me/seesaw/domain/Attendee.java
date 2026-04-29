package kr.me.seesaw.domain;

import jakarta.persistence.*;
import kr.me.seesaw.domain.vo.Email;
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

    @Comment("캘린더 이벤트 식별자")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    private VEvent event;

    @Column(name = "event_id", length = 36, insertable = false, updatable = false, nullable = false)
    private String eventId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "email_id")),
            @AttributeOverride(name = "domain", column = @Column(name = "email_domain"))
    })
    private Email email;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ParticipantRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ParticipantStatus status;

}
