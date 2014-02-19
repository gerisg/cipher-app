package edu.udistrital.ing.sistemas.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RunProcess {

	public static void run(String... args) {

		try {
			Process process = new ProcessBuilder(args).start();

			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);

			String line;
			while ((line = br.readLine()) != null)
				System.out.println(line);

			System.out.println("Exit code: " + process.exitValue());

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) {

	}
}
