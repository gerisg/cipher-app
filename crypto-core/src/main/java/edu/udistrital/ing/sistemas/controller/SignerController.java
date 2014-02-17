package edu.udistrital.ing.sistemas.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import edu.udistrital.ing.sistemas.components.Firmable;
import edu.udistrital.ing.sistemas.components.IComponent;

public class SignerController {

	private Firmable signer;
	private Map<String, IComponent> signers;

	public SignerController(Map<String, IComponent> components) {
		signers = filter(components);
	}

	/**
	 * Filtra sólo firmadores
	 */
	private Map<String, IComponent> filter(Map<String, IComponent> components) {
		Map<String, IComponent> filtered = new HashMap<>();

		for (Entry<String, IComponent> entry : components.entrySet())
			if (entry.getKey().contains("signer"))
				filtered.put(entry.getKey(), entry.getValue());

		return filtered;
	}

}
