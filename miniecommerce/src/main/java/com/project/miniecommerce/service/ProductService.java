package com.project.miniecommerce.service;

import java.util.List;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.project.miniecommerce.entity.Product;
import com.project.miniecommerce.exception.ResourceNotFoundException;
import com.project.miniecommerce.repository.CategoryRepo;
import com.project.miniecommerce.repository.ProductRepo;


@Service
public class ProductService {

    @Autowired
    private CategoryRepo categoryRepo;
    
    @Autowired
    private ProductRepo productRepo;

    public List<Product> findAll(){
        return productRepo.findAll();
    }

    public Product findById(String Id){
        return productRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Product with id " + Id + " not found"));
    }

    public Product create (Product product) throws BadRequestException{
        if (!StringUtils.hasText(product.getName())) {
            throw new BadRequestException("product name must be filled");
        }

        if (product.getCategory()==null) {
            throw new BadRequestException("category name must be filled");
        }

        if (!StringUtils.hasText(product.getCategory().getId())) {
            throw new BadRequestException("category id must be filled");
        }

        categoryRepo.findById(product.getCategory().getId())
                    .orElseThrow(() -> new BadRequestException("category id " + product.getCategory().getId() + " not found"));


        product.setId(UUID.randomUUID().toString());
        return productRepo.save(product);
    }

    public Product edit(Product product) throws BadRequestException{

        if (!StringUtils.hasText(product.getId())) {
            throw new BadRequestException("product ID must be filled");
        }


        if (!StringUtils.hasText(product.getName())) {
            throw new BadRequestException("product name must be filled");
        }

        if (product.getCategory()==null) {
            throw new BadRequestException("category name must be filled");
        }

        if (!StringUtils.hasText(product.getCategory().getId())) {
            throw new BadRequestException("category id must be filled");
        }

        categoryRepo.findById(product.getCategory().getId())
                    .orElseThrow(() -> new BadRequestException("category id " + product.getCategory().getId() + " not found"));


        return productRepo.save(product);
    }

    public Product changePicture(String Id, String Picture){
        Product product = findById(Id);
        product.setPicture(Picture);
        return productRepo.save(product);
    }

    public void deleteById(String Id){
        productRepo.deleteById(Id);
    }
}


