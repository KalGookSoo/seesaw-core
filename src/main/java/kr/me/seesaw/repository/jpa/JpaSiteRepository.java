package kr.me.seesaw.repository.jpa;

import kr.me.seesaw.domain.Site;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface JpaSiteRepository extends Repository<Site, String> {

    List<Site> findAll();

    Site getReferenceById(String id);

    Site save(Site site);

    Optional<Site> findById(String id);

    Optional<Site> findByDomainName(String domainName);

    List<Site> findAllByIdIn(List<String> ids);

    void deleteById(String id);

    Site saveAndFlush(Site site);

}
