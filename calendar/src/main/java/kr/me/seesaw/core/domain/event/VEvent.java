package kr.me.seesaw.core.domain.event;

import jakarta.persistence.*;
import kr.me.seesaw.core.domain.CalendarComponent;
import kr.me.seesaw.core.domain.article.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tb_event")
@Comment("캘린더 이벤트")
@DynamicInsert
@DynamicUpdate
public class VEvent extends CalendarComponent {

    @Column(name = "id", insertable = false, updatable = false)
    private String articleId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Article article;

    @Column(nullable = false)
    @Comment("시작 일시")
    private LocalDateTime dtStart;

    @Comment("종료 일시")
    private LocalDateTime dtEnd;

    @Column(columnDefinition = "TEXT")
    @Comment("요약/제목")
    private String summary;

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
    @CollectionTable(name = "tb_event_attendee", joinColumns = @JoinColumn(name = "event_id"))
    @Comment("참석자")
    private List<Attendee> attendees = new ArrayList<>();

    public ZonedDateTime getStartWithZone() {
        return dtStart.atZone(ZoneId.of(tzid));
    }

    public void addAttendee(Attendee attendee) {
        attendees.add(attendee);
    }

}
