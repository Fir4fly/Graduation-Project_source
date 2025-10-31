package com.example.demo.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String loginID, String loginPass) {
        Optional<User> userOptional = userRepository.findByLoginID(loginID);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            if (user.getLoginPass().equals(loginPass)) {
                // パスワードが一致した場合：認証成功
                return user;
            }
        }
        return null;
    }
}
