package kr.me.seesaw.core.domain.site;

import java.util.List;

public interface SiteQueryRepository {

    List<Site> search(int offset, int limit, String sort);

    long count();

}
