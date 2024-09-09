package com.project.miniecommerce.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.miniecommerce.entity.Product;
import com.project.miniecommerce.service.ProductService;

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
public class ProductController {
    
    @Autowired
    public ProductService productService;

    @GetMapping("/product")
    public List<Product> findAll(){
        return productService.findAll();
    }

    @GetMapping("/product/{Id}")
    public Product findById(@PathVariable("Id") String Id){
        return productService.findById(Id);
    }

    @PostMapping ("/product")
    public Product create(@RequestBody Product product) throws BadRequestException{
        return productService.create(product);
    }

    @PutMapping("/product")
    public Product edit(@RequestBody Product product) throws BadRequestException{
        return productService.edit(product);
    }

    @DeleteMapping("/product/{Id}")
    public void deleteById(@PathVariable("Id") String Id){
        productService.deleteById(Id);
    }
}