package com.example.check_java_oop.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.example.check_java_oop.model.Exam;
import com.example.check_java_oop.model.ExamExecution;

@Service
public class CheckOutput {

	String className;
	String strCMD;

	public CheckOutput() {
	}

	public CheckOutput(String className) {
		this.className = className;
	}

	public boolean check(Exam exam, ExamExecution examExecution) {

		String fileDir = examExecution.getDirectory();
		String fileName = examExecution.getFileName();
		String fileAbsolutePath = Paths.get(fileDir).toAbsolutePath() + File.separator;

		strCMD = "java " + fileAbsolutePath + fileName + " <" + exam.getInputLocation();

		try {
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", strCMD);
			builder.directory(new File(examExecution.getDirectory()));
			Process p = builder.start();
			BufferedReader r1 = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader r2 = new BufferedReader(new FileReader(exam.getOutputLocation()));
			String line1, line2;
			while (true) {
				line1 = r1.readLine();
				line2 = r2.readLine();
				if (line1 != null || line2 != null) {
					if (line2 == null ? line1 == null : line2.equals(line1)) {
						System.out.println("Đáp án gửi: " + line1 + " Đáp án đúng: " + line2);
					} else {
						System.out.println("Đáp án gửi: " + line1 + " Đáp án đúng: " + line2);
						r2.close();
						return false;
					}
				} else {
					break;
				}
			}
			r2.close();
			return true;
		} catch (IOException ex) {
			Logger.getLogger(CheckOutput.class.getName()).log(Level.SEVERE, null, ex);
		}
		return true;
	}
}
