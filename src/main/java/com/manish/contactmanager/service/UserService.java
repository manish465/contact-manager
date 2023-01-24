package com.manish.contactmanager.service;

import com.manish.contactmanager.dao.UserRepository;
import com.manish.contactmanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<User> getAllUser() {
        return userRepository.findAll().stream().toList();
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public String addUserService(User user){
        if(!userRepository.findByEmail(user.getEmail()).isEmpty()){
            return "User already exist";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User Registered";
    }

    public String loginUserService(Map<String,String> credential) {
        User currentUser = userRepository.findByEmail(credential.get("email")).get(0);

        if(currentUser.getEmail() == null){
            return "User dose not exist";
        }

        if(passwordEncoder.matches(credential.get("password"),currentUser.getPassword())){
            return "User logged in";
        }

        return "Invalid credential";
    }
}
