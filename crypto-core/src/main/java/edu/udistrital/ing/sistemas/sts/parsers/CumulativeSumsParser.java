package edu.udistrital.ing.sistemas.sts.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cumulative Sums genera para cada cadena dos p_values, uno por test Forward y
 * otro por Reverse. El parser toma los dos valores y retorna el más chico de
 * ambos, de esta manera si al menos uno de los dos tests es FAILURE la cadena
 * se considera inválida.
 */
public class CumulativeSumsParser extends CommonParser {

	@Override
	public List<String> parseResults(String name) {
		List<String> results = new ArrayList<>();

		File stats = new File(TEST_RESULTS_DIR + name, TEST_RESULTS_FILE);
		if (stats.exists()) {

			try (BufferedReader br = new BufferedReader(new FileReader(stats))) {

				String line;
				while ((line = br.readLine()) != null)
					if (line.contains(P_VALUE)) {
						Double value1 = Double.valueOf(parse(line));
						Double value2 = Double.valueOf(parse(nextPvalue(br)));
						results.add(String.valueOf(Math.min(value1, value2)));
					}

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

		return results;
	}

	private String nextPvalue(BufferedReader br) throws IOException {
		String line;

		while ((line = br.readLine()) != null)
			if (line.contains(P_VALUE))
				break;

		return line;
	}

}