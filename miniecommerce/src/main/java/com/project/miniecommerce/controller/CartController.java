package com.project.miniecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.miniecommerce.entity.Cart;
import com.project.miniecommerce.model.CartRequest;
import com.project.miniecommerce.security.service.UserDetailImplementation;
import com.project.miniecommerce.service.CartService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class CartController {

    @Autowired
    private CartService cartService;
    
    @GetMapping("/cart")
    public List<Cart> findByUserId(@AuthenticationPrincipal UserDetailImplementation user){
        return cartService.findByUserId(user.getUsername());
    }

    @PostMapping("/cart")
    public Cart create(@AuthenticationPrincipal UserDetailImplementation user, @RequestBody CartRequest request) {
        return cartService.addCart(user.getUsername(), request.getProductId(), request.getQuantity());
    }

    @PatchMapping("/cart/{productId}")
    public Cart update(@AuthenticationPrincipal UserDetailImplementation user, @PathVariable("productId") String productId, 
            @RequestParam("quantity") Double quantity){
        return cartService.updateQuantity(user.getUsername(), productId, quantity);
    }

    @DeleteMapping("/cart/{productId}")
    public void delete(@AuthenticationPrincipal UserDetailImplementation user, @PathVariable("productId") String productId){
        cartService.delete(user.getUsername(), productId);
    }


    

}
