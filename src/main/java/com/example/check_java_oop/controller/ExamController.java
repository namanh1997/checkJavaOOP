package com.example.check_java_oop.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.example.check_java_oop.exception.StorageFileNotFoundException;
import com.example.check_java_oop.model.Exam;
import com.example.check_java_oop.model.ExamExecution;
import com.example.check_java_oop.model.StorageProperties;
import com.example.check_java_oop.model.User;
import com.example.check_java_oop.model.UserClass;
import com.example.check_java_oop.model.UserPrincipal;
import com.example.check_java_oop.service.CheckSourceCode;
import com.example.check_java_oop.service.ConvertToXMLService;
import com.example.check_java_oop.service.ExamExecutionService;
import com.example.check_java_oop.service.ExamService;
import com.example.check_java_oop.service.FileService;
import com.example.check_java_oop.service.StorageService;
import com.example.check_java_oop.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExamController {

	@Autowired
	private FileService fileService;

	@Autowired
	private UserService userService;

	@Autowired
	private ExamService examService;

	@Autowired
	private ConvertToXMLService convertToXMLService;

	@Autowired
	private final StorageService storageService;

	@Autowired
	public ExamController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/upload")
	public String listUploadedFiles(Model model) {
		Exam exam = new Exam();
		model.addAttribute("exam", exam);
		return "upload";
	}

	@PostMapping("/upload")
	public String handleFileUpload(RedirectAttributes redirectAttributes,
			@ModelAttribute("exam") Exam exam, Model model) {
		examService.save(exam);
		return "redirect:/upload";
	}

	@GetMapping("/exam/show/{id}")
	public String show(@PathVariable("id") Integer id, Model model) {
		Exam exam = examService.findOne(id);

		ExamExecution examExecution = new ExamExecution();
		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		examExecution.setUser(userPrincipal.getUser());
		examExecution.setExam(exam);

		model.addAttribute("examExecution", examExecution);
		model.addAttribute("exam", exam);
		model.addAttribute("examFile", fileService.readFileToString(exam.getExamLocation()));
		model.addAttribute("inputFile",
				fileService.readFileToString(exam.getInputLocation()));
		model.addAttribute("outputFile",
				fileService.readFileToString(exam.getOutputLocation()));
		return "show";
	}

	@GetMapping("/examList")
	public String examList(Model model) {
		model.addAttribute("exams", examService.findAll());
		return "examList";
	}

	@GetMapping("/examList/search")
	public String search(@RequestParam("term") String term, Model model) {
		if (StringUtils.isEmpty(term)) {
			return "redirect:/examList";
		}

		model.addAttribute("exams", examService.search(term));
		return "examList";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}
}