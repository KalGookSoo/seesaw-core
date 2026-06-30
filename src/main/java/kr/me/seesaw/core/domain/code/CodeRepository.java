package kr.me.seesaw.core.domain.code;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface CodeRepository {

    Code save(Code code);

    Optional<Code> findById(String id);

    List<Code> findAll();

    List<Code> findAll(Sort sort);

    void deleteById(String id);

    List<Code> findByName(String name);

}
