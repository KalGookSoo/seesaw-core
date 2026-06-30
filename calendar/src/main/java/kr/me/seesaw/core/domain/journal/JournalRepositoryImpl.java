package kr.me.seesaw.core.domain.journal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JournalRepositoryImpl implements JournalRepository {

    private final JpaVJournalRepository jpaVJournalRepository;

    @Override
    public VJournal save(VJournal journal) {
        return jpaVJournalRepository.save(journal);
    }

    @Override
    public void delete(VJournal journal) {
        jpaVJournalRepository.delete(journal);
    }

    @Override
    public Optional<VJournal> findById(String id) {
        return jpaVJournalRepository.findById(id);
    }

    @Override
    public VJournal getReferenceById(String id) {
        return jpaVJournalRepository.getReferenceById(id);
    }

}
