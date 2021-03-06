package edu.udistrital.ing.sistemas.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.udistrital.ing.sistemas.components.Generable;
import edu.udistrital.ing.sistemas.components.IComponent;
import edu.udistrital.ing.sistemas.components.IComponent.Type;
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

	private Chain chain;
	private List<String> chains;

	private STSTest tests;

	public ChainsController(List<IComponent> components) {
		filter(components);
		tests = new STSTest();
	}

	/**
	 * Filtra sólo generadores de cadenas
	 */
	private void filter(List<IComponent> components) {
		generators = new HashMap<>();

		for (IComponent component : components)
			if (component.getType().equals(Type.generator))
				generators.put(component.getName(), component);

		if (generators.isEmpty())
			throw new RuntimeException("Debe existir al menos un componente \"generador\"");
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

	public Chain getChain() {
		return chain;
	}

	public void setChain(int index) {
		this.chain = new Chain(index + 1, chains.get(index));
	}

	public void removeChain() {
		this.chain = null;
	}

	public class Chain {

		private String text;
		private int index;

		public Chain(int index, String text) {
			this.index = index;
			this.text = text;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

	}

	public String getFreq() throws IOException {
		return tests.getFreq();
	}

	public String getReport() throws IOException {
		return tests.getReport();
	}

}