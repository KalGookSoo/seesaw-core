package kr.me.seesaw.core.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RFC 5545 Section 3.8.1.11 기반 이벤트 상태
 */
@Getter
@AllArgsConstructor
public enum EventStatus {
    TENTATIVE("잠정적"),
    CONFIRMED("확정됨"),
    CANCELLED("취소됨");

    private final String description;
}
