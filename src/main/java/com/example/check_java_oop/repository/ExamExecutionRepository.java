package com.example.check_java_oop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.check_java_oop.model.ExamExecution;

@Repository("examExecutionRepository")
public interface ExamExecutionRepository extends JpaRepository<ExamExecution, Integer> {
	List<ExamExecution> findByNameContaining(String term);
}
