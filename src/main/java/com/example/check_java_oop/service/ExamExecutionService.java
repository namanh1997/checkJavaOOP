package com.example.check_java_oop.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.check_java_oop.exception.ResourceNotFoundException;
import com.example.check_java_oop.model.ExamExercise;
import com.example.check_java_oop.repository.ExamExecutionRepository;

@Service
public class ExamExecutionService {
	@Autowired
	private ExamExecutionRepository examExecutionRepository;

	@Autowired
	private FileService fileService;

	@Autowired
	private CheckSourceCode checkSourceCode;

	@Autowired
	private ConvertToXMLService convertToXMLService;

	public Iterable<ExamExercise> findAll() {
		return examExecutionRepository.findAll();
	}

	public List<ExamExercise> search(String term) {
		return examExecutionRepository.findByNameContaining(term);
	}

	public ExamExercise findOne(Integer id) {
		return examExecutionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ExamExecution", "id", id));
	}

	public void save(ExamExercise examExecution) {
		examExecution.setName(examExecution.getExam().getName());
		examExecution.setFileName(
				examExecution.getExamExerciseData().getOriginalFilename().split("\\.")[0]);
		Long folder = new Timestamp(System.currentTimeMillis()).getTime();
		examExecution.setDirectory(Paths.get(examExecution.getDirectory()).toAbsolutePath()
				+ File.separator + folder);

		Path dir = Paths.get(examExecution.getDirectory());
		if (!dir.toFile().exists()) {
			dir.toFile().mkdirs();
		}

		examExecution.setExamExerciseLocation(
				fileService.saveFile(examExecution.getExamExerciseData(), examExecution));

		convertToXMLService.convertToXML(examExecution);

		examExecution.setExamExerciseXMLLocation(
				Paths.get(examExecution.getDirectory()).toAbsolutePath() + File.separator
						+ examExecution.getFileName() + ".xml");

		checkSourceCode.checkSource(examExecution.getExam(), examExecution);

		examExecutionRepository.save(examExecution);
	}

	public void delete(Integer id) {
		examExecutionRepository.deleteById(id);
	}
}
