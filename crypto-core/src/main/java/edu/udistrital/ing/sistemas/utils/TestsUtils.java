package edu.udistrital.ing.sistemas.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import edu.udistrital.ing.sistemas.sts.STSTest;

public class TestsUtils {

	private static final Double ALPHA = new Double(0.01);

	private Vector<Vector<String>> data;
	private Vector<String> columnNames;
	private Map<String, String> errors;
	private List<Integer> failures;

	public TestsUtils() {
		data = new Vector<Vector<String>>();
		errors = new HashMap<String, String>();
		failures = new ArrayList<Integer>();
		columnNames = new Vector<String>();
		columnNames.add("ID");
	}

	/**
	 * Método de conveniencia para construir los vectores de datos y nombres de
	 * columnas a partir de las resultados de los tests del NIST
	 */
	public void buildVectors(Map<String, List<String>> results) {

		// Load vector with tests results
		for (Entry<String, List<String>> entry : results.entrySet()) {
			List<String> list = entry.getValue();

			// Test errors
			if (list.isEmpty()) {
				errors.put(entry.getKey(), STSTest.parsers.get(entry.getKey()).getMessage());
				continue;
			}

			// Create pool
			if (data.isEmpty())
				initPool(list.size());

			// Complete with results
			for (int i = 0; i < list.size(); i++) {
				String value = list.get(i);
				data.get(i).add(value);

				try {
					if (Double.valueOf(value) < ALPHA)
						failures.add(i);
				} catch (NumberFormatException e) {
					// Nothing
				}

			}

			// Save test name
			columnNames.add(entry.getKey());
		}

		// Last column: results
		columnNames.add("Results");

		for (int i = 0; i < data.size(); i++) {
			String msg = "SUCCESS";

			if (failures.contains(new Integer(i)))
				msg = "FAILURE";

			data.get(i).add(msg);
		}
	}

	/**
	 * Crea un pool de vectores y los inicia con un elemento que corresponde al
	 * ID de cada fila
	 */
	private void initPool(int size) {

		for (int i = 0; i < size; i++) {
			Vector<String> vector = new Vector<String>(17);
			vector.add(String.valueOf(i));

			data.add(vector);
		}
	}

	public Vector<Vector<String>> getData() {
		return data;
	}

	public Vector<String> getColumnNames() {
		return columnNames;
	}

	public Map<String, String> getErrors() {
		return errors;
	}
}