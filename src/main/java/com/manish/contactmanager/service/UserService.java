package com.manish.contactmanager.service;

import com.manish.contactmanager.dao.UserRepository;
import com.manish.contactmanager.model.User;
import com.manish.contactmanager.utils.CustomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final CustomUtils customUtils;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BCryptPasswordEncoder tokenObject;


    @Autowired
    public UserService(UserRepository userRepository, CustomUtils customUtils){
        this.userRepository = userRepository;
        this.customUtils = customUtils;
        this.tokenObject = new BCryptPasswordEncoder();
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Map<String,Object> getAllUser(String authorizationHeader) {
        Map<String,Object> res = customUtils.requireSignin(authorizationHeader,tokenObject,"admin");
        if(res != null) {
            return res;
        }

        res = new HashMap<>();

        res.put("code", 200);
        res.put("data", userRepository.findAll().stream().toList());

        return res;
    }

    public Map<String,Object> getUserById(long id, String authorizationHeader) {
        Map<String,Object> res = customUtils.requireSignin(authorizationHeader,tokenObject,"any");
        if(res != null) {
            return res;
        }

        Optional<User> currentUser = userRepository.findById(id);
        currentUser.get().setPassword("");

        res = new HashMap<>();

        res.put("code", 200);
        res.put("data", currentUser.get());

        return res;
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

    public Map<String, Object> updateUserService(long id,Map<String, String> credential, String authorizationHeader) {
        Map<String,Object> res = customUtils.requireSignin(authorizationHeader,tokenObject,"any");
        if(res != null) {
            return res;
        }

        Optional<User> currentUser = userRepository.findById(id);

        if(currentUser.isPresent()){
            currentUser.get().setFirstName(credential.get("first_name"));
            currentUser.get().setLastName(credential.get("last_name"));
            currentUser.get().setEmail(credential.get("email"));

            userRepository.save(currentUser.get());

            res = new HashMap<>();

            res.put("code", 200);
            res.put("msg","User updated");

            return res;
        }

        res = new HashMap<>();

        res.put("code", 400);
        res.put("msg", "User dose not exist");

        return res;
    }

    public Map<String, Object> deleteUserService(long id, String authorizationHeader) {
        Map<String,Object> res = customUtils.requireSignin(authorizationHeader,tokenObject,"admin");
        if(res != null) {
            return res;
        }

        Optional<User> currentUser = userRepository.findById(id);
        if(currentUser.isPresent()){
            userRepository.deleteById(currentUser.get().getUserId());

            res = new HashMap<>();

            res.put("code", 400);
            res.put("msg", "User deleted");

            return res;
        }

        res = new HashMap<>();

        res.put("code", 400);
        res.put("msg", "User dose not exist");

        return res;
    }
}
