package kr.me.seesaw.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
@ToString(callSuper = true)
public abstract class CalendarComponent extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Comment("UID (RFC 5545)")
    private String uid;

    @Column(nullable = false)
    @Comment("데이터 생성/수정 시점 기록")
    private Instant dtStamp;

    @Comment("최초 생성 시점")
    private Instant created;

    @Comment("최종 수정 시점")
    private Instant lastModified;

    @Column(nullable = false)
    @Comment("변경 횟수 순서")
    private Integer sequence = 0;

}
