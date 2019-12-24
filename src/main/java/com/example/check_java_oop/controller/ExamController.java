package com.example.check_java_oop.controller;

import com.example.check_java_oop.model.Exam;
import com.example.check_java_oop.model.ExamExercise;
import com.example.check_java_oop.service.ExamService;
import com.example.check_java_oop.service.FileService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ExamController {
	@Autowired
	private FileService fileService;
	@Autowired
	private ExamService examService;

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
		return "redirect:/examList";
	}

	@PostMapping("/exam/update")
	public String updateExam(RedirectAttributes redirectAttributes,
			@ModelAttribute("exam") Exam exam, Model model) {
		examService.save(exam);
		return "redirect:/examList";
	}

	@GetMapping("/exam/show/{id}")
	public String show(@PathVariable("id") Integer id, Model model) {
		Exam exam = examService.findOne(id);

		ExamExercise examExercise = new ExamExercise();

		model.addAttribute("examExercise", examExercise);
		model.addAttribute("exam", exam);
		model.addAttribute("examFile", fileService.readFileToString(exam.getExamLocation()));
		model.addAttribute("inputFile",
				fileService.readFileToString(exam.getInputLocation()));
		model.addAttribute("outputFile",
				fileService.readFileToString(exam.getOutputLocation()));
		model.addAttribute("exampleInputFile",
				fileService.readFileToString(exam.getExampleInputLocation()));
		model.addAttribute("exampleOutputFile",
				fileService.readFileToString(exam.getExampleOutputLocation()));
		return "show";
	}

	@GetMapping("/examList")
	public String examList(Model model) {
		model.addAttribute("exams", examService.findAll());
		return "examList";
	}

	@GetMapping("/exam/search")
	public String search(@RequestParam("term") String term, Model model) {
		if (StringUtils.isEmpty(term)) {
			return "redirect:/examList";
		}

		model.addAttribute("exams", examService.search(term));
		return "examList";
	}

	@GetMapping("/exam/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("exam", examService.findOne(id));
		return "examEdit";
	}

	@GetMapping("/exam/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		examService.delete(id);
		redirect.addFlashAttribute("successMessage", "Xóa đề thành công!");
		return "redirect:/examList";
	}

//	@ExceptionHandler(StorageFileNotFoundException.class)
//	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//		return ResponseEntity.notFound().build();
//	}
}
