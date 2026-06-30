package kr.me.seesaw.core.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RFC 5545 Section 3.2.12 기반 참여 상태
 */
@Getter
@AllArgsConstructor
public enum ParticipantStatus {
    /**
     * 조치 필요
     */
    NEEDS_ACTION("조치 필요"),
    /**
     * 승인/수락
     */
    ACCEPTED("수락"),
    /**
     * 거절
     */
    DECLINED("거절"),
    /**
     * 잠정적 수락
     */
    TENTATIVE("잠정적 수락"),
    /**
     * 위임됨
     */
    DELEGATED("위임됨"),
    /**
     * 완료 (VTODO 전용)
     */
    COMPLETED("완료"),
    /**
     * 진행 중 (VTODO 전용)
     */
    IN_PROCESS("진행 중");

    private final String description;
}
