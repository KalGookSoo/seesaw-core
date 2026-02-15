package kr.me.seesaw.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString()

@Entity
@Table(name = "tb_code", indexes = {
        @Index(columnList = "parent_id")
})
@DynamicInsert
@DynamicUpdate
@Comment("코드")
public class Code extends AbstractHierarchical<Code> {

    @Comment("이름")
    private String name;

    @Comment("설명")
    private String description;

    @Comment("순서")
    private Integer sequence;

}
