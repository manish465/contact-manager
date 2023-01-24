package com.manish.contactmanager.controller;

import com.manish.contactmanager.model.User;
import com.manish.contactmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("api/v1/user")
    public List<User> getAllRegisteredUsers(){
        return userService.getAllUser();
    }

    @GetMapping(path = "api/v1/user/{id}")
    public Optional<User> getCurrentUser(@PathVariable("id") long id){
        return userService.getUserById(id);
    }

    @PostMapping("api/v1/signup")
    public String signUp(@RequestBody User user){
        return userService.addUserService(user);
    }
}
