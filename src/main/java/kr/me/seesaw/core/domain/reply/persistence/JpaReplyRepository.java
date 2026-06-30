package kr.me.seesaw.core.domain.reply.persistence;

import kr.me.seesaw.core.domain.reply.Reply;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface JpaReplyRepository extends Repository<Reply, String> {

    Reply save(Reply reply);

    Optional<Reply> findById(String id);

    List<Reply> findAll();

    void deleteById(String id);

    void delete(Reply reply);

    List<Reply> findAllByArticleIdIn(List<String> articleIds);

    void deleteAllInBatch(Iterable<Reply> entities);

    Reply getReferenceById(String id);

}
