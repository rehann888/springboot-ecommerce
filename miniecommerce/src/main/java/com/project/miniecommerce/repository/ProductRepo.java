package com.project.miniecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.miniecommerce.entity.Product;

public interface ProductRepo extends JpaRepository<Product, String>{
    
}
