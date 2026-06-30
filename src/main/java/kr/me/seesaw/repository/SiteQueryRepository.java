package kr.me.seesaw.repository;

import kr.me.seesaw.domain.Site;

import java.util.List;

public interface SiteQueryRepository {

    List<Site> search(int offset, int limit, String sort);

    long count();

}
