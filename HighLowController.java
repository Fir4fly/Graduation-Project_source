// src/main/java/com/example/demo/controller/HighLowController.java

package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.HighLow;
import com.example.demo.model.Medal;
import com.example.demo.service.HighLowService;
import com.example.demo.service.MedalService;

@Controller
public class HighLowController {

    @Autowired
    private MedalService medalService;
    
    @Autowired
    private HighLowService highLowService;

    /**
     * High&Lowゲーム画面の表示
     */
    @GetMapping("/High_Low")
    public String showHighLow(Model model, HttpSession session) {
        // ログインユーザーのIDを仮に 'testuser' に固定 (要件による)
        String loginId = "testuser"; 

        // データベースからユーザー情報を取得
        // 実際にはログインセッションからユーザーIDを取得することが推奨されます
        Medal medal = medalService.findByLoginId(loginId); 
        
     // 表向きのカードを Service で生成
        int faceUpVal = highLowService.drawCardValue();
        String faceUpCard = highLowService.getCardName(faceUpVal);
        
        session.setAttribute("faceUpCardValue", faceUpVal);

        // Thymeleafに渡すデータ
        model.addAttribute("user", medal);       // ユーザー情報 (mymedal表示用)
        model.addAttribute("betCount", 0);     // 初期ベット数
        
        // カードの初期表示 (ここはビジネスロジックで制御されます)
        model.addAttribute("faceUpCard", faceUpCard);  // 仮の表向きのカード
        model.addAttribute("faceDownCard", "?"); // 裏向きのカード

        return "High_Low"; // High_Low.htmlをレンダリング
    }

    // --- ゲームプレイ用のエンドポイントの例 (後で実装が必要) ---
    /**
     * HIGH/LOWの選択とベット額を受け取り、ゲームを処理するAPI
     * 実際には非同期通信(Ajax/Fetch)で呼び出されます
     */
    @PostMapping("/api/highlow/play")
    @ResponseBody
    public HighLow playGame(@RequestBody HighLow request, HttpSession session) {

    	Integer faceUpCardValue = (Integer) session.getAttribute("faceUpCardValue");
        if (faceUpCardValue == null) {
            // 例: セッション切れなど。エラー処理を適切に行う
            HighLow errorResult = new HighLow();
            errorResult.setResult("ERROR");
            errorResult.setFaceUpCard("E");
            errorResult.setNewCard("R");
            errorResult.setMedalChange(0);
            return errorResult; 
        }
        
        // ★ 本来はセッションからユーザーIDを取得
        String loginId = "testuser";
        Medal medal = medalService.findByLoginId(loginId);
        int currentMedal = medal.getMyMedal();

        // Service のゲーム処理を実行
        HighLow result = highLowService.playGame(
                request.getBetAmount(),
                request.getChoice(),
                currentMedal,
                faceUpCardValue
        );

        // メダル更新
        medal.setMyMedal(result.getNewMedal());
        medalService.updateMedal(medal);

        return result; // → JSONで返される
    }
    
    @GetMapping("/api/highlow/newgame")
    @ResponseBody
    public HighLow newGame(HttpSession session) {

        int faceUpVal = highLowService.drawCardValue();
        
        session.setAttribute("faceUpCardValue", faceUpVal);

        HighLow hl = new HighLow();
        hl.setFaceUpCard(highLowService.getCardName(faceUpVal));
        hl.setNewCard("?");

        return hl;
    }

}