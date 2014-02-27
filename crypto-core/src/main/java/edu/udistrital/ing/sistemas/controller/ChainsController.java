package edu.udistrital.ing.sistemas.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.udistrital.ing.sistemas.components.Generable;
import edu.udistrital.ing.sistemas.components.IComponent;
import edu.udistrital.ing.sistemas.sts.STSTest;
import edu.udistrital.ing.sistemas.utils.RunProcess;

/**
 * Este controlador conoce como trabajar con cadenas
 * 
 * @author ggallardo
 * 
 */
public class ChainsController {

	private Generable generator;
	private Map<String, IComponent> generators;

	private String chain;
	private List<String> chains;

	private STSTest tests;

	public ChainsController(Map<String, IComponent> components) {
		generators = filter(components);
		tests = new STSTest();
	}

	/**
	 * Filtra sólo generadores de cadenas
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
	public void generate(String name, int rows, int columns) {
		generator = (Generable) generators.get(name);
		generator.generarSecuenciasAleatorias(rows, columns);
		chains = loadDataGenerated();
	}

	/**
	 * Parsea el archivo generado con cadenas aleatorias
	 */
	private List<String> loadDataGenerated() {

		List<String> data = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(generator.getAbsoluteRoute()))) {
			String line;
			while ((line = br.readLine()) != null)
				data.add(line);

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		return data;
	}

	/**
	 * Ejecuta los tests de la herramienta externa generada por el NIST
	 * (http://csrc.nist.gov). La herramienta se encarga de realizar diferentes
	 * tests sobre cadenas para determinar la aleatoriedad.
	 */
	public void runTest(int columns, int rows) {
		RunProcess.run("./assess", String.valueOf(columns), generator.getAbsoluteRoute(), String.valueOf(rows));
	}

	/**
	 * Parsea los resultados de cada test de la herramienta del NIST
	 */
	public Map<String, List<String>> getResults() {
		return tests.parseResults();
	}

	public List<String> getChains() {
		return this.chains;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(int index) {
		this.chain = chains.get(index);
	}

	public void removeChain() {
		this.chain = "";
	}

	public String getFreq() throws IOException {
		return tests.getFreq();
	}

	public String getReport() throws IOException {
		return tests.getReport();
	}

}