package com.project.miniecommerce.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Category implements Serializable{

    @Id
    private String Id;
    private String name;
}
