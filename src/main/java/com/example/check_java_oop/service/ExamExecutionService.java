package com.example.check_java_oop.service;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.check_java_oop.exception.ResourceNotFoundException;
import com.example.check_java_oop.model.ExamExecution;
import com.example.check_java_oop.repository.ExamExecutionRepository;

@Service
public class ExamExecutionService {
	@Autowired
	private ExamExecutionRepository examExecutionRepository;

	@Autowired
	private FileService fileService;

	public Iterable<ExamExecution> findAll() {
		return examExecutionRepository.findAll();
	}

	public List<ExamExecution> search(String term) {
		return examExecutionRepository.findByNameContaining(term);
	}

	public ExamExecution findOne(Integer id) {
		return examExecutionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("ExamExecution", "id", id));
	}

	public void save(ExamExecution examExecution) {
		examExecution.setFileName(
				examExecution.getExamExecutionData().getOriginalFilename().split("\\.")[0]);
		examExecution.setDirectory(Paths.get(examExecution.getDirectory()).toAbsolutePath()
				+ File.separator + examExecution.getCreatedAt());
		examExecution.setExamExecutionLocation(
				fileService.saveFile(examExecution.getExamExecutionData(), examExecution));
		examExecutionRepository.save(examExecution);
	}

	public void delete(Integer id) {
		examExecutionRepository.deleteById(id);
	}
}
