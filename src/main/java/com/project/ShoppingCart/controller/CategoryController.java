package com.project.ShoppingCart.controller;

import com.project.ShoppingCart.model.Category;
import com.project.ShoppingCart.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    public String createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return "success";
    }

    @GetMapping("/getAll")
    public List<Category> getAllCategory(){

        return categoryService.getAllCategory();
    }
}
