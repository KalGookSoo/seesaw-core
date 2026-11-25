package kr.me.seesaw.core.domain.vote.persistence;

import java.util.List;
import java.util.Optional;

import kr.me.seesaw.core.domain.vote.Vote;
import kr.me.seesaw.core.domain.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
@RequiredArgsConstructor
public class VoteRepositoryImpl implements VoteRepository {

    private final JpaVoteRepository jpaVoteRepository;

    @Override
    public Page<Vote> findActivity(String username, List<String> categoryIds, Pageable pageable) {
        return jpaVoteRepository.findActivity(username, categoryIds, pageable);
    }

    @Override
    public Vote save(Vote vote) {
        return jpaVoteRepository.save(vote);
    }

    @Override
    public Optional<Vote> findByCreatedByAndReferenceId(String createdBy, String referenceId) {
        return jpaVoteRepository.findByCreatedByAndReferenceId(createdBy, referenceId);
    }

    @Override
    public long countByReferenceIdAndApproved(String referenceId, boolean approved) {
        return jpaVoteRepository.countByReferenceIdAndApproved(referenceId, approved);
    }

    @Override
    public List<Vote> findAllByReferenceIdInAndApproved(List<String> referenceIds, boolean approved) {
        return jpaVoteRepository.findAllByReferenceIdInAndApproved(referenceIds, approved);
    }

    @Override
    public void delete(Vote vote) {
        jpaVoteRepository.delete(vote);
    }

}
