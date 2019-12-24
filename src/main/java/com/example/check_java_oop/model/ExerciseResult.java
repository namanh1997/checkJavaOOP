package com.example.check_java_oop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table
public class ExerciseResult implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(nullable = false)
	private int id;
	@Column
	private String result;
	@Column
	private String outputResult;
	@Column
	@Lob
	private String output;

	@OneToOne(mappedBy = "exerciseResult")
	private ExamExercise examExecution;

	public ExerciseResult() {
		super();
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public String getOutputResult() {
		return outputResult;
	}

	public void setOutputResult(String outputResult) {
		this.outputResult = outputResult;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public int getId() {
		return id;
	}

	public ExamExercise getExamExecution() {
		return examExecution;
	}

	public void setExamExecution(ExamExercise examExecution) {
		this.examExecution = examExecution;
	}

}
