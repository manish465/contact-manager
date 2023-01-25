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
    public Map<String,Object> getCurrentUser(@PathVariable("id") long id, @RequestHeader(value="Authorization") String authorizationHeader){
        return userService.getUserById(id,authorizationHeader);
    }

    @PostMapping("/signup")
    public Map<String,Object> signUp(@RequestBody User user){
        return userService.addUserService(user);
    }

    @PostMapping("/signin")
    public Map<String,Object> signIn(@RequestBody Map<String,String> credential){
        return userService.loginUserService(credential);
    }

    @PutMapping(path = "/{id}")
    public Map<String,Object> updateUserById(@PathVariable("id") long id,
                                             @RequestBody Map<String,String> newUserCred,
                                             @RequestHeader(value="Authorization") String authorizationHeader){
        return userService.updateUserService(id,newUserCred, authorizationHeader);
    }

    @DeleteMapping(path = "/{id}")
    public Map<String, Object> deleteUserById(@PathVariable("id") long id, @RequestHeader(value="Authorization") String authorizationHeader){
        return userService.deleteUserService(id,authorizationHeader);
    }
}
