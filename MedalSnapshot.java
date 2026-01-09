package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "medal_daily_snapshot") 
@IdClass(MedalSnapshotId.class) 
@Data
public class MedalSnapshot {
    @Id
    private String loginid;

    @Id
    private LocalDate snapshotDate;

    private Integer mymedal;
    private LocalDate registdate;
    private LocalDate updatedate;
}