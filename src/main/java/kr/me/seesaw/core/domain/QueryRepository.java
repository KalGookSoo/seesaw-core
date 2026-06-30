package kr.me.seesaw.core.domain;

import java.util.List;

public interface QueryRepository<T, Q> {

    List<T> search(int offset, int limit, String sort, Q q);

    long count(Q q);

}
