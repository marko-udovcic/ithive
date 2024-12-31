package com.itm.ithive.service.interfaces;

import com.itm.ithive.model.Category;

import java.util.List;


public interface CategoryService {
    List<Category> findAllCategories();

    Category saveCategory(Category category);

    Category updateCategory(Category category, long id);

    void deleteCategory(long id);

    Category getCategoryById(Long categoryId);

}
