package com.manish.contactmanager.controller;

import com.manish.contactmanager.model.User;
import com.manish.contactmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public Map<String,Object> getAllRegisteredUsers(@RequestHeader(value="Authorization") String authorizationHeader){
        return userService.getAllUser(authorizationHeader);
    }

    @GetMapping(path = "/{id}")
    public Optional<User> getCurrentUser(@PathVariable("id") long id){
        return userService.getUserById(id);
    }

    @PostMapping("/signup")
    public Map<String,Object> signUp(@RequestBody User user){
        return userService.addUserService(user);
    }

    @PostMapping("/signin")
    public Map<String,Object> signIn(@RequestBody Map<String,String> credential){
        return userService.loginUserService(credential);
    }

}
