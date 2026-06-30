package kr.me.seesaw.core.domain.attachment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kr.me.seesaw.core.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString

@Entity
@Table(name = "tb_attachment")
@Comment("첨부파일")
@DynamicInsert
@DynamicUpdate
public class Attachment extends BaseEntity {

    @Comment("참조 식별자")
    @Column(length = 36)
    private String referenceId;

    @Comment("원본이름")
    private String originalName;

    @Comment("이름")
    private String name;

    @Comment("경로명")
    private String pathName;

    @Comment("MIME 타입")
    private String mimeType;

    @Comment("크기")
    private long size;

    private static String generateName(String originName) {
        return String.format("%s_%s", UUID.randomUUID(), originName);
    }

    public boolean isInlineImage() {
        return pathName.equals(Type.INLINE_IMAGE.getPath());
    }

    public boolean isAttachment() {
        return pathName.equals(Type.ATTACHMENT.getPath());
    }

    @Getter
    @AllArgsConstructor
    public enum Type {
        PROFILE("/profiles"),
        BACKGROUND_IMAGE("/backgroundImages"),
        INLINE_IMAGE("/images"),
        ATTACHMENT("/attachments");

        private final String path;
    }

}
