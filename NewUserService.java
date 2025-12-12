package com.example.demo.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class NewUserService {
	
	@Autowired
	private final UserRepository userRepository;
	
	public NewUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User registerNewUser(User newUser) {
		// 重複チェック
		Optional<User> exisitingUser = userRepository.findByLoginID(newUser.getLoginID());
		if(exisitingUser.isPresent()) {
			return null;
		}
		
		Date currentDate = Date.valueOf(LocalDate.now());
        newUser.setRegistDate(currentDate);
        newUser.setUpdateDate(currentDate);
        
        return userRepository.save(newUser);
	}
}
