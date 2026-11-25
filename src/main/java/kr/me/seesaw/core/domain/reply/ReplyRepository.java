package kr.me.seesaw.core.domain.reply;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {

    Page<Reply> findActivity(String username, List<String> categoryIds, Pageable pageable);

    Reply save(Reply reply);

    Optional<Reply> findById(String id);

    List<Reply> findAll();

    void deleteById(String id);

    void delete(Reply reply);

    List<Reply> findAllByArticleIdIn(List<String> articleIds);

    void deleteAllInBatch(Iterable<Reply> entities);

    Reply getReferenceById(String id);

}
