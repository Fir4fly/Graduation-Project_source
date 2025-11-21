package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Medal;

@Repository
public interface MedalRepository extends JpaRepository<Medal, String> {

    // ログインIDに基づいてメダル情報を検索
    Optional<Medal> findByLoginId(String loginId);
    
    // ランキング表示用にメダル数が多い順に全ユーザーのリストを取得
    List<Medal> findTop3ByOrderByMyMedalDesc(); // 所持メダル数で降順ソートし、上位3件を取得
    
    List<Medal> findTop50ByOrderByMyMedalDesc();

}