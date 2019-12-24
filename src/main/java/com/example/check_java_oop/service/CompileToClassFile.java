package com.example.check_java_oop.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.check_java_oop.model.MyClassLoader;
import com.example.check_java_oop.model.ExamFile;

@Service
public class CompileToClassFile {

	@Autowired
	private FileService fileService;

	public List<Class<?>> compile(ExamFile file) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String fileDir = file.getDirectory();
		Path fileAbsolutePath = Paths.get(fileDir).toAbsolutePath();

		String cmd = "javac " + fileAbsolutePath + File.separator + file.getFileName()
				+ ".java";

		try {
			ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", cmd);
			builder.directory(new File(file.getDirectory()));
			builder.start();

			MyClassLoader myClassLoader = new MyClassLoader();
			Thread.sleep(2 * 1000);
			List<String> list = fileService.listClassFileAbsolutePathInDir(file.getDirectory());
			for (int i = 0; i < list.size(); i++) {
				byte[] data = fileService.readFileToByteArray(list.get(i));
				String fileName = fileService.getFileNameWithoutExtensionFromPath(list.get(i));
				classes.add(myClassLoader.defineClass(fileName, data));
			}
			System.out.println("compiled " + list);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return classes;
	}
}
