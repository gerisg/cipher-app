package edu.udistrital.ing.sistemas.sts.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Los resultados de test Rank no pueden buscar sólo por P_VALUE dado que esta
 * variable es mostrada con valor 0 cuando existe algún error. Por este motivo,
 * primero se verifica si existe algún error, en ese caso se skipea la línea
 * siguiente que es la que contiene el valor. En el caso que no tengamos error,
 * se splitea la línea y se lee el valor de la última posición.
 */
public class RankParser extends CommonParser {

	@Override
	public List<String> parseResults(String name) {
		List<String> results = new ArrayList<>();

		File stats = new File(TEST_RESULTS_DIR + name, TEST_RESULTS_FILE);
		if (stats.exists()) {

			try (BufferedReader br = new BufferedReader(new FileReader(stats))) {

				String line;
				while ((line = br.readLine()) != null)
					if (line.contains(ERROR))
						br.readLine(); // skip
					else if (line.contains(P_VALUE))
						results.add(parse(line));

			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}

		return results;

	}

	@Override
	public String getMessage() {
		return "Error: Insufficient # Of Bits To Define An 32x32 (32x32) Matrix";

	}

}