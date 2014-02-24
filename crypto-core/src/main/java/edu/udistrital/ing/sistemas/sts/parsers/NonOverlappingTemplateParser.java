package edu.udistrital.ing.sistemas.sts.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Este parser recorre las 148 ventanas que posee para cada cadena y realiza un
 * promedio de estos pValues
 */
public class NonOverlappingTemplateParser extends CommonParser {

	@Override
	public List<String> parseResults(String name) {
		List<String> results = new ArrayList<>();

		File stats = new File(TEST_RESULTS_DIR + name, TEST_RESULTS_FILE);
		if (stats.exists()) {

			try (BufferedReader br = new BufferedReader(new FileReader(stats))) {

				int z = 0;
				boolean start = false;
				Double sum = new Double(0.0);

				String line;
				while ((line = br.readLine()) != null) {

					int i = line.indexOf(SUCCESS);
					int j = line.indexOf(FAILURE);

					if (i != -1) {

						if (!start)
							start = true;
						sum += Double.valueOf(line.substring(i - 10, i - 2));
						z++;

					} else if (j != -1) {

						if (!start)
							start = true;
						sum += Double.valueOf(line.substring(j - 10, j - 2));
						z++;

					} else if (start) {
						results.add(String.valueOf(sum / z));

						z = 0;
						start = false;
						sum = new Double(0.0);
					}
				}

			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}

		return results;
	}
}