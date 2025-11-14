// src/main/java/com/example/demo/controller/HighLowController.java

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Controller
public class HighLowController {

    @Autowired
    private UserService userService; // Userの情報を取得するためのサービス

    /**
     * High&Lowゲーム画面の表示
     */
    @GetMapping("/High_Low")
    public String showHighLow(Model model) {
        // ログインユーザーのIDを仮に 'testuser' に固定 (要件による)
        String loginId = "testuser"; 

        // データベースからユーザー情報を取得
        // 実際にはログインセッションからユーザーIDを取得することが推奨されます
        User user = userService.findByLoginId(loginId); 

        if (user == null) {
            // ユーザーが見つからない場合はエラー処理
            // 例: ログイン画面へリダイレクト
            return "redirect:/login"; 
        }

        // Thymeleafに渡すデータ
        model.addAttribute("user", user);       // ユーザー情報 (mymedal表示用)
        model.addAttribute("betCount", 0);     // 初期ベット数
        
        // カードの初期表示 (ここはビジネスロジックで制御されます)
        model.addAttribute("faceUpCard", "J");  // 仮の表向きのカード
        model.addAttribute("faceDownCard", "?"); // 裏向きのカード

        return "High_Low"; // High_Low.htmlをレンダリング
    }

    // --- ゲームプレイ用のエンドポイントの例 (後で実装が必要) ---
    /**
     * HIGH/LOWの選択とベット額を受け取り、ゲームを処理するAPI
     * 実際には非同期通信(Ajax/Fetch)で呼び出されます
     */
    /*
    @PostMapping("/api/highlow/play")
    @ResponseBody // JSONデータを返す場合は @RestController にするか @ResponseBody をつける
    public Map<String, Object> playGame(@RequestParam("bet") int bet, 
                                        @RequestParam("choice") String choice) {
        // TODO:
        // 1. 裏向きのカードを決定する
        // 2. 勝敗を判定する (WIN/LOSE)
        // 3. Userのmymedalを更新する
        // 4. 結果をJSONで返す
        
        // 仮のレスポンス
        Map<String, Object> result = new HashMap<>();
        result.put("result", "LOSE"); // or "WIN"
        result.put("newMedalCount", 4900); // 更新後のメダル数
        return result;
    }
    */
}