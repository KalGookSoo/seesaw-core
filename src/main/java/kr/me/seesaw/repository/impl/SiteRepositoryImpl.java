package kr.me.seesaw.repository.impl;

import kr.me.seesaw.domain.Site;
import kr.me.seesaw.repository.SiteRepository;
import kr.me.seesaw.repository.jpa.JpaSiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SiteRepositoryImpl implements SiteRepository {

    private final JpaSiteRepository jpaSiteRepository;

    @Override
    public List<Site> findAll() {
        return jpaSiteRepository.findAll();
    }

    @Override
    public Site getReferenceById(String id) {
        return jpaSiteRepository.getReferenceById(id);
    }

    @Override
    public Site insert(Site site) {
        return jpaSiteRepository.save(site);
    }

    @Override
    public Optional<Site> findById(String id) {
        return jpaSiteRepository.findById(id);
    }

    @Override
    public Optional<Site> findByDomainName(String domainName) {
        return jpaSiteRepository.findByDomainName(domainName);
    }

    @Override
    public List<Site> findAllByIdIn(List<String> ids) {
        return jpaSiteRepository.findAllByIdIn(ids);
    }

    @Override
    public void deleteById(String id) {
        jpaSiteRepository.deleteById(id);
    }

    @Override
    public Site update(Site site) {
        return jpaSiteRepository.saveAndFlush(site);
    }

}
