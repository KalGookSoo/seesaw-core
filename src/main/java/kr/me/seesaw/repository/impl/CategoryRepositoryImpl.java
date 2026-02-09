package kr.me.seesaw.repository.impl;

import kr.me.seesaw.domain.Category;
import kr.me.seesaw.repository.CategoryRepository;
import kr.me.seesaw.repository.jpa.JpaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JpaCategoryRepository jpaCategoryRepository;

    @Override
    public Category save(Category category) {
        return jpaCategoryRepository.save(category);
    }

    @Override
    public Category getReferenceById(String id) {
        return jpaCategoryRepository.getReferenceById(id);
    }

    @Override
    public Optional<Category> findById(String id) {
        return jpaCategoryRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        jpaCategoryRepository.deleteById(id);
    }

    @Override
    public Collection<Category> findAllBySiteId(String siteId, Sort sort) {
        return jpaCategoryRepository.findAllBySiteId(siteId, sort);
    }

    @Override
    public void flush() {
        jpaCategoryRepository.flush();
    }

}
