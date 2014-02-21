package edu.udistrital.ing.sistemas.sts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class STSParser {

	static final String TEST_RESULTS_DIR = "./experiments/AlgorithmTesting/";
	static final String TEST_RESULTS_FILE = "stats.txt";

	static final String SUCCESS = "SUCCESS";
	static final String FAILURE = "FAILURE";

	public List<String> parseResults(String name) {
		List<String> results = new ArrayList<>();

		File stats = new File(TEST_RESULTS_DIR + name, TEST_RESULTS_FILE);
		System.out.println(stats.getAbsolutePath());
		if (stats.exists()) {

			try (BufferedReader br = new BufferedReader(new FileReader(stats))) {

				String line;
				while ((line = br.readLine()) != null)
					if (line.contains(SUCCESS))
						results.add(SUCCESS);
					else if (line.contains(FAILURE))
						results.add(FAILURE);

			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		}

		return results;
	}
}
