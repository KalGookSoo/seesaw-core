package kr.me.seesaw.context;

import kr.me.seesaw.model.CategoryModel;
import kr.me.seesaw.model.SiteModel;

import java.util.List;
import java.util.Map;

public interface SiteContext {

    SiteModel getSite();

    Map<String, CategoryModel> getAllCategories();

    List<CategoryModel> getNestedCategories();

}
