package kr.me.seesaw.domain;

import jakarta.persistence.*;
import kr.me.seesaw.domain.vo.CategoryType;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"site", "articles"}, callSuper = true)
@ToString(exclude = {"site", "articles"})

@Entity
@Table(name = "tb_category", indexes = {
        @Index(columnList = "site_id"),
        @Index(columnList = "parent_id")
})
@DynamicInsert
@DynamicUpdate
@Comment("카테고리")
public class Category extends AbstractHierarchical<Category> {

    @Comment("이름")
    private String name;

    @Comment("설명")
    private String description;

    @Enumerated(EnumType.STRING)
    @Comment("타입")
    private CategoryType type;

    @Comment("사이트 노출여부")
    private boolean siteExposed;

    @Comment("사이트 노출순서")
    private int siteExposedOrder;

    @Comment("노출여부")
    private boolean exposed;

    @Comment("순서")
    private Integer sequence;

    @Column(name = "site_id", insertable = false, updatable = false)
    private String siteId;

    @Comment("사이트")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", referencedColumnName = "id")
    private Site site;

    @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Article> articles = new ArrayList<>();

}
