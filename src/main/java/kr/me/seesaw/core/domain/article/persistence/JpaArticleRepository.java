package kr.me.seesaw.core.domain.article.persistence;

import kr.me.seesaw.core.domain.article.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface JpaArticleRepository extends Repository<Article, String> {

    Article save(Article article);

    Article saveAndFlush(Article article);

    void delete(Article article);

    Optional<Article> findById(String id);

    Article getReferenceById(String id);

    Page<Article> findAllByCategoryId(String categoryId, Pageable pageable);

    List<Article> findAllByCategoryIdInAndExposed(List<String> categoryIds, boolean exposed, Sort sort);

    List<Article> findAllByCategoryIdAndFixed(String categoryId, boolean fixed, Sort sort);

    @Query("""
            select article from Article article
            where article.category.site.id = :siteId
              and article.exposed = true
              and (lower(article.title) like lower(concat('%', :keyword, '%'))
                   or lower(article.content) like lower(concat('%', :keyword, '%')))
            order by article.createdDate desc
            """)
    List<Article> search(@Param("siteId") String siteId, @Param("keyword") String keyword, Pageable pageable);

    @Query("""
            select count(article) from Article article
            where article.category.site.id = :siteId
              and article.exposed = true
              and (lower(article.title) like lower(concat('%', :keyword, '%'))
                   or lower(article.content) like lower(concat('%', :keyword, '%')))
            """)
    long countSearch(@Param("siteId") String siteId, @Param("keyword") String keyword);

}
