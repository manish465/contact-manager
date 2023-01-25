package com.manish.contactmanager.service;

import com.manish.contactmanager.dao.UserRepository;
import com.manish.contactmanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private final BCryptPasswordEncoder tokenObject;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
        this.tokenObject = new BCryptPasswordEncoder();
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<User> getAllUser() {
        return userRepository.findAll().stream().toList();
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public Map<String,Object> addUserService(User user){
        if(!userRepository.findByEmail(user.getEmail()).isEmpty()){
            Map<String,Object> res = new HashMap<>();

            res.put("code",400);
            res.put("msg","User already exist");

            return res;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        Map<String,Object> res = new HashMap<>();

        res.put("code",200);
        res.put("msg","User registed");

        return res;
    }

    public Map<String,Object> loginUserService(Map<String,String> credential) {
        User currentUser = userRepository.findByEmail(credential.get("email")).get(0);

        if(currentUser.getEmail() == null){
            Map<String,Object> res = new HashMap<>();

            res.put("code",400);
            res.put("msg","User dose not exist");

            return res;
        }

        if(passwordEncoder.matches(credential.get("password"),currentUser.getPassword())){
            Map<String,Object> res = new HashMap<>();

            res.put("code",200);
            res.put("token",tokenObject.encode(Long.toString(currentUser.getUserId())));
            res.put("msg","User logged in");

            return res;
        }

        Map<String,Object> res = new HashMap<>();

        res.put("code",400);
        res.put("token","Invalid credential");

        return res;
    }
}
