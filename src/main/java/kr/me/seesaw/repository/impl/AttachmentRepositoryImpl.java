package kr.me.seesaw.repository.impl;

import kr.me.seesaw.domain.Attachment;
import kr.me.seesaw.repository.AttachmentRepository;
import kr.me.seesaw.repository.jpa.JpaAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AttachmentRepositoryImpl implements AttachmentRepository {

    private final JpaAttachmentRepository jpaAttachmentRepository;

    @Override
    public Attachment save(Attachment attachment) {
        return jpaAttachmentRepository.save(attachment);
    }

    @Override
    public Optional<Attachment> findById(String id) {
        return jpaAttachmentRepository.findById(id);
    }

    @Override
    public Attachment getReferenceById(String id) {
        return jpaAttachmentRepository.getReferenceById(id);
    }

    @Override
    public List<Attachment> findAllByReferenceIdIn(List<String> referenceIds) {
        return jpaAttachmentRepository.findAllByReferenceIdIn(referenceIds);
    }

    @Override
    public List<Attachment> findAllByIdIn(Collection<String> ids) {
        return jpaAttachmentRepository.findAllByIdIn(ids);
    }

    @Override
    public void delete(Attachment attachment) {
        jpaAttachmentRepository.delete(attachment);
    }

    @Override
    public void deleteAllInBatch(Iterable<Attachment> entities) {
        jpaAttachmentRepository.deleteAllInBatch(entities);
    }

}
