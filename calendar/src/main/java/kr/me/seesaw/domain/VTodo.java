package kr.me.seesaw.domain;

import jakarta.persistence.*;
import kr.me.seesaw.domain.vo.RecurrenceRule;
import kr.me.seesaw.domain.vo.TodoStatus;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "tb_todo")
@Comment("할 일(Todo)")
@DynamicInsert
@DynamicUpdate
public class VTodo extends CalendarComponent {

    @Comment("시작 일시")
    private LocalDateTime dtStart;

    @Comment("마감 기한")
    private LocalDateTime due;

    @Comment("완료 일시")
    private LocalDateTime completed;

    @Column(name = "percent_complete")
    @Comment("진행률 (0-100)")
    private Integer percentComplete;

    @Comment("우선순위 (0-9)")
    private Integer priority;

    @Column(columnDefinition = "TEXT")
    @Comment("요약")
    private String summary;

    @Enumerated(EnumType.STRING)
    @Comment("상태")
    private TodoStatus status;

    @Embedded
    private RecurrenceRule rrule;

}
