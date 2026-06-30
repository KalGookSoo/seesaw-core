package kr.me.seesaw.core.domain.article.persistence;

import kr.me.seesaw.core.domain.article.Article;
import kr.me.seesaw.core.domain.article.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepository {

    private final JpaArticleRepository jpaArticleRepository;

    @Override
    public Article save(Article article) {
        return jpaArticleRepository.save(article);
    }

    @Override
    public Article update(Article article) {
        return jpaArticleRepository.saveAndFlush(article);
    }

    @Override
    public void delete(Article article) {
        jpaArticleRepository.delete(article);
    }

    @Override
    public Optional<Article> findById(String id) {
        return jpaArticleRepository.findById(id);
    }

    @Override
    public Page<Article> findAllByCategoryId(String categoryId, Pageable pageable) {
        return jpaArticleRepository.findAllByCategoryId(categoryId, pageable);
    }

    @Override
    public List<Article> findAllByCategoryIdAndFixed(String categoryId, boolean fixed, Sort sort) {
        return jpaArticleRepository.findAllByCategoryIdAndFixed(categoryId, fixed, sort);
    }

    @Override
    public Article getReferenceById(String id) {
        return jpaArticleRepository.getReferenceById(id);
    }

}
