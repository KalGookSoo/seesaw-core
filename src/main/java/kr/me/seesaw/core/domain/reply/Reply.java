package kr.me.seesaw.core.domain.reply;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import kr.me.seesaw.core.domain.AbstractHierarchical;
import kr.me.seesaw.core.domain.article.Article;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"article"}, callSuper = true)
@ToString(exclude = {"article"})

@Entity
@Table(name = "tb_reply", indexes = {
        @Index(columnList = "article_id"),
        @Index(columnList = "parent_id")
})
@Comment("댓글")
@DynamicInsert
@DynamicUpdate
public class Reply extends AbstractHierarchical<Reply> {

    @Comment("노출여부")
    private boolean exposed;

    @Comment("본문")
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "article_id", insertable = false, updatable = false)
    private String articleId;

    @Comment("게시글")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    @JsonBackReference
    private Article article;

}
