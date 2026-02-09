package kr.me.seesaw.repository.jpa;

import kr.me.seesaw.domain.View;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface JpaViewRepository extends Repository<View, String> {

    View save(View view);

    Optional<View> findById(String id);

    List<View> findAll();

    void deleteById(String id);

    List<View> findAllByArticleIdIn(List<String> articleIds);

    void deleteAllInBatch(Iterable<View> entities);

}
