package com.project.miniecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import java.util.List;


import com.project.miniecommerce.entity.Cart;
import com.project.miniecommerce.entity.Product;
import com.project.miniecommerce.entity.User;
import com.project.miniecommerce.exception.BadRequestException;
import com.project.miniecommerce.repository.CartRepo;
import com.project.miniecommerce.repository.ProductRepo;

@Service
public class CartService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartRepo cartRepo;

    
    // cek ketersediaan product
    // cek apakah sudah ada dalam cart user
    // jika ada, update quantity
    // jika belum, buat baru
    @Transactional 
    public Cart addCart(String username, String productId, Double quantity){
        Product product = productRepo.findById(productId)
                .orElseThrow(()-> new BadRequestException("Product ID " + productId + " Not Found"));

        Optional<Cart> optional = cartRepo.findByUserIdAndProductId(username, productId);
        Cart cart;
        if (optional.isPresent()){
            cart = optional.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            cart.setTotal(new BigDecimal(cart.getPrice().doubleValue()*cart.getQuantity()));
            cartRepo.save(cart); 
        } else {
            cart = new Cart();
            cart.setId(UUID.randomUUID().toString());
            cart.setProduct(product);
            cart.setQuantity(quantity);
            cart.setPrice(product.getPrice());
            cart.setTotal(new BigDecimal(cart.getPrice().doubleValue()*cart.getQuantity()));
            cart.setUser(new User(username));
            cartRepo.save(cart);
        }
        return cart;
    }

    @Transactional
    public Cart updateQuantity(String username, String productId, Double quantity){
        Cart cart = cartRepo.findByUserIdAndProductId(username, productId)
            .orElseThrow(() -> new BadRequestException(
                "Product ID " + productId + " Not Found in your cart"));
        cart.setQuantity(cart.getQuantity() + quantity);
        cart.setTotal(new BigDecimal(cart.getPrice().doubleValue()*cart.getQuantity()));
        cartRepo.save(cart); 
        return cart;
    }
    
    public void delete(String username, String productId){
        Cart cart = cartRepo.findByUserIdAndProductId(username, productId)
            .orElseThrow(() -> new BadRequestException(
                "Product ID " + productId + " Not Found in your cart"));
            cartRepo.delete(cart);
    
    }

    public List<Cart> findByUserId(String username){
        return cartRepo.findByUserId(username);
    }
    
}
