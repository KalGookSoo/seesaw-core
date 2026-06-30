package kr.me.seesaw.core.domain.reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {

    Reply save(Reply reply);

    Optional<Reply> findById(String id);

    List<Reply> findAll();

    void deleteById(String id);

    void delete(Reply reply);

    List<Reply> findAllByArticleIdIn(List<String> articleIds);

    void deleteAllInBatch(Iterable<Reply> entities);

    Reply getReferenceById(String id);

}
