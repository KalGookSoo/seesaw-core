package kr.me.seesaw.core.domain.journal;

import java.util.Optional;

public interface JournalRepository {

    VJournal save(VJournal journal);

    void delete(VJournal journal);

    Optional<VJournal> findById(String id);

    VJournal getReferenceById(String id);

}
