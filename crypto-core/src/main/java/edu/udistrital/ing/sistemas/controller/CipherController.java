package edu.udistrital.ing.sistemas.controller;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import edu.udistrital.ing.sistemas.components.Cifrable;
import edu.udistrital.ing.sistemas.components.IComponent;
import edu.udistrital.ing.sistemas.components.IComponent.Type;

/**
 * 
 * Este controlador se encarga de trabajar con cifradores
 * 
 * @author ggallardo
 * 
 */
public class CipherController {

	private Cifrable cipher;
	private Map<String, IComponent> ciphers;

	public CipherController(Map<Type, IComponent> components) {
		ciphers = filter(components);
	}

	/**
	 * Filtra sólo cifradores
	 */
	private Map<String, IComponent> filter(Map<Type, IComponent> components) {
		Map<String, IComponent> filtered = new HashMap<>();

		for (Entry<Type, IComponent> entry : components.entrySet())
			if (entry.getKey().equals(Type.cipher))
				filtered.put(entry.getValue().getName(), entry.getValue());

		return filtered;
	}

	/**
	 * Obtener lista de cifradores
	 */
	public String[] getList() {
		return ciphers.keySet().toArray(new String[ciphers.size()]);
	}

	/**
	 * Inicializar algoritmo de cifrado
	 */
	public void select(String name) {
		cipher = (Cifrable) ciphers.get(name);
		cipher.init(String.valueOf(new Random().nextInt(Integer.MAX_VALUE)));
	}

	/**
	 * Encriptar un mensaje de texto
	 */
	public String encryptText(String message) throws InvalidKeyException {
		return cipher.encryptText(message);
	}

	/**
	 * Desencriptar un mensaje de texto cifrado
	 */
	public String decryptText(String encrypted) throws InvalidKeyException {
		return cipher.decryptText(encrypted);
	}

	/**
	 * Mostrar clave privada
	 */
	public String getPrivateKey() {
		return cipher.getPrivateKey();
	}

	/**
	 * Mostrar clave publica
	 */
	public String getPublicKey() {
		return cipher.getPublicKey();
	}

	public String getModuleP() {
		BigInteger moduleP = cipher.getModuleP();
		return moduleP.toString();
	}

	public String getRandomK() {
		BigInteger randomK = cipher.getRandomK();
		return randomK.toString();
	}

}