package kr.me.seesaw.core.domain.site.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import kr.me.seesaw.core.domain.site.Site;
import kr.me.seesaw.core.domain.site.SiteQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class SiteQueryRepositoryImpl implements SiteQueryRepository {

    private final EntityManager em;

    @Override
    public List<Site> search(int offset, int limit, String sort) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Site> cq = cb.createQuery(Site.class);
        Root<Site> site = cq.from(Site.class);

        // 추가적인 검색 조건이 있다면 여기에 구현

        TypedQuery<Site> query = em.createQuery(cq);
        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public long count() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Site> site = cq.from(Site.class);

        cq.select(cb.count(site));

        return em.createQuery(cq).getSingleResult();
    }

}
