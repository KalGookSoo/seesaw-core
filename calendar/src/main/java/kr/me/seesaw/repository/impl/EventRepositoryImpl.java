package kr.me.seesaw.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import kr.me.seesaw.domain.Article;
import kr.me.seesaw.domain.VEvent;
import kr.me.seesaw.repository.EventRepository;
import kr.me.seesaw.repository.jpa.JpaEventRepository;
import kr.me.seesaw.dto.query.EventQuery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private final JpaEventRepository jpaEventRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public VEvent save(VEvent event) {
        return jpaEventRepository.save(event);
    }

    @Override
    public Optional<VEvent> findById(String id) {
        return jpaEventRepository.findById(id);
    }

    @Override
    public List<VEvent> findAll(EventQuery eventQuery) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<VEvent> query = cb.createQuery(VEvent.class);
        Root<VEvent> event = query.from(VEvent.class);
        Join<VEvent, Article> article = event.join("article", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(eventQuery.getCategoryId())) {
            predicates.add(cb.equal(article.get("categoryId"), eventQuery.getCategoryId()));
        }

        if (eventQuery.getStart() != null) {
            // dtStart >= search.start OR dtEnd >= search.start
            predicates.add(cb.or(
                    cb.greaterThanOrEqualTo(event.get("dtStart"), eventQuery.getStart()),
                    cb.greaterThanOrEqualTo(event.get("dtEnd"), eventQuery.getStart())
            ));
        }

        if (eventQuery.getEnd() != null) {
            // dtStart <= search.end
            predicates.add(cb.lessThanOrEqualTo(event.get("dtStart"), eventQuery.getEnd()));
        }

        if (StringUtils.hasText(eventQuery.getQuery())) {
            String pattern = "%" + eventQuery.getQuery() + "%";
            predicates.add(cb.or(
                    cb.like(event.get("summary"), pattern),
                    cb.like(event.get("description"), pattern),
                    cb.like(article.get("title"), pattern),
                    cb.like(article.get("content"), pattern)
            ));
        }

        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(cb.asc(event.get("dtStart")));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void delete(VEvent event) {
        jpaEventRepository.delete(event);
    }

}
