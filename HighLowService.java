// src/main/java/com/example/demo/service/HighLowService.java

package com.example.demo.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class HighLowService {

    private final Random random = new Random();

    /**
     * カードの値をランダムに生成する (1: A, 2-10: 2-10, 11: J, 12: Q, 13: K)
     * @return 1から13までの整数値
     */
    public int drawCardValue() {
        return random.nextInt(13) + 1; // 1 から 13
    }
    
    /**
     * カードの数値から表示用の文字列に変換する
     */
    public String getCardName(int value) {
        if (value >= 2 && value <= 10) {
            return String.valueOf(value);
        }
        return switch (value) {
            case 1 -> "A";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            default -> "?";
        };
    }

    /**
     * 勝敗を判定する
     * @param faceUpCardValue 表向きのカードの値
     * @param newCardValue 新しくめくったカードの値
     * @param choice プレイヤーの選択 ("HIGH" or "LOW")
     * @return "WIN" または "LOSE"
     */
    public String determineWinner(int faceUpCardValue, int newCardValue, String choice) {
        // カードの値が同じ場合は引き分け（一般的に負け扱い）
        if (newCardValue == faceUpCardValue) {
            return "LOSE"; 
        }

        boolean isHigh = newCardValue > faceUpCardValue;

        if ("HIGH".equalsIgnoreCase(choice)) {
            return isHigh ? "WIN" : "LOSE";
        } else if ("LOW".equalsIgnoreCase(choice)) {
            return isHigh ? "LOSE" : "WIN";
        }

        return "LOSE"; // 不正な選択の場合
    }
    
    /**
     * メダルの増減数を計算する
     * @param result 勝敗結果 ("WIN" or "LOSE")
     * @param betAmount ベット額
     * @return メダルの増減数 (勝利時は +betAmount, 敗北時は -betAmount)
     */
    public int calculateMedalChange(String result, int betAmount) {
        if ("WIN".equals(result)) {
            // HIGH&LOWの配当は通常2倍ですが、純粋な増分としてはベット額を返します
            return betAmount; 
        } else {
            return -betAmount;
        }
    }
}