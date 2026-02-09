package kr.me.seesaw.repository.impl;

import kr.me.seesaw.domain.VEvent;
import kr.me.seesaw.repository.VEventRepository;
import kr.me.seesaw.repository.jpa.JpaVEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VEventRepositoryImpl implements VEventRepository {

    private final JpaVEventRepository jpaVEventRepository;

    @Override
    public VEvent save(VEvent event) {
        return jpaVEventRepository.save(event);
    }

    @Override
    public void delete(VEvent event) {
        jpaVEventRepository.delete(event);
    }

    @Override
    public Optional<VEvent> findById(String id) {
        return jpaVEventRepository.findById(id);
    }

    @Override
    public VEvent getReferenceById(String id) {
        return jpaVEventRepository.getReferenceById(id);
    }

}
