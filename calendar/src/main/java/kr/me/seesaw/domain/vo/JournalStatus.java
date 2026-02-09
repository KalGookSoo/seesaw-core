package kr.me.seesaw.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RFC 5545 Section 3.8.1.11 기반 저널 상태
 */
@Getter
@AllArgsConstructor
public enum JournalStatus {
    DRAFT("초안"),
    FINAL("최종본"),
    CANCELLED("취소됨");

    private final String description;
}
