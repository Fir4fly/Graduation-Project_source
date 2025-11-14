package com.example.demo.model;

/**
 * High&Lowゲームの現在の状態やプレイヤーの選択を保持するためのモデル/DTO
 * (データベースエンティティではありません)
 */
public class HighLow {

    private int betCount;    // 現在のベット数 (HLBetMedal)
    private String faceUpCard; // 表向きのカード (例: "A", "2", "K")
    private String faceDownCard; // 裏向きのカード (未めくり時は"?"、めくり後は値)
    private String playerChoice; // プレイヤーの選択 ("HIGH" or "LOW")

    // コンストラクタ
    public HighLow() {
        this.setBetCount(0);
        this.setFaceUpCard("");
        this.setFaceDownCard("?");
        this.setPlayerChoice("");
    }

	public int getBetCount() {
		return betCount;
	}

	public void setBetCount(int betCount) {
		this.betCount = betCount;
	}

	public String getFaceUpCard() {
		return faceUpCard;
	}

	public void setFaceUpCard(String faceUpCard) {
		this.faceUpCard = faceUpCard;
	}

	public String getPlayerChoice() {
		return playerChoice;
	}

	public void setPlayerChoice(String playerChoice) {
		this.playerChoice = playerChoice;
	}

	public String getFaceDownCard() {
		return faceDownCard;
	}

	public void setFaceDownCard(String faceDownCard) {
		this.faceDownCard = faceDownCard;
	}
}