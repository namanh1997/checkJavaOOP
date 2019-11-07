package com.example.check_java_oop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.check_java_oop.model.ExamExecution;
import com.example.check_java_oop.service.ExamExecutionService;
import com.example.check_java_oop.service.ExamService;
import com.example.check_java_oop.service.FileService;

@Controller
public class ExamExercutionController {

	@Autowired
	private FileService fileService;

	@Autowired
	private ExamService examService;

	@Autowired
	private ExamExecutionService examExecutionService;

	@PostMapping("/uploadExercise")
	public String uploadFileExercise(RedirectAttributes redirectAttributes,
			@ModelAttribute("examExecution") ExamExecution examExecution, Model model) {

		
		examExecutionService.save(examExecution);
		return "redirect:/uploadExercise";
	}
}
