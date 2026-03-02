package kr.me.seesaw.repository;

import kr.me.seesaw.domain.Attachment;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AttachmentRepository {

    Attachment save(Attachment attachment);

    Optional<Attachment> findById(String id);

    Attachment getReferenceById(String id);

    List<Attachment> findAllByReferenceIdIn(List<String> referenceIds);

    List<Attachment> findAllByIdIn(Collection<String> ids);

    void delete(Attachment attachment);

    void deleteAllInBatch(Iterable<Attachment> entities);

}
