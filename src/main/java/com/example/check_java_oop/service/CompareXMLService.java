package com.example.check_java_oop.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.example.check_java_oop.model.Exam;
import com.example.check_java_oop.model.ExamExercise;
import com.example.check_java_oop.model.UserClass;
import com.example.check_java_oop.model.UserMethod;
import com.example.check_java_oop.model.UserVariable;

@Service
public class CompareXMLService {

	int classErr = 0;
	int varErr = 0;
	int methodErr = 0;
	public String XmlExercise, XmlTest;

	public CompareXMLService() {
		super();
	}

	public void compare(Exam exam, ExamExercise examExecution) {

		this.XmlExercise = examExecution.getExamExerciseXMLLocation();
		this.XmlTest = exam.getAnswerXMLLocation();
		List<UserClass> alClassesD = unmarshallXML(this.XmlExercise);
		List<UserClass> alClassesT = unmarshallXML(this.XmlTest);
		boolean classNotFound = true;
		boolean varNotFound = true;
		boolean methodNotFound = true;

		for (int i = 0; i < alClassesD.size(); i++) {
			UserClass dCl = alClassesD.get(i);
			for (int j = 0; j < alClassesT.size(); j++) {
				UserClass tCl = alClassesT.get(j);
				if (dCl.getName().equals(tCl.getName())) {
					classNotFound = false;
					if (!dCl.getModifier().equals(tCl.getModifier())
							|| !dCl.getExtend().equals(tCl.getExtend())
							|| !dCl.getImplement().equals(tCl.getImplement())) {
						classErr++;
					}

					for (int m = 0; m < alClassesD.get(i).getUserVariables().size(); m++) {
						UserVariable dVar = alClassesD.get(i).getUserVariables().get(m);
						for (int n = 0; n < alClassesT.get(j).getUserVariables().size(); n++) {
							UserVariable tVar = alClassesT.get(j).getUserVariables().get(n);
							if (dVar.getName().equals(tVar.getName())) {
								varNotFound = false;
								if (!dVar.getModifier().equals(tVar.getModifier())
										|| !dVar.getType().equals(tVar.getType())) {
									varErr++;
								}
							}
						}
						if (varNotFound) {
							varErr++;
						}
					}

					for (int m = 0; m < alClassesD.get(i).getUserMethods().size(); m++) {
						UserMethod dMethod = alClassesD.get(i).getUserMethods().get(m);
						for (int n = 0; n < alClassesT.get(j).getUserMethods().size(); n++) {
							UserMethod tMethod = alClassesT.get(j).getUserMethods().get(n);
							if (dMethod.getName().equals(tMethod.getName())) {
								methodNotFound = false;
								if (!dMethod.getReturnType().equals(tMethod.getReturnType())
										|| !dMethod.getModifier().equals(tMethod.getModifier())
										|| !dMethod.getParameterType().equals(tMethod.getParameterType())) {
									methodErr++;
								}
							}
						}
						if (methodNotFound) {
							methodErr++;
						}
					}
				}
			}
			if (classNotFound) {
				classErr++;
			}
		}

		System.out.println("So Class loi: " + classErr);
		System.out.println("So UserVariable loi: " + varErr);
		System.out.println("So UserMethod loi: " + methodErr);
	}

	public boolean check() {
		if (classErr == 0 && varErr == 0 && methodErr == 0) {
			return true;
		}
		return false;
	}

	public List<UserClass> unmarshallXML(String xml) {
		List<UserClass> classes = new ArrayList<>();
		List<UserVariable> variables = new ArrayList<>();
		List<UserMethod> methods = new ArrayList<>();
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(xml));

			doc.getDocumentElement().normalize();
			NodeList nlClasses = doc.getElementsByTagName("class");

			for (int i = 0; i < nlClasses.getLength(); i++) {
				Node classNode = nlClasses.item(i);

				if (classNode.getNodeType() == Node.ELEMENT_NODE) {
					Element classElement = (Element) classNode;
					UserClass cl = new UserClass();

					cl.setName(classElement.getAttribute("name"));
					cl.setModifier(classElement.getAttribute("modifier"));
					cl.setExtend(classElement.getAttribute("extends"));
					cl.setImplement(new ArrayList<String>(
							Arrays.asList(classElement.getAttribute("implements"))));

					NodeList nlVariables = classElement.getElementsByTagName("variable");

					for (int j = 0; j < nlVariables.getLength(); j++) {
						Node varNode = nlVariables.item(j);

						if (varNode.getNodeType() == Node.ELEMENT_NODE) {
							Element varElement = (Element) varNode;
							UserVariable var = new UserVariable();

							var.setName(varElement.getAttribute("name"));
							var.setType(varElement.getAttribute("type"));
							var.setModifier(varElement.getAttribute("modifier"));
							variables.add(var);
						}
					}

					cl.setUserVariables(variables);

					NodeList nlMethods = classElement.getElementsByTagName("method");

					for (int k = 0; k < nlMethods.getLength(); k++) {
						Node methodNode = nlMethods.item(k);

						if (methodNode.getNodeType() == Node.ELEMENT_NODE) {
							Element methodElement = (Element) methodNode;
							UserMethod method = new UserMethod();

							method.setName(methodElement.getAttribute("name"));
							method.setReturnType(methodElement.getAttribute("return"));
							method.setModifier(methodElement.getAttribute("modifier"));
							method.setParameterType(
									Arrays.asList(methodElement.getAttribute("parameter_type").split(" ")));
							methods.add(method);
						}
					}

					cl.setUserMethods(methods);
					classes.add(cl);
					methods.clear();
					variables.clear();
				}
			}

//			for (int i = 0; i < classes.size(); i++) {
//				UserClass tempCl = classes.get(i);
//
//				System.out.println("Class name: " + tempCl.getName());
//				System.out.println("Class modifier: " + tempCl.getModifier());
//				System.out.println("Class extends: " + tempCl.getExtend());
//				System.out.println("Class implements: " + tempCl.getImplement());
//				System.out.println();
//
//				List<UserVariable> tempVL = tempCl.getUserVariables();
//				for (int j = 0; j < tempVL.size(); j++) {
//					System.out.println("UserVariable name: " + tempVL.get(j).getName());
//					System.out.println("UserVariable type: " + tempVL.get(j).getType());
//					System.out.println("UserVariable modifier: " + tempVL.get(j).getModifier());
//					System.out.println();
//				}
//
//				List<UserMethod> tempML = tempCl.getUserMethods();
//				for (int k = 0; k < tempML.size(); k++) {
//					System.out.println("UserMethod name: " + tempML.get(k).getName());
//					System.out.println("UserMethod return: " + tempML.get(k).getReturnType());
//					System.out.println("UserMethod modifier: " + tempML.get(k).getModifier());
//					System.out
//							.println("UserMethod parameter type: " + tempML.get(k).getParameterType());
//					System.out.println();
//				}
//			}

		} catch (Exception ex) {
			System.out.println(ex);
		}
		return classes;
	}

}
