/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.udistrital.ing.sistemas;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * 
 * @author wbejarano
 */
public class ComponentLoader {

	private Map<String, IGenerable> generatorComponents;
	private Map<String, ICifrable> cipherComponents;
	private Map<String, IFirmable> signComponents;
	private static final String componentPath = "components/";

	public ComponentLoader() throws MalformedURLException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		loadComponents();
	}

	private void loadComponents() throws MalformedURLException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		// HasMap para almacenar
		generatorComponents = new HashMap<>();
		cipherComponents = new HashMap<>();
		signComponents = new HashMap<>();

		File dir = new File(componentPath);
		String[] ficheros = dir.list();
		if (ficheros == null) {
			System.out.println("No hay ficheros en el directorio especificado");
		} else {
			for (String fichero : ficheros) {
				// Extraemos la extensión
				int dot = fichero.lastIndexOf(".");
				String extension = fichero.substring(dot + 1);

				if ("jar".equals(extension) || "JAR".equals(extension)) {

					// System.out.println(fichero);
					String jar = componentPath + fichero;
					// Optenermos datos del jar
					URL u = new File(jar).toURI().toURL();

					// Verificamos el jar file
					@SuppressWarnings("resource")
					JarFile jarfile = new JarFile(jar);
					Manifest manifest = jarfile.getManifest();
					Attributes attrs = (Attributes) manifest.getMainAttributes();

					// Obtenemos el nombre de la clase principal
					String className = attrs.getValue("Main-Class");
					// Obtenemos el nombre del tipo
					String Implementation_type = attrs.getValue("Implementation-type");
					// Nombre clave y único que sirve como identificador
					String slugName = attrs.getValue("slug");

					URLClassLoader cl = new URLClassLoader(new URL[] { u });
					switch (Implementation_type) {
					case "generator":
						generatorComponents.put(slugName, (IGenerable) Class.forName(className, true, cl).newInstance());
						break;
					case "cipher":
						cipherComponents.put(slugName, (ICifrable) Class.forName(className, true, cl).newInstance());
						String classSignName = attrs.getValue("Sign-Main-Class");
						String slugSignName = attrs.getValue("Sign-slug");
						signComponents.put(slugSignName, (IFirmable) Class.forName(classSignName, true, cl).newInstance());
						break;
					default:
						break;
					}
				}
			}
		}
	}

	public Map<String, IGenerable> getGeneratorComponents() {
		return generatorComponents;
	}

	public Map<String, ICifrable> getCipherComponents() {
		return cipherComponents;
	}

	public Map<String, IFirmable> getSignComponents() {
		return signComponents;
	}

	public IGenerable getObjectGenerador(String slug) {
		return (IGenerable) generatorComponents.get(slug);
	}

	public ICifrable getObjectCifrador(String slug) {
		return (ICifrable) cipherComponents.get(slug);
	}

	public IFirmable getObjectFirmador(String slug) {
		return (IFirmable) signComponents.get(slug);
	}

}
