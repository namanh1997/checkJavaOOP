package com.example.check_java_oop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.check_java_oop.model.Exam;

@Repository("examRepository")
public interface ExamRepository extends JpaRepository<Exam, Integer> {
	List<Exam> findByNameContaining(String term);
}
