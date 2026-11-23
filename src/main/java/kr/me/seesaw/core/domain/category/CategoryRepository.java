package kr.me.seesaw.core.domain.category;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    Category save(Category category);

    Category getReferenceById(String id);

    Optional<Category> findById(String id);

    void deleteById(String id);

    Collection<Category> findAllBySiteId(String siteId, Sort sort);

    Collection<Category> findAllBySiteIdAndExposed(String siteId, boolean exposed, Sort sort);

    List<Category> search(String siteId, String keyword, Pageable pageable);

    void flush();

}
