package kr.me.seesaw.service;

import kr.me.seesaw.command.CreateCategoryCommand;
import kr.me.seesaw.command.MoveCategoryCommand;
import kr.me.seesaw.command.UpdateCategoryCommand;
import kr.me.seesaw.model.CategoryModel;

import java.util.List;

public interface CategoryService {

    CategoryModel createCategory(CreateCategoryCommand command);

    CategoryModel getCategoryById(String id);

    CategoryModel updateCategory(String id, UpdateCategoryCommand command);

    void deleteCategoryById(String id);

    List<CategoryModel> getCategoriesBySiteId(String siteId);

    CategoryModel moveCategory(String id, MoveCategoryCommand command);

}
