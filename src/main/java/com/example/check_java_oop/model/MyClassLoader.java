package com.example.check_java_oop.model;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

public class MyClassLoader extends ClassLoader {

	byte[] data;

	public MyClassLoader() {
	}

	public Class<?> defineClass(String fullClassName, byte[] data) {
		return defineClass(fullClassName, data, 0, data.length);
	}

	public static void addClassPath(String s) throws Exception {
		File f = new File(s);
		URI u = f.toURI();
		URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class<URLClassLoader> urlClass = URLClassLoader.class;
		Method method = urlClass.getDeclaredMethod("addURL", new Class[] { URL.class });
		method.setAccessible(true);
		method.invoke(urlClassLoader, new Object[] { u.toURL() });
	}
}
