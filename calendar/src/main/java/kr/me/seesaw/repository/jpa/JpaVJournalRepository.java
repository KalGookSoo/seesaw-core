package kr.me.seesaw.repository.jpa;

import kr.me.seesaw.domain.VJournal;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface JpaVJournalRepository extends Repository<VJournal, String> {

    VJournal save(VJournal journal);

    void delete(VJournal journal);

    Optional<VJournal> findById(String id);

    VJournal getReferenceById(String id);

}
