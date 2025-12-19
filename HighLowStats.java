package com.example.demo.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "M_HL_TABLE")
public class HighLowStats {
	@Id
	@Column(name = "loginID", length = 50)
	private String loginId;
	
	@Column(name = "hl_bet_medal")
	private int hlBetMedal;
	
	@Column(name = "hl_play_count")
	private int hlPlayCount;
	
	@Column(name = "hl_get_medal")
	private int hlGetMedal;
	
	@Column(name = "RegistDATE")
	private Date registDate;
	
	@Column(name = "UpdateDATE")
	private Date updateDate;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public int getHlBetMedal() {
		return hlBetMedal;
	}

	public void setHlBetMedal(int hlBetMedal) {
		this.hlBetMedal = hlBetMedal;
	}

	public int getHlPlayCount() {
		return hlPlayCount;
	}

	public void setHlPlayCount(int hlPlayCount) {
		this.hlPlayCount = hlPlayCount;
	}

	public int getHlGetMedal() {
		return hlGetMedal;
	}

	public void setHlGetMedal(int hlGetMedal) {
		this.hlGetMedal = hlGetMedal;
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

