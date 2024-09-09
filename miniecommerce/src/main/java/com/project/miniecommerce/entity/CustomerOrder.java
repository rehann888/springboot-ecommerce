package com.project.miniecommerce.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


import com.project.miniecommerce.model.OrderStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;


@Data
@Entity
public class CustomerOrder implements Serializable {

    @Id
    private String Id;
    private String number;

    @Temporal(TemporalType.DATE)
    private Date date;

    @JoinColumn
    @ManyToOne
    private User user;

    private String shippingAddress;
    private BigDecimal amount;
    private BigDecimal shippingCost;
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    
}
