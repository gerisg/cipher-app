package edu.udistrital.ing.sistemas.sts.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Es un parser genérico para el archivo stats.txt. Solo sirve si el valor de
 * p_value existe con ese nombre de variable. La posición del valor de esta
 * variable se recibe en el constructor.
 * 
 * @author ggallardo
 * 
 */
public class CommonParser {

	static final String ERROR = "Error";
	static final String SUCCESS = "SUCCESS";
	static final String FAILURE = "FAILURE";
	static final String P_VALUE = "p_value";

	static final String TEST_RESULTS_DIR = "experiments/AlgorithmTesting/";
	static final String TEST_RESULTS_FILE = "stats.txt";

	private int position = 2;

	public CommonParser() {
	}

	/**
	 * La posición es donde se encuentra el valor de la variable p_value,
	 * considerando que la línea se separa en espacios en blanco. Por ejemplo:
	 * si la linea a parsear es: "value1 value2 p_value value3", el parámetro
	 * posición debería ser 2.
	 */
	public CommonParser(int position) {
		this.position = position;
	}

	public List<String> parseResults(String name) {
		List<String> results = new ArrayList<>();

		File stats = new File(TEST_RESULTS_DIR + name, TEST_RESULTS_FILE);
		if (stats.exists()) {

			try (BufferedReader br = new BufferedReader(new FileReader(stats))) {

				String line;
				while ((line = br.readLine()) != null)
					if (line.contains(P_VALUE))
						results.add(parse(line));

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		return results;
	}

	protected String parse(String line) {
		String[] split = line.split(" ");
		return split[position];
	}

	public String getMessage() {
		return "";
	}
}
