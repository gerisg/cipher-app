package edu.udistrital.ing.sistemas.utils;

import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

public class ChainsUtils {

	/**
	 * Convierte cadenas de bits en enteros de 32 bits.
	 */
	private static Integer[] toInteger(String bits, Object[] recursive) {

		if (bits.length() <= 32)
			return (Integer[]) ArrayUtils.add(recursive, Integer.valueOf(bits, 2));

		Integer current = Integer.valueOf(bits.substring(0, 31), 2);
		Integer[] values = (Integer[]) ArrayUtils.add(recursive, current);

		return toInteger(bits.substring(32, bits.length()), values);
	}

	/**
	 * Método de conveniencia para construir un vector de vectores a partir de
	 * las cadenas generadas
	 */
	public static Vector<Vector<String>> buildVectorData(List<String> chains) {
		Vector<Vector<String>> vector = new Vector<>();

		int i = 1;
		for (String chain : chains) {
			Vector<String> row = new Vector<String>(3);
			
			row.add(String.valueOf(i++));
			row.add(chain);
			row.add(ArrayUtils.toString(toInteger(chain, new Integer[0])));

			vector.add(row);
		}

		return vector;
	}
}
