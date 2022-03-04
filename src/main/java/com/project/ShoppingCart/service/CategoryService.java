package com.project.ShoppingCart.service;

import com.project.ShoppingCart.model.Category;
import com.project.ShoppingCart.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepo categoryRepo;

    public void createCategory(Category category){

        categoryRepo.save(category);
    }

    public List<Category> getAllCategory(){

        return categoryRepo.findAll();
    }
}