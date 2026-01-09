package com.example.demo.model;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 複合主キーを保持するためのクラス
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedalSnapshotId implements Serializable {
    private String loginid;
    private LocalDate snapshotDate;
}