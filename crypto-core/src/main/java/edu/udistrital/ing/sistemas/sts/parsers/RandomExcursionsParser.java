package edu.udistrital.ing.sistemas.sts.parsers;

import java.util.Collections;
import java.util.List;

public class RandomExcursionsParser extends CommonParser {

	/**
	 * TODO Implementar cuando se conozca cómo es la salida correcta
	 */
	@Override
	public List<String> parseResults(String name) {
		return Collections.emptyList();
	}

	@Override
	public String getMessage() {
		return "WARNING:  TEST NOT APPLICABLE.  THERE ARE AN INSUFFICIENT NUMBER OF CYCLES.";
	}
}
