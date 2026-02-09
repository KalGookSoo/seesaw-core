package kr.me.seesaw.repository.impl;

import kr.me.seesaw.domain.Reply;
import kr.me.seesaw.repository.ReplyRepository;
import kr.me.seesaw.repository.jpa.JpaReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository {

    private final JpaReplyRepository jpaReplyRepository;

    @Override
    public Reply save(Reply reply) {
        return jpaReplyRepository.save(reply);
    }

    @Override
    public Optional<Reply> findById(String id) {
        return jpaReplyRepository.findById(id);
    }

    @Override
    public List<Reply> findAll() {
        return jpaReplyRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        jpaReplyRepository.deleteById(id);
    }

    @Override
    public void delete(Reply reply) {
        jpaReplyRepository.delete(reply);
    }

    @Override
    public List<Reply> findAllByArticleIdIn(List<String> articleIds) {
        return jpaReplyRepository.findAllByArticleIdIn(articleIds);
    }

    @Override
    public void deleteAllInBatch(Iterable<Reply> entities) {
        jpaReplyRepository.deleteAllInBatch(entities);
    }

    @Override
    public Reply getReferenceById(String id) {
        return jpaReplyRepository.getReferenceById(id);
    }

}
