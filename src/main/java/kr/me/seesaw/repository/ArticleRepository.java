package kr.me.seesaw.repository;

import kr.me.seesaw.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository {

    Article save(Article article);

    Article update(Article article);

    void delete(Article article);

    Optional<Article> findById(String id);

    Page<Article> findAllByCategoryId(String categoryId, Pageable pageable);

    List<Article> findAllByCategoryIdAndFixed(String categoryId, boolean fixed, Sort sort);

    Article getReferenceById(String id);

}
