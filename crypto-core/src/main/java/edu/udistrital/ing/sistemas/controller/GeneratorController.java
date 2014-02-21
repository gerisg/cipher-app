package edu.udistrital.ing.sistemas.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;

import edu.udistrital.ing.sistemas.components.Generable;
import edu.udistrital.ing.sistemas.components.IComponent;
import edu.udistrital.ing.sistemas.gui.AnalyzerGUI;
import edu.udistrital.ing.sistemas.sts.STSTest;
import edu.udistrital.ing.sistemas.utils.RunProcess;

/**
 * Este controlador conoce como trabajar con los componentes generadores
 * 
 * @author ggallardo
 * 
 */
public class GeneratorController {

	private Generable generator;
	private Map<String, IComponent> generators;

	public GeneratorController(Map<String, IComponent> components) {
		generators = filter(components);
	}

	/**
	 * Filtra sólo generadores
	 */
	private Map<String, IComponent> filter(Map<String, IComponent> components) {
		Map<String, IComponent> filtered = new HashMap<>();

		for (Entry<String, IComponent> entry : components.entrySet())
			if (entry.getKey().contains("generator"))
				filtered.put(entry.getKey(), entry.getValue());

		return filtered;
	}

	/**
	 * Obtener lista de generadores
	 */
	public String[] getList() {
		return generators.keySet().toArray(new String[generators.size()]);
	}

	/**
	 * Generar secuencias aleatorias
	 * 
	 * @param name
	 *            : generador a utilizar
	 */
	public void generate(String name) {
		generator = (Generable) generators.get(name);
		generator.generarSecuenciasAleatorias();
	}

	public Object[][] loadData() {

		if (generator == null)
			return new Integer[0][0];

		Map<String, Integer[]> data = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(generator.getAbsoluteRoute()))) {
			String line;
			while ((line = br.readLine()) != null)
				data.put(line, toInteger(line, new Integer[0]));

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return mapToArray(data);
	}

	private Object[][] mapToArray(Map<String, Integer[]> data) {
		Object[][] arr = new Object[data.size()][2];

		int i = 0;
		Iterator<Entry<String, Integer[]>> it = data.entrySet().iterator();
		while (it.hasNext()) {

			Entry<String, Integer[]> map = (Entry<String, Integer[]>) it.next();
			arr[i][0] = map.getKey();
			arr[i][1] = ArrayUtils.toString(map.getValue());

			i++;
		}
		return arr;
	}

	/**
	 * Convierte bits en enteros de 32 bits.
	 */
	private Integer[] toInteger(String bits, Object[] recursive) {

		if (bits.length() <= 32)
			return (Integer[]) ArrayUtils.add(recursive, Integer.valueOf(bits, 2));

		Integer current = Integer.valueOf(bits.substring(0, 31), 2);
		Integer[] values = (Integer[]) ArrayUtils.add(recursive, current);

		return toInteger(bits.substring(32, bits.length()), values);
	}

	// TODO Extraer a otro controller
	public void runTest(String bits, String lines) {
		RunProcess.run("./assess", bits, generator.getAbsoluteRoute(), lines);
	}

	// TODO Extraer a otro controller
	public Map<String, List<String>> getResults() {

		Map<String, List<String>> results = new HashMap<>();

		for (String name : AnalyzerGUI.TESTS_NAME)
			results.put(name, STSTest.parseResult(name));

		return results;
	}

}