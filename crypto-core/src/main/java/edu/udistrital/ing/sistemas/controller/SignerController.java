package edu.udistrital.ing.sistemas.controller;

import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.udistrital.ing.sistemas.components.Firmable;
import edu.udistrital.ing.sistemas.components.IComponent;
import edu.udistrital.ing.sistemas.components.IComponent.Type;

public class SignerController {

	private Firmable signer;
	private Map<String, IComponent> signers;

	public SignerController(List<IComponent> components) {
		filter(components);
	}

	/**
	 * Filtra sólo firmadores
	 */
	private void filter(List<IComponent> components) {
		signers = new HashMap<>();

		for (IComponent component : components)
			if (component.getType().equals(Type.signer))
				signers.put(component.getName(), component);

		if (signers.isEmpty())
			throw new RuntimeException("Debe existir al menos un componente \"firmador\"");
	}

	/**
	 * Obtener lista de firmadores
	 */
	public String[] getList() {
		return signers.keySet().toArray(new String[signers.size()]);
	}

	/**
	 * Inicializar algoritmo de firmado
	 */
	public void select(String name) {
		signer = (Firmable) signers.get(name);
		signer.init(String.valueOf(new Random().nextInt(Integer.MAX_VALUE)));
	}

	/**
	 * Generar la firma del mensaje
	 */
	public byte[] sign(String msg) throws InvalidKeyException, SignatureException {
		return signer.signKey(msg);
	}

	/**
	 * Verificar si la firma es válida
	 */
	public String verify(byte[] key, String msg) throws InvalidKeyException, SignatureException {
		return signer.signVerify(key, msg);
	}

}