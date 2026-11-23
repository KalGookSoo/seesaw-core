package kr.me.seesaw.core.domain.category.persistence;

import kr.me.seesaw.core.domain.category.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JpaCategoryRepository extends Repository<Category, String> {

    Category save(Category category);

    Category getReferenceById(String id);

    Optional<Category> findById(String id);

    void deleteById(String id);

    Collection<Category> findAllBySiteId(String siteId, Sort sort);

    @Query("""
            select category from Category category
            where category.site.id = :siteId
              and category.exposed = true
              and (lower(category.name) like lower(concat('%', :keyword, '%'))
                   or lower(category.description) like lower(concat('%', :keyword, '%')))
            order by category.sequence asc
            """)
    List<Category> search(@Param("siteId") String siteId, @Param("keyword") String keyword, Pageable pageable);

    void flush();

}
