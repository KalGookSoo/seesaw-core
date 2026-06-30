package kr.me.seesaw.core.domain.category;

import org.springframework.data.domain.Sort;

import java.util.Collection;
import java.util.Optional;

public interface CategoryRepository {

    Category save(Category category);

    Category getReferenceById(String id);

    Optional<Category> findById(String id);

    void deleteById(String id);

    Collection<Category> findAllBySiteId(String siteId, Sort sort);

    void flush();

}
