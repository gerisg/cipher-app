package edu.udistrital.ing.sistemas.sts.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LinearComplexityParser extends CommonParser {

	/**
	 * La salida del test LinearComplexity es confusa porque no posee
	 * p_value=valor. Se observó que separando en espacios la línea donde existe
	 * el pValue posee más de 30 términos, con lo cual se utliza esto para
	 * identificar la linea.
	 */
	@Override
	public List<String> parseResults(String name) {
		List<String> results = new ArrayList<>();

		File stats = new File(TEST_RESULTS_DIR + name, TEST_RESULTS_FILE);
		if (stats.exists()) {

			try (BufferedReader br = new BufferedReader(new FileReader(stats))) {

				String line;
				while ((line = br.readLine()) != null) {
					String[] split = line.split(" ");
					if (split.length >= 30)
						results.add(split[split.length - 1]);
				}

			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}

		return results;
	}

	@Override
	public String getMessage() {
		return "";
	}

}