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
        String loginId = (String) session.getAttribute("loginID");

        if (loginId == null) {
            return "redirect:/login";
        }
        // データベースからユーザー情報を取得
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
        String loginId = (String) session.getAttribute("loginID");
        
        // 1. ログインチェック
        if (loginId == null) {
            HighLow error = new HighLow();
            error.setResult("ERROR: LOGIN_REQUIRED");
            return error;
        }

        // 2. メダル情報の存在チェック
        Medal medal = medalService.findByLoginId(loginId);
        if (medal == null) {
            HighLow error = new HighLow();
            error.setResult("ERROR: NO_MEDAL_DATA"); // メダルデータがない新規ユーザー等の場合
            return error;
        }

        // 3. セッションのカード情報の存在チェック（★重要）
        Integer faceUpCardValue = (Integer) session.getAttribute("faceUpCardValue");
        if (faceUpCardValue == null) {
            HighLow error = new HighLow();
            error.setResult("ERROR: SESSION_EXPIRED"); // セッション切れやリロード対策
            return error;
        }

        int currentMedal = medal.getMyMedal();

        // Service の実行
        HighLow result = highLowService.playGame(
                request.getBetAmount(),
                request.getChoice(),
                currentMedal,
                faceUpCardValue // ここで int に変換されるため、上の null チェックが必須
        );

        // メダル更新
        medal.setMyMedal(result.getNewMedal());
        medalService.updateMedal(medal);

        // 統計更新 (M_HL_TABLE)
        // ここでエラーが出る場合は、DBにテーブルが存在するか、カラム名が RegistDATE 等と一致するか再確認
        highLowService.updateGameStats(loginId, request.getBetAmount(), result.getResult());

        return result; 
    }
    
    @GetMapping("/api/highlow/newgame")
    @ResponseBody
    public HighLow newGame(HttpSession session) {

    		String loginId = (String) session.getAttribute("loginID");

        if (loginId == null) {
            HighLow error = new HighLow();
            error.setFaceUpCard("ERROR");
            error.setNewCard("LOGIN_REQUIRED");
            return error;
        }
        
        int faceUpVal = highLowService.drawCardValue();
        session.setAttribute("faceUpCardValue", faceUpVal);

        HighLow hl = new HighLow();
        hl.setFaceUpCard(highLowService.getCardName(faceUpVal));
        hl.setNewCard("?");

        return hl;
    }

}