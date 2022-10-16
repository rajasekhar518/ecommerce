package com.demo.backend.controller;

import com.demo.backend.model.User;
import com.demo.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/")
    public User createUser(@RequestBody User user){
        return userService.saveUser(user);
    }

}
