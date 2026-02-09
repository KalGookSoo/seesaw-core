package kr.me.seesaw.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.me.seesaw.domain.vo.Frequency;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
 * RFC 5545 Section 3.8.5.3 기반 재귀 규칙 (RRULE)
 */
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecurrenceRule {

    @Enumerated(EnumType.STRING)
    @Column(name = "rrule_freq")
    private Frequency freq;

    @Column(name = "rrule_interval")
    private Integer interval;

    @Column(name = "rrule_until")
    private LocalDateTime until;

    @Column(name = "rrule_count")
    private Integer count;

    @Column(name = "rrule_by_day")
    private String byDay; // 예: "MO,TU,-1FR"

    @Column(name = "rrule_by_month")
    private String byMonth; // 예: "1,6,12"

    @Column(name = "rrule_by_monthday")
    private String byMonthDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "rrule_wkst")
    private DayOfWeek wkst;

    // RRULE 문자열을 파싱하거나 생성하는 로직
    public static RecurrenceRule of(String rruleString) {
        // RFC 5545 파싱 로직 구현
        return new RecurrenceRule();
    }

}
