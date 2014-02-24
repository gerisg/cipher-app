package edu.udistrital.ing.sistemas.sts.parsers;

import java.util.Collections;
import java.util.List;

public class UniversalParser extends CommonParser {

	/**
	 * TODO Implementar cuando se conozca cómo es la salida correcta
	 */
	@Override
	public List<String> parseResults(String name) {
		return Collections.emptyList();
	}

	@Override
	public String getMessage() {
		return "ERROR:  L IS OUT OF RANGE. -OR- :  Q IS LESS THAN 320.000000. -OR- :  Unable to allocate T.";
	}

}