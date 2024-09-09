package com.project.miniecommerce.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class OrderRequest implements Serializable{
    
    private BigDecimal shippingCost;
    private String shippingAddress;
    private List<CartRequest> items;
    
}
