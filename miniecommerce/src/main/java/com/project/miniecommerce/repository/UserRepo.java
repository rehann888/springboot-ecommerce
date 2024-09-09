package com.project.miniecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.miniecommerce.entity.User;

public interface UserRepo extends JpaRepository<User, String> {

    boolean existsByEmail(String email);
    
}
