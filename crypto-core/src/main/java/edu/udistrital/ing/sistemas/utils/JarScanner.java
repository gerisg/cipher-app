package edu.udistrital.ing.sistemas.utils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarScanner {

	private Iterable<Class<?>> types;

	public JarScanner(File file) {
		types = collectTypesFromJar(file);
	}

	public Collection<Class<?>> findAssignableTo(Class<?> interfaceType) {
		LinkedList<Class<?>> foundTypes = new LinkedList<>();

		for (Class<?> type : types)
			if (interfaceType.isAssignableFrom(type))
				foundTypes.add(type);

		return foundTypes;
	}

	private Collection<Class<?>> collectTypesFromJar(File file) {
		JarScannerClassLoader loader = new JarScannerClassLoader(getClass().getClassLoader());

		try (JarFile jarFile = new JarFile(file)) {

			LinkedList<Class<?>> linkedList = new LinkedList<>();
			Enumeration<JarEntry> jarEntries = jarFile.entries();

			while (jarEntries.hasMoreElements()) {
				JarEntry jarEntry = jarEntries.nextElement();

				if (jarEntry.getName().contains(".class")) {

					String name = jarEntry.getName().replace(".class", "").replaceAll("/", ".");
					byte[] buff = org.apache.commons.io.IOUtils.toByteArray(jarFile.getInputStream(jarEntry));

					try {
						Class<?> buildClass = loader.buildClass(name, buff);
						linkedList.add(buildClass);
					} catch (NoClassDefFoundError e) {
						// warning....
					}
				}
			}

			return linkedList;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private class JarScannerClassLoader extends ClassLoader {

		public JarScannerClassLoader(ClassLoader parent) {
			super(parent);
		}

		private Class<?> buildClass(String name, byte[] buff) {
			return defineClass(name, buff, 0, buff.length);
		}
	}

}
