package com.project.miniecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.miniecommerce.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, String>{
   
}
