package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.MedalSnapshot;
import com.example.demo.model.MedalSnapshotId;

public interface MedalSnapshotRepository extends JpaRepository<MedalSnapshot, MedalSnapshotId> {
	List<MedalSnapshot> findByLoginidOrderBySnapshotDateAsc(String loginid);
	
	List<MedalSnapshot> findByLoginidOrderBySnapshotDateDesc(String loginid);
	
	void deleteByLoginidAndSnapshotDate(String loginid, LocalDate snapshotDate);
}