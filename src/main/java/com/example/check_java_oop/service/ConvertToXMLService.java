package com.example.check_java_oop.service;

import java.io.FileOutputStream;
import java.lang.Class;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

import com.example.check_java_oop.model.UserClass;
import com.example.check_java_oop.model.UserMethod;
import com.example.check_java_oop.model.UserVariable;

public final class ConvertToXMLService extends ClassLoader {

  public String strXML;
  public String strClassname;
  public StringBuilder sb;

  public ConvertToXMLService(String classname, Class<?> aclass) {
      this.strClassname = classname;
      convertToXML(aclass);
      //System.out.println(sb.toString());
      strXML = System.getProperty("user.dir") + "/src/xmlTest/" + strClassname + ".xml";
      try (FileOutputStream fos = new FileOutputStream(strXML);) {
          byte[] data = sb.toString().getBytes();
          fos.write(data);
      } catch (Exception e) {
          System.err.println("error: " + e.getMessage());
      }
  }

  public void convertToXML(Class<?> aclass) {
      sb = new StringBuilder();
      sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
      UserClass UserClass = new UserClass();
      UserClass.setName(aclass.getSimpleName());
      UserClass.setModifier(Modifier.toString(aclass.getModifiers()));
      UserClass.setExtend(aclass.getSuperclass().getSimpleName());

      Class<?>[] interfaces = aclass.getInterfaces();
      ArrayList<String> list = new ArrayList<String>();
      for (Class<?> i : interfaces) {
          list.add(i.getSimpleName());
      }
      UserClass.implement = list;
      sb.append(UserClass.toString());

      Field[] fields = aclass.getDeclaredFields();
      for (int i = 0; i < fields.length; i++) {
          UserVariable UserVariable = new UserVariable();
          UserVariable.setName(fields[i].getName());
          UserVariable.setType(fields[i].getType().getSimpleName());
          int m = fields[i].getModifiers();
          UserVariable.setModifier(Modifier.toString(m));
          sb.append(UserVariable.toString());
      }
      Method[] methods = aclass.getDeclaredMethods();
      for (int i = 0; i < methods.length; i++) {
          UserMethod UserMethod = new UserMethod();
          UserMethod.setName(methods[i].getName());
          UserMethod.setReturnType(methods[i].getReturnType().getSimpleName());
          int m = methods[i].getModifiers();
          UserMethod.setModifier(Modifier.toString(m));
          Parameter[] parameters = methods[i].getParameters();
          ArrayList<String> lp = new ArrayList<String>();
          for (Parameter para : parameters) {
              lp.add(para.getType().getSimpleName());
          }
          UserMethod.parameter_type = lp;
          sb.append(UserMethod.toString());
      }
      sb.append("</class>");
  }

  public String getXML() {
      return strXML;
  }

}
