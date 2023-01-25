package com.manish.contactmanager.utils;

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
public class CustomUtils {

    private final UserRepository userRepository;

    @Autowired
    public CustomUtils(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Map<String, Object> requireSignin(String header, BCryptPasswordEncoder tokenObject, String requiredRole){
        String token = header.substring(7);
        List<User> userList = userRepository.findAll().stream().toList();
        long currentUserId = -1;

        for (User user:userList) {
            if(tokenObject.matches(Long.toString(user.getId()),token))
                currentUserId = user.getId();
        }

        if(currentUserId == -1){
            Map<String,Object> res = new HashMap<>();

            res.put("code",400);
            res.put("msg", "You are not authenticated");

            return res;
        }

        Optional<User> currentUser = userRepository.findById(currentUserId);

        if(currentUser.isPresent()){
            if(!requiredRole.equals("any")){
                if(!currentUser.get().getRole().equals(requiredRole)){
                    Map<String,Object> res = new HashMap<>();

                    res.put("code",400);
                    res.put("msg", "You are not authorized");

                    return res;
                }
            }

            return null;
        }

        Map<String,Object> res = new HashMap<>();

        res.put("code",400);
        res.put("msg", "user not found");

        return res;
    }
}
