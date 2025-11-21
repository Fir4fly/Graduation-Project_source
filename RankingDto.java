// src/main/java/com/example/demo/dto/RankingDto.java (新規作成)

package com.example.demo.dto;

public class RankingDto {

    private int rank;           // 順位
    private String nickname;    // ユーザー名
    private int medals;         // メダル所持数
    private boolean isMe;       // 現在のユーザーかどうか（自分の順位表示用）

    public RankingDto(int rank, String nickname, int medals, boolean isMe) {
        this.rank = rank;
        this.nickname = nickname;
        this.medals = medals;
        this.isMe = isMe;
    }
    
    public int getRank() {
        return rank;
    }

    public String getNickname() {
        return nickname; 
    }

    public int getMedals() {
        return medals;
    }

    public boolean isMe() {
        return isMe;
    }
}
