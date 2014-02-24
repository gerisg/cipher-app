package edu.udistrital.ing.sistemas.sts.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Serial test genera dos pValue y este parser lo que hace es enviar como
 * resultado el menor de ellos. Entonces en el caso que al menos uno de ellos
 * sea FAILURE, la cadena es invalida.
 */
public class SerialParser extends CommonParser {

	@Override
	public List<String> parseResults(String name) {
		List<String> results = new ArrayList<>();

		File stats = new File(TEST_RESULTS_DIR + name, TEST_RESULTS_FILE);
		if (stats.exists()) {

			try (BufferedReader br = new BufferedReader(new FileReader(stats))) {

				Double value1 = null, value2 = null;

				String line;
				while ((line = br.readLine()) != null) {

					if (line.contains(P_VALUE + 1)) {
						value1 = Double.valueOf(parse(line));
						value2 = Double.valueOf(parse(br.readLine()));

						results.add(String.valueOf(Math.min(value1, value2)));
					}
				}

			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}

		return results;
	}
}
