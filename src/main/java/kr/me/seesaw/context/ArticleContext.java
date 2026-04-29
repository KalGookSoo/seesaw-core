package kr.me.seesaw.context;

import kr.me.seesaw.model.ArticleModel;
import kr.me.seesaw.search.ArticleSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleContext {

    Page<ArticleModel> findAll(Pageable pageable, ArticleSearch search);

    Page<ArticleModel> findAllByCategoryId(String categoryId, Pageable pageable);

    ArticleModel find(String id);

    ArticleModel getArticleAggregation(String id);

    List<ArticleModel> getFixedArticles(String categoryId, boolean fixed, Sort sort);

    @Nullable
    ArticleModel getPreviousArticle(ArticleSearch search, LocalDateTime createdDate);

    @Nullable
    ArticleModel getNextArticle(ArticleSearch search, LocalDateTime createdDate);

}
