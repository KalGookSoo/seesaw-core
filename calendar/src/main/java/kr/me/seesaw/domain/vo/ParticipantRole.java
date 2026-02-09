package kr.me.seesaw.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RFC 5545 Section 3.2.16 기반 참여자 역할
 */
@Getter
@AllArgsConstructor
public enum ParticipantRole {
    CHAIR("의장"),
    REQ_PARTICIPANT("필수 참석자"),
    OPT_PARTICIPANT("선택적 참석자"),
    NON_PARTICIPANT("참관인 (비참석 정보 제공자)");

    private final String description;
}
