package kr.me.seesaw.core.domain.article;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ArticleQueryRepository {

    List<Article> search(int offset, int limit, String sort, String categoryId, String keyField, String keyWord);

    long count(String categoryId, String keyField, String keyWord);

    List<Article> findAllByCategoryId(List<String> categoryIds, LocalDateTime createdDate);

    Optional<Article> findFirstNext(String categoryId, String keyField, String keyWord, LocalDateTime createdDate, String sortOrder);

}
