package kr.me.seesaw.core.domain.view;

import java.util.List;
import java.util.Optional;

public interface ViewRepository {

    View save(View view);

    Optional<View> findById(String id);

    List<View> findAll();

    void deleteById(String id);

    List<View> findAllByArticleIdIn(List<String> articleIds);

    void deleteAllInBatch(Iterable<View> entities);

}
