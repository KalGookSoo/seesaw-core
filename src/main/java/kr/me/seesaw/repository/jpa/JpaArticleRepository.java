package kr.me.seesaw.repository.jpa;

import kr.me.seesaw.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    List<Article> findAllByCategoryIdAndFixed(String categoryId, boolean fixed, Sort sort);

}
