package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Medal;
import com.example.demo.repository.MedalRepository;

@Service

public class MedalService {
	@Autowired
	private MedalRepository medalRepository;
	
	public Optional<Medal> getMyMedalInfo(String loginID){
		return medalRepository.findByLoginID(loginID);
	}
	
	public List<Medal> getTopRanking() {
		return medalRepository.findTop3ByOrderByMyMedalDesc();
	}
}
