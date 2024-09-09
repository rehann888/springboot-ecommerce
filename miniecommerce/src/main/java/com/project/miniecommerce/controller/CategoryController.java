package com.project.miniecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.miniecommerce.entity.Category;
import com.project.miniecommerce.service.CategoryService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class CategoryController {
    
    @Autowired
    public CategoryService categoryService;

    @GetMapping("/category")
    public List<Category> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("/category/{Id}")
    public Category findById(@PathVariable("Id") String Id){
        return categoryService.findById(Id);
    }

    @PostMapping ("/category")
    public Category create(@RequestBody Category category){
        return categoryService.create(category);
    }

    @PutMapping("/category")
    public Category edit(@RequestBody Category category){
        return categoryService.edit(category);
    }

    @DeleteMapping("/category/{Id}")
    public void deleteById(@PathVariable("Id") String Id){
        categoryService.deleteById(Id);
    }
}