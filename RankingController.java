// src/main/java/com/example/demo/controller/RankingController.java (新規作成)

package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.RankingDto;
import com.example.demo.service.RankingService;

@Controller
public class RankingController {

    private final RankingService rankingService;

    // コンストラクタインジェクション
    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/ranking")
    public String getRanking(Model model) {
        // 【重要】ログインIDの取得処理を実装してください。
        // Spring Securityなどを使用し、セッション/認証情報から取得します。
        // デバッグ/モックアップのために、ここでは仮のIDを使用します。
        // 例: 認証情報からログインIDを取得: String currentLoginId = auth.getName();
        String currentLoginId = "testuser";
        
        // 1. 自分のメダル所持数を取得 (My Medals表示用)
        int myMedals = rankingService.getMyMedals(currentLoginId);
        
        // 2. ランキングリスト（Top 50）を取得
        List<RankingDto> rankingList = rankingService.getFullRankingList(currentLoginId);
        
        // 3. 自分のランキング情報（画面下部の固定表示用）を取得
        RankingDto myRankingInfo = rankingService.getMyRankingInfo(currentLoginId, rankingList);
        
        // 4. データをModelに追加
        model.addAttribute("myMedals", myMedals); // My Medals
        model.addAttribute("rankingList", rankingList); // Top 50のリスト
        model.addAttribute("myRankingInfo", myRankingInfo); // 自分の順位情報 (画面下部)
        
        return "ranking";
    }
}