package com.example.check_java_oop.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.check_java_oop.model.Exam;
import com.example.check_java_oop.model.ExamExecution;
import com.example.check_java_oop.model.ExamFile;

@Service
public class FileService {

	public String getFileNameWithExtension(String filename) {
		return StringUtils.cleanPath(filename);
	}

	public String getFileNameWithoutExtension(String filename) {
		return filename.split("\\.")[0];
	}

	public String getFileNameWithoutExtensionFromPath(String filename) {
		Path path = Paths.get(filename);
		return getFileNameWithoutExtension(path.getFileName().toString());
	}

	public String saveFile(MultipartFile fileData, ExamFile file) {
		String filename = StringUtils.cleanPath(fileData.getOriginalFilename());
		String location = Paths.get(file.getDirectory()).toAbsolutePath() + File.separator
				+ filename;
		try {
			saveFileToDisk(fileData.getBytes(), location, filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return location;
	}

	public String saveXMLFile(byte[] byteArray, ExamFile file) {
		String filename = file.getName();
		String location = Paths.get(file.getDirectory()).toAbsolutePath() + File.separator
				+ filename + ".xml";
		saveFileToDisk(byteArray, location, filename);
		return location;
	}

	public void saveFileToDisk(byte[] byteArray, String location, String filename) {
		System.out.println("Client File Name = " + filename);
		if (filename != null && filename.length() > 0) {
			try {
				// Tạo file tại Server.
				File serverFile = new File(location);

				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(byteArray);
				stream.close();
				//
				System.out.println("Write file: " + serverFile);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error Write file: " + filename);
			}
		}
	}

	public List<String> listClassFileAbsolutePathInDir(String dir) {
		try (Stream<Path> walk = Files.walk(Paths.get(dir))) {

			List<String> result = walk.map(x -> x.toString()).filter(f -> f.endsWith(".class"))
					.collect(Collectors.toList());

			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] readFileToByteArray(String location) {
		try (FileInputStream fis = new FileInputStream(location)) {
			return IOUtils.toByteArray(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String readFileToString(String location) {
		return new String(readFileToByteArray(location));
	}
}
