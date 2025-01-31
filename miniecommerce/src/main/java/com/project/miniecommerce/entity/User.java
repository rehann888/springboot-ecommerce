package com.project.miniecommerce.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User implements Serializable {

    public User(String username) {
        this.Id=username;
    }
    @Id
    private String Id;

    @JsonIgnore
    private String password;

    private String name;
    private String email;
    @JsonIgnore
    private String address;
    @JsonIgnore
    private String contact;
    @JsonIgnore
    private String roles;
    @JsonIgnore
    private boolean isActive;
}
