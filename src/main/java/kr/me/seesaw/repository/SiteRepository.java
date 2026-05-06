package kr.me.seesaw.repository;

import kr.me.seesaw.domain.Site;

import java.util.List;
import java.util.Optional;

public interface SiteRepository {

    List<Site> findAll();

    Site getReferenceById(String id);

    Site insert(Site site);

    Optional<Site> findById(String id);

    Optional<Site> findByDomainName(String domainName);

    List<Site> findAllByIdIn(List<String> ids);

    void deleteById(String id);

    Site update(Site site);

}
