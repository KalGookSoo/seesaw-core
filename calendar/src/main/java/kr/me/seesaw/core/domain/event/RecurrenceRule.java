package kr.me.seesaw.core.domain.event;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

/**
 * RFC 5545 Section 3.8.5.3 기반 반복 규칙(RRULE) 값 객체입니다.
 *
 * <p>RRULE은 VEVENT 내부에 포함되는 property이며, 세부 문법은 RFC 5545의
 * RECUR value type과 recurrence rule property를 따릅니다.</p>
 *
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc5545#section-3.3.10">RFC 5545 3.3.10 Recurrence Rule</a>
 * @see <a href="https://datatracker.ietf.org/doc/html/rfc5545#section-3.8.5.3">RFC 5545 3.8.5.3 Recurrence Rule</a>
 */
@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public final class RecurrenceRule implements Serializable {

    /**
     * 반복 주기 단위입니다.
     *
     * <p>예: {@code DAILY}, {@code WEEKLY}, {@code MONTHLY}, {@code YEARLY}</p>
     */
    @Enumerated(EnumType.STRING)
    @Comment("반복 주기")
    private Frequency freq;

    /**
     * 반복 주기의 간격입니다.
     *
     * <p>예: {@code FREQ=WEEKLY;INTERVAL=2}는 2주마다 반복됨을 의미합니다.</p>
     */
    @Comment("반복 간격")
    private Integer interval;

    /**
     * 반복 종료 일시입니다.
     *
     * <p>RFC 5545의 {@code UNTIL}에 해당합니다. {@code count}와 동시에 사용하는 것은 피해야 합니다.</p>
     */
    @Comment("반복 종료 일시")
    private LocalDateTime until;

    /**
     * 반복 횟수입니다.
     *
     * <p>RFC 5545의 {@code COUNT}에 해당합니다. {@code until}과 동시에 사용하는 것은 피해야 합니다.</p>
     */
    @Comment("반복 횟수")
    private Integer count;

    /**
     * 요일별 반복 조건입니다.
     *
     * <p>RFC 5545의 {@code BYDAY}에 해당합니다. 쉼표로 여러 값을 지정할 수 있고,
     * 월간/연간 반복에서는 순번 접두어를 함께 사용할 수 있습니다.</p>
     *
     * <p>예: {@code MO}, {@code MO,WE,FR}, {@code 1MO}, {@code -1FR}</p>
     *
     * @see <a href="https://datatracker.ietf.org/doc/html/rfc5545#section-3.3.10">RFC 5545 BYDAY</a>
     */
    @Comment("요일별 반복 조건")
    private String byDay;

    /**
     * 월별 반복 조건입니다.
     *
     * <p>RFC 5545의 {@code BYMONTH}에 해당합니다. 1부터 12까지의 월 값을 쉼표로 지정합니다.</p>
     *
     * <p>예: {@code 1}, {@code 1,6,12}</p>
     */
    @Comment("월별 반복 조건")
    private String byMonth;

    /**
     * 월의 일자별 반복 조건입니다.
     *
     * <p>RFC 5545의 {@code BYMONTHDAY}에 해당합니다. 1부터 31까지의 양수 또는
     * -1부터 -31까지의 음수 값을 쉼표로 지정합니다. 음수는 월말 기준 역순 일자를 의미합니다.</p>
     *
     * <p>예: {@code 1}, {@code 1,15}, {@code -1}, {@code -1,-2}</p>
     */
    @Comment("월의 일자별 반복 조건")
    private String byMonthDay;

    /**
     * 주 시작 요일입니다.
     *
     * <p>RFC 5545의 {@code WKST}에 해당합니다. 주 단위 반복 계산에서 한 주의 시작 요일을 지정합니다.</p>
     */
    @Enumerated(EnumType.STRING)
    @Comment("주 시작 요일")
    private DayOfWeek wkst;

    private RecurrenceRule(
            Frequency freq,
            Integer interval,
            LocalDateTime until,
            Integer count,
            String byDay,
            String byMonth,
            String byMonthDay,
            DayOfWeek wkst
    ) {
        this.freq = freq;
        this.interval = interval;
        this.until = until;
        this.count = count;
        this.byDay = byDay;
        this.byMonth = byMonth;
        this.byMonthDay = byMonthDay;
        this.wkst = wkst;
    }

    public static RecurrenceRule of(
            Frequency freq,
            Integer interval,
            LocalDateTime until,
            Integer count,
            String byDay,
            String byMonth,
            String byMonthDay,
            DayOfWeek wkst
    ) {
        return new RecurrenceRule(freq, interval, until, count, byDay, byMonth, byMonthDay, wkst);
    }

    public static RecurrenceRule of(String rruleString) {
        // RFC 5545 파싱 로직 구현
        return new RecurrenceRule();
    }

}
