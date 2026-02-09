package kr.me.seesaw.repository;

import kr.me.seesaw.domain.Article;
import kr.me.seesaw.search.ArticleSearch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleQueryRepository extends QueryRepository<Article, ArticleSearch> {

    List<Article> findAllByCategoryId(List<String> categoryIds, LocalDateTime createdDate);

    Optional<Article> findFirstNext(ArticleSearch search, LocalDateTime createdDate, String sortOrder);

}
