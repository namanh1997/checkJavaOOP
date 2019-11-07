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
import com.example.check_java_oop.model.ExamExecution;
import com.example.check_java_oop.model.MyClassLoader;
import com.example.check_java_oop.model.ExamFile;

@Service
public class CheckSourceCode {

	@Autowired
	private ConvertToXMLService convertToXMLService;
	
	@Autowired
	private CompareXMLService compareXMLService;
	
	@Autowired
	private CheckOutput checkOutput;

	public void checkSource(Exam exam, ExamExecution examExecution) {

		String className = examExecution.getName();

		convertToXMLService.convertToXML(examExecution);

		compareXMLService.compare(exam, examExecution);
		String resultDesign;
		if (compareXMLService.check() == true) {
			resultDesign = "Design is true\n";
		} else {
			resultDesign = "Design is false\n";
		}
		String resultOutput;

		if (checkOutput.check(exam, examExecution)) {
			resultOutput = "Output is true\n";
		} else {
			resultOutput = "Output is false\n";
		}
		String response = resultDesign + "    " + resultOutput;
		System.out.println(response);
	}

}
