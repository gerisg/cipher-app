package edu.udistrital.ing.sistemas.sts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class STSTest {

	private static Map<String, STSParser> tests;

	static {
		STSParser parser = new STSParser();
		tests = new HashMap<>();
		tests.put("ApproximateEntropy", parser);
		tests.put("CumulativeSums", new CumulativeSums());
		tests.put("Frequency", parser);
		tests.put("LongestRun", parser);
		tests.put("OverlappingTemplate", parser);
		tests.put("RandomExcursionsVariant", parser);
		tests.put("Runs", parser);
		tests.put("Universal", parser);
		tests.put("BlockFrequency", parser);
		tests.put("FFT", parser);
		// TODO No tiene success
		tests.put("LinearComplexity", new LinearComplexity());
		// TODO Tiene ventanas
		tests.put("NonOverlappingTemplate", new NonOverlappingTemplate());
		// TODO Lanza warning!
		tests.put("RandomExcursions", parser);
		// TODO Tiene success y error
		tests.put("Rank", new Rank());
		// TODO Tiene doble success
		tests.put("Serial", new Serial());
	}

	public static List<String> parseResult(String name) {
		return tests.get(name).parseResults(name);
	}

}
