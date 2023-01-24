package com.manish.contactmanager.service;

import com.manish.contactmanager.dao.UserRepository;
import com.manish.contactmanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String addUserService(User user){
        if(!userRepository.findByEmail(user.getEmail()).isEmpty()){
            return "User already exist";
        }

        userRepository.save(user);
        return "User Registered";
    }

    public List<User> getAllUser() {
        return userRepository.findAll().stream().toList();
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }
}
