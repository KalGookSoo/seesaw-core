package kr.me.seesaw.repository.impl;

import kr.me.seesaw.domain.Code;
import kr.me.seesaw.repository.CodeRepository;
import kr.me.seesaw.repository.jpa.JpaCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CodeRepositoryImpl implements CodeRepository {

    private final JpaCodeRepository jpaCodeRepository;

    @Override
    public Code save(Code code) {
        return jpaCodeRepository.save(code);
    }

    @Override
    public Optional<Code> findById(String id) {
        return jpaCodeRepository.findById(id);
    }

    @Override
    public List<Code> findAll() {
        return jpaCodeRepository.findAll();
    }

    @Override
    public List<Code> findAll(Sort sort) {
        return jpaCodeRepository.findAll(sort);
    }

    @Override
    public void deleteById(String id) {
        jpaCodeRepository.deleteById(id);
    }

    @Override
    public List<Code> findByName(String name) {
        return jpaCodeRepository.findByName(name);
    }

}
