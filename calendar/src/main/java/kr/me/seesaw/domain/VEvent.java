package kr.me.seesaw.domain;

import jakarta.persistence.*;
import kr.me.seesaw.domain.vo.EventStatus;
import kr.me.seesaw.domain.vo.RecurrenceRule;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
    private RecurrenceRule rrule;

    @Comment("시간대 식별자")
    private String tzid;

    @Comment("기간")
    private String duration;

    public ZonedDateTime getStartWithZone() {
        return dtStart.atZone(ZoneId.of(tzid));
    }

}
