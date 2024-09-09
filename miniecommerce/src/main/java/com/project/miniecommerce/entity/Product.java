package com.project.miniecommerce.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Product implements Serializable{

    @Id
    private String Id;
    private String name;    
    private String Description;
    private String Picture;

    @JoinColumn
    @ManyToOne
    private Category category;
    private BigDecimal price;
    
    private Double stock;
}
