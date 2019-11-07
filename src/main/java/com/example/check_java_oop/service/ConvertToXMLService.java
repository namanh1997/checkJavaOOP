package com.example.check_java_oop.service;

import java.lang.Class;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.check_java_oop.model.ExamFile;
import com.example.check_java_oop.model.UserClass;
import com.example.check_java_oop.model.UserMethod;
import com.example.check_java_oop.model.UserVariable;

@Service
public final class ConvertToXMLService extends ClassLoader {

	public String strXML;
	public String strClassname;
	public StringBuilder sb;

	@Autowired
	private FileService fileService;

	@Autowired
	private CompileToClassFile compileToClassFile;

	public ConvertToXMLService() {
		super();
	}

	public void convertToXML(ExamFile file) {

		List<Class<?>> classes = compileToClassFile.compile(file);

		if (classes == null) {
			System.out.println("Dat ten class sai!!!!");
		}
		System.out.println("converting");

		sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		sb.append("<classes>\n");
		for (int j = 0; j < classes.size(); j++) {
			UserClass userClass = new UserClass();
			userClass.setName(classes.get(j).getSimpleName());
			userClass.setModifier(Modifier.toString(classes.get(j).getModifiers()));
			if (classes.get(j).getSuperclass() != null) {
				userClass.setExtend(classes.get(j).getSuperclass().getSimpleName());
			}
			Class<?>[] interfaces = classes.get(j).getInterfaces();
			ArrayList<String> list = new ArrayList<String>();
			for (Class<?> i : interfaces) {
				list.add(i.getSimpleName());
			}
			userClass.setImplement(list);
			sb.append(userClass.toString());

			Field[] fields = classes.get(j).getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				UserVariable userVariable = new UserVariable();
				userVariable.setName(fields[i].getName());
				userVariable.setType(fields[i].getType().getSimpleName());
				int m = fields[i].getModifiers();
				userVariable.setModifier(Modifier.toString(m));
				sb.append(userVariable.toString());
			}

			Method[] methods = classes.get(j).getDeclaredMethods();
			for (int i = 0; i < methods.length; i++) {
				UserMethod userMethod = new UserMethod();
				userMethod.setName(methods[i].getName());
				userMethod.setReturnType(methods[i].getReturnType().getSimpleName());
				int m = methods[i].getModifiers();
				userMethod.setModifier(Modifier.toString(m));
				Parameter[] parameters = methods[i].getParameters();
				ArrayList<String> lp = new ArrayList<String>();
				for (Parameter para : parameters) {
					lp.add(para.getType().getSimpleName());
				}
				userMethod.setParameterType(lp);
				sb.append(userMethod.toString());
			}
			sb.append("</class>");
		}
		sb.append("</classes>");

		fileService.saveXMLFile(sb.toString().getBytes(), file);
	}

}
