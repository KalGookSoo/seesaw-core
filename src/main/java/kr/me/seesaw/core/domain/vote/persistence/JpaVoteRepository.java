package kr.me.seesaw.core.domain.vote.persistence;

import java.util.List;
import java.util.Optional;

import kr.me.seesaw.core.domain.vote.Vote;
import org.springframework.data.repository.Repository;

public interface JpaVoteRepository extends Repository<Vote, String> {

    Vote save(Vote vote);

    Optional<Vote> findByCreatedByAndReferenceId(String createdBy, String referenceId);

    long countByReferenceIdAndApproved(String referenceId, boolean approved);

    List<Vote> findAllByReferenceIdInAndApproved(List<String> referenceIds, boolean approved);

    void delete(Vote vote);

}
