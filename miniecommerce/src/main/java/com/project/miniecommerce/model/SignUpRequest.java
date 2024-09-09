package com.project.miniecommerce.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class SignUpRequest implements Serializable{
    private String username;
    private String email;
    private String password;
    private String name;
}
