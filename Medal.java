package com.example.demo.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "m_medal_table")
public class Medal {
	@Id
	@Column(name = "loginid")
	private String loginID;
	
	@Column(name = "nickname")
	private String nickname;
	
	@Column(name = "mymedal")
	private Integer myMedal;
	
	@Column(name = "seasonmymedal")
	private Integer seasonMyMedal;
	
	@Column(name = "registdate")
	private Date registDate;
	
	@Column(name = "updatedate")
	private Date updatedate;
	
	public Medal() {
	}

	public Medal(String loginID, String nickname, Integer myMedal, Integer seasonMyMedal, Date registDate,
			Date updatedate) {
		super();
		this.loginID = loginID;
		this.nickname = nickname;
		this.myMedal = myMedal;
		this.seasonMyMedal = seasonMyMedal;
		this.registDate = registDate;
		this.updatedate = updatedate;
	}

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

	public Integer getMyMedal() {
		return myMedal;
	}

	public void setMyMedal(Integer myMedal) {
		this.myMedal = myMedal;
	}

	public Integer getSeasonMyMedal() {
		return seasonMyMedal;
	}

	public void setSeasonMyMedal(Integer seasonMyMedal) {
		this.seasonMyMedal = seasonMyMedal;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
}
