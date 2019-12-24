package com.example.check_java_oop.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table
@PrimaryKeyJoinColumn(name = "examFileId")
public class ExamExercise extends ExamFile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	private MultipartFile examExerciseData;
	@Column
	private String examExerciseLocation;
	@Column
	private String examExerciseXMLLocation;

	@ManyToOne
	@JoinColumn(name = "exam_id", nullable = false)
	private Exam exam;
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "exercise_result_id", referencedColumnName = "id")
	private ExerciseResult exerciseResult;

	public ExamExercise() {
		super();
	}

	public ExerciseResult getExerciseResult() {
		return exerciseResult;
	}

	public void setExerciseResult(ExerciseResult exerciseResult) {
		this.exerciseResult = exerciseResult;
	}

	public MultipartFile getExamExerciseData() {
		return examExerciseData;
	}

	public void setExamExerciseData(MultipartFile examExecution) {
		this.examExerciseData = examExecution;
	}

	public String getExamExerciseLocation() {
		return examExerciseLocation;
	}

	public void setExamExerciseLocation(String location) {
		this.examExerciseLocation = location;
	}

	public String getExamExerciseXMLLocation() {
		return examExerciseXMLLocation;
	}

	public void setExamExerciseXMLLocation(String examExecutionXMLLocation) {
		this.examExerciseXMLLocation = examExecutionXMLLocation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}
}
