package edu.udistrital.ing.sistemas.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Herramienta para la ejecución de procesos externos.
 * 
 * @author ggallardo
 * 
 */
public class RunProcess {

	/**
	 * El primer argumento es el comando a ejecutar y los siguientes son
	 * parámetros de la misma
	 */
	public static void run(String... args) {

		try {
			Process process = new ProcessBuilder(args).start();

			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			String line;
			while ((line = br.readLine()) != null)
				System.out.println(line);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {

	}
}
