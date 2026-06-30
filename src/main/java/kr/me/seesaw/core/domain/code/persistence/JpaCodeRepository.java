package kr.me.seesaw.core.domain.code.persistence;

import kr.me.seesaw.core.domain.code.Code;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface JpaCodeRepository extends Repository<Code, String> {

    Code save(Code code);

    Optional<Code> findById(String id);

    List<Code> findAll();

    List<Code> findAll(Sort sort);

    void deleteById(String id);

    List<Code> findByName(String name);

}
