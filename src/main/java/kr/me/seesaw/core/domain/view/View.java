package kr.me.seesaw.core.domain.view;

import jakarta.persistence.*;
import kr.me.seesaw.core.domain.BaseEntity;
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
@Table(name = "tb_view", indexes = {
        @Index(columnList = "article_id")
})
@Comment("뷰")
@DynamicInsert
@DynamicUpdate
public class View extends BaseEntity {

    @Column(name = "article_id", insertable = false, updatable = false)
    private String articleId;

    @Comment("게시글 식별자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private Article article;

}
