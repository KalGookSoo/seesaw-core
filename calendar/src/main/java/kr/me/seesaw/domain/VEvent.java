package kr.me.seesaw.domain;

import jakarta.persistence.*;
import kr.me.seesaw.domain.vo.EventStatus;
import kr.me.seesaw.domain.vo.RecurrenceRule;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"attendees"}, callSuper = true)
@ToString(exclude = {"attendees"}, callSuper = true)
@Entity
@Table(name = "tb_event")
@Comment("캘린더 이벤트")
@DynamicInsert
@DynamicUpdate
public class VEvent extends CalendarComponent {

    @Column(nullable = false)
    @Comment("시작 일시")
    private LocalDateTime dtStart;

    @Comment("종료 일시")
    private LocalDateTime dtEnd;

    @Column(columnDefinition = "TEXT")
    @Comment("요약/제목")
    private String summary;

    @Column(columnDefinition = "TEXT")
    @Comment("상세 설명")
    private String description;

    @Comment("장소")
    private String location;

    @Enumerated(EnumType.STRING)
    @Comment("상태")
    private EventStatus status;

    @Embedded
    @Comment("반복 규칙")
    private RecurrenceRule rrule;

    @Comment("시간대 식별자")
    private String tzid;

    @Comment("기간")
    private String duration;

    @ElementCollection
    @CollectionTable(name = "tb_event_attendees", joinColumns = @JoinColumn(name = "event_id"))
    private List<Attendee> attendees = new ArrayList<>();

    public ZonedDateTime getStartWithZone() {
        return dtStart.atZone(ZoneId.of(tzid));
    }

}
