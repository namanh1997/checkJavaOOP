package com.example.check_java_oop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.check_java_oop.model.Exam;
import com.example.check_java_oop.model.ExamExercise;
import com.example.check_java_oop.model.UserPrincipal;
import com.example.check_java_oop.repository.ExamExecutionRepository;
import com.example.check_java_oop.service.ExamExecutionService;
import com.example.check_java_oop.service.ExamService;
import com.example.check_java_oop.service.FileService;

@Controller
public class ExamExerciseController {

	@Autowired
	private FileService fileService;

	@Autowired
	private ExamService examService;

	@Autowired
	private ExamExecutionService examExecutionService;

	@PostMapping("/uploadExercise")
	public String uploadFileExercise(RedirectAttributes redirectAttributes,
			@ModelAttribute("examExecution") ExamExercise examExecution,
			@RequestParam("examId") Integer examId, Model model) {
		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		examExecution.setUser(userPrincipal.getUser());
		examExecution.setExam(examService.findOne(examId));
		examExecutionService.save(examExecution);
		
		model.addAttribute("exerciseResult", examExecution.getExerciseResult());
		model.addAttribute("exerciseResultOutput", examExecution.getExerciseResult().getOutput());
		return "exerciseResult";
	}
}
