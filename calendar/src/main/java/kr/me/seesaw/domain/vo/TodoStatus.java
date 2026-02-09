package kr.me.seesaw.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RFC 5545 Section 3.8.1.11 기반 할 일 상태
 */
@Getter
@AllArgsConstructor
public enum TodoStatus {
    NEEDS_ACTION("조치 필요"),
    COMPLETED("완료됨"),
    IN_PROCESS("진행 중"),
    CANCELLED("취소됨");

    private final String description;
}
