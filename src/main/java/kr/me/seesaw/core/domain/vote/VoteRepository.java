package kr.me.seesaw.core.domain.vote;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface VoteRepository {

    Page<Vote> findActivity(String username, List<String> categoryIds, Pageable pageable);

    Vote save(Vote vote);

    Optional<Vote> findByCreatedByAndReferenceId(String createdBy, String referenceId);

    long countByReferenceIdAndApproved(String referenceId, boolean approved);

    List<Vote> findAllByReferenceIdInAndApproved(List<String> referenceIds, boolean approved);

    void delete(Vote vote);

}
