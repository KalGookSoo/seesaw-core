package kr.me.seesaw.repository.impl;

import kr.me.seesaw.domain.VJournal;
import kr.me.seesaw.repository.VJournalRepository;
import kr.me.seesaw.repository.jpa.JpaVJournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VJournalRepositoryImpl implements VJournalRepository {

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
