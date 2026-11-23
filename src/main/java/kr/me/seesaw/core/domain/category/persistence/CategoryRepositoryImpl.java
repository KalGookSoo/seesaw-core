package kr.me.seesaw.core.domain.category.persistence;

import kr.me.seesaw.core.domain.category.Category;
import kr.me.seesaw.core.domain.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
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
    public Collection<Category> findAllBySiteIdAndExposed(String siteId, boolean exposed, Sort sort) {
        return jpaCategoryRepository.findAllBySiteIdAndExposed(siteId, exposed, sort);
    }

    @Override
    public List<Category> search(String siteId, String keyword, Pageable pageable) {
        return jpaCategoryRepository.search(siteId, keyword, pageable);
    }

    @Override
    public long countSearch(String siteId, String keyword) {
        return jpaCategoryRepository.countSearch(siteId, keyword);
    }

    @Override
    public void flush() {
        jpaCategoryRepository.flush();
    }

}
