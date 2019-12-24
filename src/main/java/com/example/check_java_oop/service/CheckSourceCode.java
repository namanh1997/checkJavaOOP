package com.example.check_java_oop.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.check_java_oop.model.Exam;
import com.example.check_java_oop.model.ExamExercise;
import com.example.check_java_oop.model.MyClassLoader;
import com.example.check_java_oop.model.ExamFile;
import com.example.check_java_oop.model.ExerciseResult;

@Service
public class CheckSourceCode {

	@Autowired
	private CompareXMLService compareXMLService;

	@Autowired
	private CheckOutput checkOutput;

	public void checkSource(Exam exam, ExamExercise examExecution) {

		examExecution.setExerciseResult(new ExerciseResult());

		compareXMLService.compare(exam, examExecution);
		String resultDesign;
		if (compareXMLService.check() == true) {
			resultDesign = "Đúng";
		} else {
			resultDesign = "Sai";
		}
		String resultOutput;

		if (checkOutput.check(exam, examExecution)) {
			examExecution.getExerciseResult().setOutputResult("Đúng");
			resultOutput = "Output đúng\n";
		} else {
			examExecution.getExerciseResult().setOutputResult("Sai");
			resultOutput = "Output sai\n";
		}
		String response = resultDesign + resultOutput;
		System.out.println(response);
		examExecution.getExerciseResult().setResult(resultDesign);
	}

}
