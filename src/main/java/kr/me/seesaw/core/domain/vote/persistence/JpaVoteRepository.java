package kr.me.seesaw.core.domain.vote.persistence;

import java.util.List;
import java.util.Optional;

import kr.me.seesaw.core.domain.vote.Vote;
import org.springframework.data.repository.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaVoteRepository extends Repository<Vote, String> {

    @Query("""
            select v from Vote v where v.createdBy = :username and v.approved = true and (
              exists (select a.id from Article a where a.id = v.referenceId and a.exposed = true and a.category.id in :categoryIds)
              or exists (select r.id from Reply r where r.id = v.referenceId and r.exposed = true and r.article.exposed = true and r.article.category.id in :categoryIds)
            )
            """)
    Page<Vote> findActivity(@Param("username") String username, @Param("categoryIds") List<String> categoryIds, Pageable pageable);

    Vote save(Vote vote);

    Optional<Vote> findByCreatedByAndReferenceId(String createdBy, String referenceId);

    long countByReferenceIdAndApproved(String referenceId, boolean approved);

    List<Vote> findAllByReferenceIdInAndApproved(List<String> referenceIds, boolean approved);

    void delete(Vote vote);

}
