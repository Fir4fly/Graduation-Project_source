package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.HighLowStats;
import com.example.demo.model.Medal;
import com.example.demo.model.MedalSnapshot;
import com.example.demo.model.MedalSnapshotId;
import com.example.demo.repository.HighLowStatsRepository;
import com.example.demo.repository.MedalSnapshotRepository;
import com.example.demo.service.MedalService;

@Controller
public class AchievementsController {

    @Autowired
    private HighLowStatsRepository highLowStatsRepository;
    
    @Autowired
    private MedalService medalService;
    
    @Autowired
    private MedalSnapshotRepository medalSnapshotRepository;

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
        
        saveMedalSnapshot(loginId, currentMedals);
        
        model.addAttribute("myMedals", currentMedals);
        
        List<MedalSnapshot>history = medalSnapshotRepository.findByLoginidOrderBySnapshotDateAsc(loginId);
        List<String>labels = history.stream()
        		.map(s -> s.getSnapshotDate().toString())
        		.collect(Collectors.toList());
        
        List<Integer>data = history.stream()
        		.map(MedalSnapshot::getMymedal)
        		.collect(Collectors.toList());
        
        model.addAttribute("chartLabels",labels);
        model.addAttribute("chartData",data);
        
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
    
    private void saveMedalSnapshot(String loginId, Integer currentMedals) {
        LocalDate today = LocalDate.now();
        
        // データ保存処理
        MedalSnapshot snapshot = medalSnapshotRepository
            .findById(new MedalSnapshotId(loginId, today))
            .orElse(new MedalSnapshot());

        snapshot.setLoginid(loginId);
        snapshot.setSnapshotDate(today);
        snapshot.setMymedal(currentMedals);
        
        if (snapshot.getRegistdate() == null) {
            snapshot.setRegistdate(today);
        }
        snapshot.setUpdatedate(today);

        medalSnapshotRepository.save(snapshot);
        
        //データ削除処理
        List<MedalSnapshot> history = medalSnapshotRepository.findByLoginidOrderBySnapshotDateDesc(loginId);
        
        if(history.size() > 7) {
        	for(int i = 7; i < history.size(); i++) {
        		medalSnapshotRepository.delete(history.get(i));
        	}
        }
    }
}
