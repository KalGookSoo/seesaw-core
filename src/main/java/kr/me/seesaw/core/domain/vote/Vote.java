package kr.me.seesaw.core.domain.vote;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kr.me.seesaw.core.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString()

@Entity
@Table(name = "tb_vote", indexes = {
        @Index(name = "idx_vote_reference_id_approved", columnList = "reference_id, approved")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uk_vote_created_by_reference_id", columnNames = {"created_by", "reference_id"})
})
@Comment("투표")
@DynamicInsert
@DynamicUpdate
public class Vote extends BaseEntity {

    @Comment("참조 식별자")
    @Column(length = 36)
    private String referenceId;

    @Comment("찬성여부")
    private boolean approved;

}
