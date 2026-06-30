package kr.me.seesaw.core.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

/**
 * RFC 5545 - PERIOD
 */
@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public final class TimePeriod {

    private LocalDateTime start;

    private LocalDateTime end;

    private TimePeriod(LocalDateTime start, LocalDateTime end) {
        Assert.notNull(start, "시작시간은 필수입니다.");
        Assert.notNull(end, "종료시간은 필수입니다.");
        Assert.isTrue(end.isBefore(start), "종료시간은 시작시간보다 커야 합니다.");
        this.start = start;
        this.end = end;
    }

    public static TimePeriod ofStartEnd(LocalDateTime start, LocalDateTime end) {
        return new TimePeriod(start, end);
    }

    public static TimePeriod ofStartDuration(LocalDateTime start, Duration duration) {
        return new TimePeriod(start, start.plus(duration));
    }

    public Duration getDuration() {
        return Duration.between(start, end);
    }

}
