package com.project.miniecommerce.entity;

import java.io.Serializable;
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
public class OrderLog implements Serializable {
    
    @Id
    private String Id;

    @JoinColumn
    @ManyToOne
    private User user;

    @JoinColumn
    @ManyToOne
    private CustomerOrder order;

    private Integer LogType;
    private String LogMessege;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
}
