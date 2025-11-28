// src/main/java/com/example/demo/controller/RankingController.java (新規作成)

package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.RankingDto;
import com.example.demo.service.RankingService;

@Controller
public class RankingController {

    private final RankingService rankingService;
    private final HttpSession session;

    // コンストラクタインジェクション
    public RankingController(RankingService rankingService, HttpSession session) {
        this.rankingService = rankingService;
        this.session = session;
    }

    @GetMapping("/ranking")
    public String getRanking(Model model) {
        String loginUser = (String)session.getAttribute("loginID");
        if(loginUser == null) {
        		return "redirect:/login";
        }
        String currentLoginId = loginUser;
        
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