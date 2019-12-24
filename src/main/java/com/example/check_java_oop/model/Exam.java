package com.example.check_java_oop.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@Entity
@Table
@PrimaryKeyJoinColumn(name = "examFileId")
public class Exam extends ExamFile implements Serializable {

	private static final long serialVersionUID = 1L;

	@Transient
	private MultipartFile examData;
	@Transient
	private MultipartFile answerData;
	@Transient
	private MultipartFile inputData;
	@Transient
	private MultipartFile outputData;
	@Transient
	private MultipartFile exampleInputData;
	@Transient
	private MultipartFile exampleOutputData;
	@Column
	private String examLocation;
	@Column
	private String answerLocation;
	@Column
	private String answerXMLLocation;
	@Column
	private String inputLocation;
	@Column
	private String outputLocation;
	@Column
	private String exampleInputLocation;
	@Column
	private String exampleOutputLocation;
	@OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
	private List<ExamExercise> examExecutions;

	public List<ExamExercise> getExamExecutions() {
		return examExecutions;
	}

	public void setExamExecutions(List<ExamExercise> examExecutions) {
		this.examExecutions = examExecutions;
	}

	public Exam() {
		super();
	}

	public String getExampleInputLocation() {
		return exampleInputLocation;
	}

	public void setExampleInputLocation(String exampleInputLocation) {
		this.exampleInputLocation = exampleInputLocation;
	}

	public String getExampleOutputLocation() {
		return exampleOutputLocation;
	}

	public void setExampleOutputLocation(String exampleOutputLocation) {
		this.exampleOutputLocation = exampleOutputLocation;
	}

	public MultipartFile getExampleInputData() {
		return exampleInputData;
	}

	public void setExampleInputData(MultipartFile exampleInputData) {
		this.exampleInputData = exampleInputData;
	}

	public MultipartFile getExampleOutputData() {
		return exampleOutputData;
	}

	public void setExampleOutputData(MultipartFile exampleOutputData) {
		this.exampleOutputData = exampleOutputData;
	}

	public MultipartFile getExamData() {
		return examData;
	}

	public void setExamData(MultipartFile examData) {
		this.examData = examData;
	}

	public MultipartFile getAnswerData() {
		return answerData;
	}

	public void setAnswerData(MultipartFile answerData) {
		this.answerData = answerData;
	}

	public MultipartFile getInputData() {
		return inputData;
	}

	public void setInputData(MultipartFile inputData) {
		this.inputData = inputData;
	}

	public MultipartFile getOutputData() {
		return outputData;
	}

	public void setOutputData(MultipartFile outputData) {
		this.outputData = outputData;
	}

	public String getExamLocation() {
		return examLocation;
	}

	public void setExamLocation(String location) {
		this.examLocation = location;
	}

	public String getAnswerLocation() {
		return answerLocation;
	}

	public void setAnswerLocation(String answerLocation) {
		this.answerLocation = answerLocation;
	}

	public String getAnswerXMLLocation() {
		return answerXMLLocation;
	}

	public void setAnswerXMLLocation(String answerXMLLocation) {
		this.answerXMLLocation = answerXMLLocation;
	}

	public String getInputLocation() {
		return inputLocation;
	}

	public void setInputLocation(String inputLocation) {
		this.inputLocation = inputLocation;
	}

	public String getOutputLocation() {
		return outputLocation;
	}

	public void setOutputLocation(String outputLocation) {
		this.outputLocation = outputLocation;
	}

}
