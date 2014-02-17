package edu.udistrital.ing.sistemas.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.udistrital.ing.sistemas.components.Cifrable;
import edu.udistrital.ing.sistemas.components.IComponent;

public class CipherController {

	private Cifrable cipher;
	private Map<String, IComponent> ciphers;

	public CipherController(Map<String, IComponent> components) {
		ciphers = filter(components);
	}

	/**
	 * Filtra sólo cifradores
	 */
	private Map<String, IComponent> filter(Map<String, IComponent> components) {
		Map<String, IComponent> filtered = new HashMap<>();

		for (Entry<String, IComponent> entry : components.entrySet())
			if (entry.getKey().contains("cipher"))
				filtered.put(entry.getKey(), entry.getValue());

		return filtered;
	}

}
