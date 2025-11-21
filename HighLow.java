package com.example.demo.model;

/**
 * High&Lowゲームの現在の状態やプレイヤーの選択を保持するためのモデル/DTO
 * (データベースエンティティではありません)
 */
public class HighLow {

    private int betAmount;    // 現在のベット数 (HLBetMedal)
    private String faceUpCard; // 表向きのカード (例: "A", "2", "K")
    private String newCard; // 裏向きのカード (未めくり時は"?"、めくり後は値)
    private String choice; //プレイヤーが選択したもの
    private String result; //結果
    private int medalChange; //メダルの増減
    private int newMedal; //更新後のメダル

    // コンストラクタ
    public HighLow() {
        this.betAmount = 0;
        this.choice = "";
        this.faceUpCard = "";
        this.newCard = "?";
        this.result = "";
        this.medalChange = 0;
        this.newMedal = 0;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getFaceUpCard() {
        return faceUpCard;
    }

    public void setFaceUpCard(String faceUpCard) {
        this.faceUpCard = faceUpCard;
    }

    public String getNewCard() {
        return newCard;
    }

    public void setNewCard(String newCard) {
        this.newCard = newCard;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getMedalChange() {
        return medalChange;
    }

    public void setMedalChange(int medalChange) {
        this.medalChange = medalChange;
    }

    public int getNewMedal() {
        return newMedal;
    }

    public void setNewMedal(int newMedal) {
        this.newMedal = newMedal;
    }
}