package kr.me.seesaw.repository;

import kr.me.seesaw.domain.VJournal;

import java.util.Optional;

public interface VJournalRepository {

    VJournal save(VJournal journal);

    void delete(VJournal journal);

    Optional<VJournal> findById(String id);

    VJournal getReferenceById(String id);

}
