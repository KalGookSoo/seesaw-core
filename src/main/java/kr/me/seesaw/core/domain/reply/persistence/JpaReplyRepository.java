package kr.me.seesaw.core.domain.reply.persistence;

import kr.me.seesaw.core.domain.reply.Reply;
import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface JpaReplyRepository extends Repository<Reply, String> {

    @Query("""
            select r from Reply r
            where r.createdBy = :username and r.article.category.id in :categoryIds
              and r.exposed = true and r.article.exposed = true
            """)
    Page<Reply> findActivity(@Param("username") String username, @Param("categoryIds") List<String> categoryIds, Pageable pageable);

    Reply save(Reply reply);

    Optional<Reply> findById(String id);

    List<Reply> findAll();

    void deleteById(String id);

    void delete(Reply reply);

    List<Reply> findAllByArticleIdIn(List<String> articleIds);

    void deleteAllInBatch(Iterable<Reply> entities);

    Reply getReferenceById(String id);

}
