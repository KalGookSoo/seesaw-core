package kr.me.seesaw.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import kr.me.seesaw.domain.Article;
import kr.me.seesaw.repository.ArticleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ArticleQueryRepositoryImpl implements ArticleQueryRepository {

    private final EntityManager em;

    @Override
    public List<Article> search(int offset, int limit, String sort, String categoryId, String keyField, String keyWord) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Article> cq = cb.createQuery(Article.class);
        Root<Article> article = cq.from(Article.class);

        List<Predicate> predicates = getPredicates(cb, article, categoryId, keyField, keyWord);
        cq.where(predicates.toArray(new Predicate[0]));

        if (StringUtils.hasText(sort)) {
            String[] parts = sort.split(" ");
            String field = parts[0].contains(".") ? parts[0].substring(parts[0].lastIndexOf(".") + 1) : parts[0];
            if (parts.length > 1 && "DESC".equalsIgnoreCase(parts[1])) {
                cq.orderBy(cb.desc(article.get(field)));
            } else {
                cq.orderBy(cb.asc(article.get(field)));
            }
        }

        TypedQuery<Article> query = em.createQuery(cq);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public long count(String categoryId, String keyField, String keyWord) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Article> article = cq.from(Article.class);

        List<Predicate> predicates = getPredicates(cb, article, categoryId, keyField, keyWord);
        cq.select(cb.count(article)).where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getSingleResult();
    }

    @Override
    public List<Article> findAllByCategoryId(List<String> categoryIds, LocalDateTime createdDate) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Article> cq = cb.createQuery(Article.class);
        Root<Article> article = cq.from(Article.class);

        Predicate categoryPredicate = article.get("category").get("id").in(categoryIds);
        Predicate datePredicate = cb.greaterThanOrEqualTo(article.get("createdDate"), createdDate);

        cq.where(cb.and(categoryPredicate, datePredicate));

        return em.createQuery(cq).getResultList();
    }

    private List<Predicate> getPredicates(CriteriaBuilder cb, Root<Article> article, String categoryId, String keyField, String keyWord) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(categoryId)) {
            predicates.add(cb.equal(article.get("category").get("id"), categoryId));
        }

        if (StringUtils.hasText(keyWord)) {
            String pattern = "%" + keyWord + "%";
            if ("title".equals(keyField)) {
                predicates.add(cb.like(article.get("title"), pattern));
            } else if ("content".equals(keyField)) {
                predicates.add(cb.like(article.get("content"), pattern));
            } else if ("createdBy".equals(keyField)) {
                predicates.add(cb.like(article.get("createdBy"), pattern));
            }
        }

        return predicates;
    }

    @Override
    public Optional<Article> findFirstNext(String categoryId, String keyField, String keyWord, LocalDateTime createdDate, String sortOrder) {
        if (createdDate == null) {
            throw new IllegalArgumentException("createdDate가 null입니다.");
        }

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Article> cq = cb.createQuery(Article.class);
        Root<Article> article = cq.from(Article.class);

        List<Predicate> predicates = getPredicates(cb, article, categoryId, keyField, keyWord);
        if ("DESC".equalsIgnoreCase(sortOrder)) {
            predicates.add(cb.lessThan(article.get("createdDate"), createdDate));
            cq.orderBy(cb.desc(article.get("createdDate")));
        } else {
            predicates.add(cb.greaterThan(article.get("createdDate"), createdDate));
            cq.orderBy(cb.asc(article.get("createdDate")));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Article> query = em.createQuery(cq);
        query.setMaxResults(1);

        return query.getResultList().stream().findFirst();
    }

}
