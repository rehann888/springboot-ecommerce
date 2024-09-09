package com.project.miniecommerce.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
public class Cart implements Serializable {
    
    @Id
    private String Id;

    @JoinColumn
    @ManyToOne
    private Product product;

    @JoinColumn
    @ManyToOne
    private User user;

    private Double quantity;
    private BigDecimal price;
    private BigDecimal total;

    @Temporal(TemporalType.TIMESTAMP)
    private Date CreatedDate;
}
