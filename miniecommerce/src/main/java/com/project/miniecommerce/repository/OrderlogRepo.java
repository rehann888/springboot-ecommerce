package com.project.miniecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.miniecommerce.entity.OrderLog;

public interface OrderlogRepo extends JpaRepository<OrderLog, String> {
    
}
