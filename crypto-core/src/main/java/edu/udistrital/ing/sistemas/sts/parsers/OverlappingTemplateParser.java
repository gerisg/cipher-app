package edu.udistrital.ing.sistemas.sts.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Los resultados de OverlapingTemplate no poseen valores para la variable
 * pValue, entonces para mantener la misma línea en la GUI, se devuelve en la
 * respuesta un 1 para los SUCCESS y 0 para los FAILURE.
 */
public class OverlappingTemplateParser extends CommonParser {

	@Override
	public List<String> parseResults(String name) {
		List<String> results = new ArrayList<>();

		File stats = new File(TEST_RESULTS_DIR + name, TEST_RESULTS_FILE);
		if (stats.exists()) {

			try (BufferedReader br = new BufferedReader(new FileReader(stats))) {

				String line;
				while ((line = br.readLine()) != null) {

					if (line.contains(SUCCESS))
						results.add(new String("1.000000"));

					else if (line.contains(FAILURE))
						results.add(new String("0.000000"));
				}

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		return results;
	}
}