package com.project.ShoppingCart.controller;

import com.project.ShoppingCart.common.ApiResponse;
import com.project.ShoppingCart.model.Category;
import com.project.ShoppingCart.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "New Category Created"), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public List<Category> getAllCategory(){
        return categoryService.getAllCategory();
    }


    @PutMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") int categoryId, @RequestBody Category category){
        if(!categoryService.findById(categoryId)){
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category with this id does not exist"), HttpStatus.CONFLICT);
        }
        categoryService.updateCategory(categoryId, category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Category has been updated"), HttpStatus.OK);
    }
}
