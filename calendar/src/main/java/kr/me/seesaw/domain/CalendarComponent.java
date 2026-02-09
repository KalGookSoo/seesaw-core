package kr.me.seesaw.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import java.time.Instant;
import java.time.ZoneOffset;

@MappedSuperclass
@Getter
@Setter
@ToString(callSuper = true)
public abstract class CalendarComponent extends BaseEntity {

    @Column(nullable = false)
    @Comment("데이터 생성/수정 시점 기록")
    private Instant dtStamp;

    @Column(nullable = false)
    @Comment("변경 횟수 순서")
    private Integer sequence = 0;

    @Comment("UID (RFC 5545)")
    public String getUid() {
        return getId();
    }

    @Comment("최초 생성 시점")
    public Instant getCreated() {
        return getCreatedDate() != null ? getCreatedDate().toInstant(ZoneOffset.UTC) : null;
    }

    @Comment("최종 수정 시점")
    public Instant getLastModified() {
        return getLastModifiedDate() != null ? getLastModifiedDate().toInstant(ZoneOffset.UTC) : null;
    }

}
