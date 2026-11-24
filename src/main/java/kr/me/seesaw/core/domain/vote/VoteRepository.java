package kr.me.seesaw.core.domain.vote;

import java.util.List;
import java.util.Optional;

public interface VoteRepository {

    Vote save(Vote vote);

    Optional<Vote> findByCreatedByAndReferenceId(String createdBy, String referenceId);

    long countByReferenceIdAndApproved(String referenceId, boolean approved);

    List<Vote> findAllByReferenceIdInAndApproved(List<String> referenceIds, boolean approved);

    void delete(Vote vote);

}
