package com.example.check_java_oop.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.check_java_oop.exception.ResourceNotFoundException;
import com.example.check_java_oop.model.Exam;
import com.example.check_java_oop.repository.ExamRepository;

@Service
public class ExamService {

	@Autowired
	private ExamRepository examRepository;

	@Autowired
	private FileService fileService;

	@Autowired
	private ConvertToXMLService convertToXMLService;

	public Iterable<Exam> findAll() {
		return examRepository.findAll();
	}

	public List<Exam> search(String term) {
		return examRepository.findByNameContaining(term);
	}

	public Exam findOne(Integer id) {
		return examRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Exam", "id", id));
	}

	public void save(Exam exam) {
		exam.setFileName(exam.getExamData().getOriginalFilename().split("\\.")[0]);
		exam.setDirectory(Paths.get(exam.getDirectory()).toAbsolutePath() + File.separator
				+ exam.getFileName());
		Path dir = Paths.get(exam.getDirectory());
		if (!dir.toFile().exists()) {
			dir.toFile().mkdirs();
		}

		exam.setExamLocation(fileService.saveFile(exam.getExamData(), exam));
		exam.setAnswerLocation(fileService.saveFile(exam.getAnswerData(), exam));
		exam.setInputLocation(fileService.saveFile(exam.getInputData(), exam));
		exam.setOutputLocation(fileService.saveFile(exam.getOutputData(), exam));

		convertToXMLService.convertToXML(exam);
		exam.setAnswerXMLLocation(Paths.get(exam.getDirectory()).toAbsolutePath()
				+ File.separator + exam.getFileName() + ".xml");
		examRepository.save(exam);
	}

	public void delete(Integer id) {
		examRepository.deleteById(id);
	}
}
