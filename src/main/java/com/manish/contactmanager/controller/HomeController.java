package com.manish.contactmanager.controller;

import com.manish.contactmanager.model.User;
import com.manish.contactmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class HomeController {

    private final UserService userService;

    @Autowired
    public HomeController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody User user){
        return userService.addUserService(user);
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody Map<String,String> credential){
        return userService.loginUserService(credential);
    }
}
