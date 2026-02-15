package kr.me.seesaw.domain;

import jakarta.persistence.*;
import kr.me.seesaw.domain.vo.Address;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"roleMappings", "categories"}, callSuper = true)
@ToString(exclude = {"roleMappings", "categories"})

@Entity
@Table(name = "tb_site", indexes = {
        @Index(columnList = "parent_id")
})
@Comment("사이트")
@DynamicInsert
@DynamicUpdate
public class Site extends AbstractHierarchical<Site> {

    @Comment("이름")
    private String name;

    @Comment("도메인이름")
    @Column(unique = true)
    private String domainName;

    @Comment("설명")
    private String description;

    @Comment("분류코드")
    private String distributionCode;

    @Comment("검색엔진 노출여부")
    private boolean searchEngineExposed;

    @Comment("이미지 노출여부")
    private boolean imageExposed;

    @Comment("태그")
    private String tags;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "zipcode", column = @Column(name = "zipcode")),
            @AttributeOverride(name = "value", column = @Column(name = "address"))
    })
    private Address address;

    @Comment("연락처")
    private String contactNumber;

    @Comment("소개글")
    private String intro;

    @Comment("본문")
    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "site", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<RoleMapping> roleMappings = new ArrayList<>();

    @OneToMany(mappedBy = "site", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Category> categories = new ArrayList<>();

    public Address getAddress() {
        return address == null ? Address.empty() : address;
    }

}
