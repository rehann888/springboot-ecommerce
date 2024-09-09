package com.project.miniecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

import com.project.miniecommerce.entity.Category;
import com.project.miniecommerce.exception.ResourceNotFoundException;
import com.project.miniecommerce.repository.CategoryRepo;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepo categoryRepo;

    public Category findById(String Id){
        return categoryRepo.findById(Id)
        .orElseThrow(() -> new ResourceNotFoundException("Category with id " + Id + " not found"));
    }

    public List<Category> findAll(){
        return categoryRepo.findAll();
    }

    public Category create(Category category){
        category.setId(UUID.randomUUID().toString());
        return categoryRepo.save(category);
    }

    public Category edit(Category category){
        return categoryRepo.save(category);
    }

    public void deleteById(String Id){
        categoryRepo.deleteById(Id);
    }


}
