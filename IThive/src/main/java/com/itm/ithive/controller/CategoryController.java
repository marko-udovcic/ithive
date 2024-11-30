package com.itm.ithive.controller;

import com.itm.ithive.model.Category;
import com.itm.ithive.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //

    @GetMapping
    public List<Category> findAllCategories () {
        return categoryService.findAllCategories();
    }

    @PostMapping
    public Category saveCategory (@RequestBody Category category){
        return categoryService.saveCategory(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory (@RequestBody Category category, @PathVariable long id){
        return categoryService.updateCategory(category, id);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory (@PathVariable long id){
        categoryService.deleteCategory(id);
    }


}
