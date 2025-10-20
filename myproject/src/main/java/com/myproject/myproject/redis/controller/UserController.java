package com.myproject.myproject.redis.controller;

import com.myproject.myproject.redis.entity.User;
import com.myproject.myproject.redis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/cache")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user){

        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }
}
