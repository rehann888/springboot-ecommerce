package com.project.miniecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.miniecommerce.entity.OrderItem;

public interface OrderitemRepo extends JpaRepository<OrderItem, String> {
    
}
