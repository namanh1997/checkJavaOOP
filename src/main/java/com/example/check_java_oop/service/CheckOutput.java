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
import com.example.check_java_oop.model.ExamExercise;

@Service
public class CheckOutput {

	public boolean check(Exam exam, ExamExercise examExecution) {

		String fileDir = examExecution.getDirectory();
		String fileName = examExecution.getFileName();
		String fileAbsolutePath = Paths.get(fileDir).toAbsolutePath() + File.separator;
		int error = 0;

		String strCMD = "java -classpath " + fileDir + " " + fileName + " < "
				+ exam.getExampleInputLocation();

		try {
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", strCMD);
			builder.directory(new File(examExecution.getDirectory()));
			Process p = builder.start();
			BufferedReader r1 = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader r2 = new BufferedReader(
					new FileReader(exam.getExampleOutputLocation()));
			String line1, line2, outputExercise = "";

			int i;
			while ((i = r1.read()) != -1) {
				line1 = String.valueOf((char)i);
				line1 += r1.readLine();
				outputExercise += line1 + "\n";
				line2 = r2.readLine();
				if (line1 == null || !line2.equals(line1)) {
					error++;
				}
			}
			examExecution.getExerciseResult().setOutput(outputExercise);
			r1.close();
			r2.close();

		} catch (IOException ex) {
			error++;
			Logger.getLogger(CheckOutput.class.getName()).log(Level.SEVERE, null, ex);
		}
		return error == 0 ? true : false;
	}
}
