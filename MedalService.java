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
	
	/**
     * ログインIDからユーザーのメダル情報を取得します。
     * @param loginID ログインユーザーのID
     * @return Medal情報のOptional
     */
	public Optional<Medal> getMyMedalInfo(String loginID){
		return medalRepository.findByLoginId(loginID);
	}
	
	public List<Medal> getTopRanking() {
		return medalRepository.findTop3ByOrderByMyMedalDesc();
	}
	
	public Medal findByLoginId(String loginId) {
		return medalRepository.findByLoginId(loginId).orElse(null);
	}
	
	public void updateMedal(Medal medal) {
        if (medal != null) {
            medalRepository.save(medal);  // save が INSERT/UPDATE を兼ねる
        }
    }
}
