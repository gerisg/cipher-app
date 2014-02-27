package edu.udistrital.ing.sistemas.sts;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.udistrital.ing.sistemas.sts.parsers.CommonParser;
import edu.udistrital.ing.sistemas.sts.parsers.CumulativeSumsParser;
import edu.udistrital.ing.sistemas.sts.parsers.LinearComplexityParser;
import edu.udistrital.ing.sistemas.sts.parsers.NonOverlappingTemplateParser;
import edu.udistrital.ing.sistemas.sts.parsers.OverlappingTemplateParser;
import edu.udistrital.ing.sistemas.sts.parsers.RandomExcursionsParser;
import edu.udistrital.ing.sistemas.sts.parsers.RankParser;
import edu.udistrital.ing.sistemas.sts.parsers.SerialParser;
import edu.udistrital.ing.sistemas.sts.parsers.UniversalParser;

/**
 * STS es una herramienta desarrollada por el NIST que ejecuta test relacionados
 * a la aleatoriedad de cadenas <br />
 * Esta clase define el parser necesario para cada uno de los tests soportados.
 * 
 * @author ggallardo
 * 
 */
public class STSTest {

	public static Map<String, CommonParser> parsers;

	static {
		CommonParser commonParser = new CommonParser(2);

		parsers = new HashMap<>();
		parsers.put("ApproximateEntropy", commonParser);
		parsers.put("CumulativeSums", new CumulativeSumsParser());
		parsers.put("Frequency", commonParser);
		parsers.put("LongestRun", new CommonParser(12));
		parsers.put("OverlappingTemplate", new OverlappingTemplateParser());
		parsers.put("RandomExcursionsVariant", new RandomExcursionsParser());
		parsers.put("Runs", commonParser);
		parsers.put("Universal", new UniversalParser());
		parsers.put("BlockFrequency", commonParser);
		parsers.put("FFT", commonParser);
		parsers.put("LinearComplexity", new LinearComplexityParser());
		parsers.put("NonOverlappingTemplate", new NonOverlappingTemplateParser());
		parsers.put("RandomExcursions", new RandomExcursionsParser());
		parsers.put("Rank", new RankParser());
		parsers.put("Serial", new SerialParser());
	}

	public List<String> parseResult(String name) {
		return parsers.get(name).parseResults(name);
	}

	public Map<String, List<String>> parseResults() {
		Map<String, List<String>> results = new HashMap<>();

		for (String name : parsers.keySet())
			results.put(name, parseResult(name));

		return results;
	}

	public String getReport() throws IOException {
		return getFile(CommonParser.TEST_RESULTS_DIR + "finalAnalysisReport.txt");
	}

	public String getFreq() throws IOException {
		return getFile(CommonParser.TEST_RESULTS_DIR + "freq.txt");
	}

	private String getFile(String fn) throws IOException {
		StringBuilder sb = new StringBuilder();
		List<String> lines = Files.readAllLines(Paths.get(fn), StandardCharsets.UTF_8);
		for (String line : lines)
			sb.append(line + "\n");
		return sb.toString();
	}

}