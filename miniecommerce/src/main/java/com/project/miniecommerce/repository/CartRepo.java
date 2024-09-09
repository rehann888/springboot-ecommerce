package com.project.miniecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.project.miniecommerce.entity.Cart;
import java.util.List;


public interface CartRepo extends JpaRepository<Cart, String> {

    Optional<Cart> findByUserIdAndProductId(String username, String productId);

    List<Cart>findByUserId(String username);
    
}
