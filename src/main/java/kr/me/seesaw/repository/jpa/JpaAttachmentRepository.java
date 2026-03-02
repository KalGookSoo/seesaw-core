package kr.me.seesaw.repository.jpa;

import kr.me.seesaw.domain.Attachment;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JpaAttachmentRepository extends Repository<Attachment, String> {

    Attachment save(Attachment attachment);

    Optional<Attachment> findById(String id);

    List<Attachment> findAllByReferenceIdIn(List<String> referenceIds);

    List<Attachment> findAllByIdIn(Collection<String> ids);

    void delete(Attachment attachment);

    void deleteAllInBatch(Iterable<Attachment> entities);

    Attachment getReferenceById(String id);

}
