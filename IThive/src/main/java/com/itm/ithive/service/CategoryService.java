package com.itm.ithive.service;

import com.itm.ithive.model.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
    List<Category> findAllCategories();
    Category saveCategory(Category category);
    Category updateCategory(Category category, long id);
    void deleteCategory(long id);

}
