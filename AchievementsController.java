package com.example.demo.controller;

import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.HighLowStats;
import com.example.demo.model.Medal;
import com.example.demo.repository.HighLowStatsRepository;
import com.example.demo.service.MedalService;

@Controller
public class AchievementsController {

    @Autowired
    private HighLowStatsRepository highLowStatsRepository;
    
    @Autowired
    private MedalService medalService;

    @GetMapping("/achievements")
    public String achievements(Model model, HttpSession session) {
        //データベースからSessionに保存
        String loginId = (String) session.getAttribute("loginID");
        String nickname = (String)session.getAttribute("nickname");
        
        if (loginId == null) {
            return "redirect:/login"; 
        }
        
        //所持メダル データ取得
        Optional<Medal> myMedalOptional = medalService.getMyMedalInfo(loginId);
        Integer currentMedals = myMedalOptional.map(Medal::getMyMedal).orElse(0);
        model.addAttribute("myMedals", currentMedals);
        
        //High&Low データ取得
        HighLowStats stats = highLowStatsRepository.findById(loginId).orElse(null);
        if (stats != null) {
            model.addAttribute("hlPlayCount", stats.getHlPlayCount());
            model.addAttribute("hlGetMedal", stats.getHlGetMedal());
            model.addAttribute("hlBetMedal", stats.getHlBetMedal());
        } else {
            // 初回プレイ前などでデータがない場合は0を表示
            model.addAttribute("hlPlayCount", 0);
            model.addAttribute("hlGetMedal", 0);
            model.addAttribute("hlBetMedal", 0);
        }
        
        //ユーザー名 データ取得
        model.addAttribute("nickname", nickname);
        
        return "achievements";
    }
}
