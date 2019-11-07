package com.example.check_java_oop.model;

import java.io.Serializable;
import java.util.Date;

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
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class ExamExecution extends ExamFile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(nullable = false)
	private int id;
	@Column
	private String result;
	@Transient
	private MultipartFile examExecutionData;
	@Transient
	private MultipartFile examExecutionXML;
	@Column
	private String examExecutionLocation;
	@Column
	private String examExecutionXMLLocation;

	@ManyToOne
	@JoinColumn(name = "exam_id", nullable = false)
	private Exam exam;
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public ExamExecution() {
		super();
	}

	public int getId() {
		return id;
	}

	public MultipartFile getExamExecutionData() {
		return examExecutionData;
	}

	public void setExamExecutionData(MultipartFile examExecution) {
		this.examExecutionData = examExecution;
	}

	public MultipartFile getExamExecutionXML() {
		return examExecutionXML;
	}

	public void setExamExecutionXML(MultipartFile examExecutionXML) {
		this.examExecutionXML = examExecutionXML;
	}

	public String getExamExecutionLocation() {
		return examExecutionLocation;
	}

	public void setExamExecutionLocation(String location) {
		this.examExecutionLocation = location;
	}

	public String getExamExecutionXMLLocation() {
		return examExecutionXMLLocation;
	}

	public void setExamExecutionXMLLocation(String examExecutionXMLLocation) {
		this.examExecutionXMLLocation = examExecutionXMLLocation;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}
}
