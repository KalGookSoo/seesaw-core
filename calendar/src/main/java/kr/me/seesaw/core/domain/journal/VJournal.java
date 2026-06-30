package kr.me.seesaw.core.domain.journal;

import jakarta.persistence.*;
import kr.me.seesaw.core.domain.CalendarComponent;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"descriptions"}, callSuper = true)
@ToString(exclude = {"descriptions"}, callSuper = true)
@Entity
@Table(name = "tb_journal")
@Comment("저널(Journal)")
@DynamicInsert
@DynamicUpdate
public class VJournal extends CalendarComponent {

    @Comment("기준 일시")
    private LocalDateTime dtStart;

    @Column(columnDefinition = "TEXT")
    @Comment("요약")
    private String summary;

    @ElementCollection
    @CollectionTable(name = "tb_journal_description", joinColumns = @JoinColumn(name = "journal_id"))
    @Column(name = "description", columnDefinition = "TEXT")
    private List<String> descriptions;

    @Enumerated(EnumType.STRING)
    @Comment("상태")
    private JournalStatus status;

}
