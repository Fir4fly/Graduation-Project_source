package com.example.demo.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "m_user_table")
public class User {

	// 主キーであることを示す
    @Id
    // テーブルのloginidカラムとマッピング
    @Column(name = "loginid")
    private String loginID;

    // テーブルのnicknameカラムとマッピング
    @Column(name = "nickname")
    private String nickname;

    // テーブルのloginpassカラムとマッピング
    @Column(name = "loginpass")
    private String loginPass;

    // テーブルのregistdateカラムとマッピング
    @Column(name = "registdate")
    private Date registDate;

    // テーブルのupdatedateカラムとマッピング
    @Column(name = "updatedate")
    private Date updateDate;

    // --- コンストラクタ ---

    // JPAで必要となる引数なしコンストラクタ
    public User() {
    }

    // 全フィールドを持つコンストラクタ（開発で便利）
    public User(String loginID, String nickname, String loginPass, Date registDate, Date updateDate) {
        this.loginID = loginID;
        this.nickname = nickname;
        this.loginPass = loginPass;
        this.registDate = registDate;
        this.updateDate = updateDate;
    }
    
    //ゲッターとセッター
    
    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
