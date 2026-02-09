package kr.me.seesaw.repository;

import java.util.List;

public interface QueryRepository<T, Q> {

    List<T> search(int offset, int limit, String sort, Q q);

    long count(Q q);

}
