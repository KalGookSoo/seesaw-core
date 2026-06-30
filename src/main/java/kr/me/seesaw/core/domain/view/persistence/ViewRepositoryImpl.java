package kr.me.seesaw.core.domain.view.persistence;

import kr.me.seesaw.core.domain.view.View;
import kr.me.seesaw.core.domain.view.ViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ViewRepositoryImpl implements ViewRepository {

    private final JpaViewRepository jpaViewRepository;

    @Override
    public View save(View view) {
        return jpaViewRepository.save(view);
    }

    @Override
    public Optional<View> findById(String id) {
        return jpaViewRepository.findById(id);
    }

    @Override
    public List<View> findAll() {
        return jpaViewRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        jpaViewRepository.deleteById(id);
    }

    @Override
    public List<View> findAllByArticleIdIn(List<String> articleIds) {
        return jpaViewRepository.findAllByArticleIdIn(articleIds);
    }

    @Override
    public void deleteAllInBatch(Iterable<View> entities) {
        jpaViewRepository.deleteAllInBatch(entities);
    }

}
