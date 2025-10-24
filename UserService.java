package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String loginID, String loginPass) {
        User user = userRepository.findByUsername(loginID);
        if (user != null && user.getLoginPass().equals(loginPass)) {
            return user;
        }
        return null;
    }
}
