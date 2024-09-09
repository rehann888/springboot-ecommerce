package com.project.miniecommerce.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.miniecommerce.entity.User;
import com.project.miniecommerce.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class UserController {
    
    @Autowired
    public UserService userService;

    @GetMapping("/user")
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/user/{Id}")
    public User findById(@PathVariable("Id") String Id){
        return userService.findById(Id);
    }

    @PostMapping ("/user")
    public User create(@RequestBody User user) throws BadRequestException{
        return userService.create(user);
    }

    @PutMapping("/user")
    public User edit(@RequestBody User user) throws BadRequestException{
        return userService.edit(user);
    }

    @DeleteMapping("/user/{Id}")
    public void deleteById(@PathVariable("Id") String Id){
        userService.deleteById(Id);
    }
}