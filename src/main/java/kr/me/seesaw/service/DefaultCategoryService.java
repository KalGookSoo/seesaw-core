package kr.me.seesaw.service;

import kr.me.seesaw.command.CreateCategoryCommand;
import kr.me.seesaw.command.MoveCategoryCommand;
import kr.me.seesaw.command.UpdateCategoryCommand;
import kr.me.seesaw.core.hierarchy.HierarchicalFactory;
import kr.me.seesaw.domain.Category;
import kr.me.seesaw.domain.Site;
import kr.me.seesaw.model.CategoryModel;
import kr.me.seesaw.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@Transactional
@RequiredArgsConstructor
@Service
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryModel createCategory(CreateCategoryCommand command) {
        Category category = new Category();
        category.setName(command.getName());
        category.setDescription(command.getDescription());
        category.setType(command.getType());
        category.setSiteExposed(command.isSiteExposed());
        category.setSiteExposedOrder(command.getSiteExposedOrder());
        category.setExposed(command.isExposed());
        category.setSequence(command.getSequence());

        Site site = new Site();
        site.setId(command.getSiteId());
        category.setSite(site);

        if (command.getParentId() != null) {
            Category parent = new Category();
            parent.setId(command.getParentId());
            category.setParent(parent);
        }

        Category savedCategory = categoryRepository.save(category);
        return new CategoryModel(savedCategory);
    }

    @Transactional(readOnly = true)
    @Override
    public CategoryModel getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 카테고리가 존재하지 않습니다. id: " + id));
        return new CategoryModel(category);
    }

    @Override
    public CategoryModel updateCategory(String id, UpdateCategoryCommand command) {
        Category category = categoryRepository.getReferenceById(id);
        category.setName(command.getName());
        category.setDescription(command.getDescription());
        category.setType(command.getType());
        category.setSiteExposed(command.isSiteExposed());
        category.setSiteExposedOrder(command.getSiteExposedOrder());
        category.setExposed(command.isExposed());
        category.setSequence(command.getSequence());

        if (category.getSite() == null) {
            category.setSite(new Site());
        }
        category.getSite().setId(command.getSiteId());

        if (command.getParentId() != null) {
            Category parent = new Category();
            parent.setId(command.getParentId());
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        Category updatedCategory = categoryRepository.save(category);
        categoryRepository.flush();
        return new CategoryModel(updatedCategory);
    }

    @Override
    public void deleteCategoryById(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryModel> getCategoriesBySiteId(String siteId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "sequence");
        Collection<Category> categories = categoryRepository.findAllBySiteId(siteId, sort);
        List<CategoryModel> models = categories.stream()
                .map(CategoryModel::new)
                .toList();
        return HierarchicalFactory.build(models);
    }

    @Override
    public CategoryModel moveCategory(String id, MoveCategoryCommand command) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 카테고리가 존재하지 않습니다. id: " + id));
        category.setSequence(command.getSequence());
        if (command.getParentId() != null) {
            Category parent = new Category();
            parent.setId(command.getParentId());
            category.setParent(parent);
        } else {
            category.setParent(null);
        }
        Category updatedCategory = categoryRepository.save(category);
        return new CategoryModel(updatedCategory);
    }

}
