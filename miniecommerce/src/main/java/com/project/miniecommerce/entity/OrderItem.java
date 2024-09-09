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
public class OrderItem implements Serializable{
    
    @Id
    private String Id;
    
    @JoinColumn
    @ManyToOne
    private CustomerOrder order;
    
    @ManyToOne
    @JoinColumn
    private Product product;
    
    private String description;
    private Double Quantity;
    private BigDecimal price;
    private BigDecimal total;

}
