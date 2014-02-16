package edu.udistrital.ing.sistemas.security;

import java.math.BigInteger;
import java.security.InvalidKeyException;

import edu.udistrital.ing.sistemas.ICifrable;

/**
 * @author wbejarano
 */
public class Cifrador implements ICifrable {

	private ICifrable servicioCifrador;
	private static Cifrador instance;

	private Cifrador(ICifrable componenteCifrable, String seed) {
		servicioCifrador = componenteCifrable;
		init(seed);
	}

	public static Cifrador getInstance(ICifrable componenteCifrable, String seed) {

		if (instance == null)
			instance = new Cifrador(componenteCifrable, seed);

		return instance;
	}

	@Override
	public String getName() {
		return servicioCifrador.getName();
	}

	@Override
	public void init(String randomNumber) {
		servicioCifrador.init(randomNumber);
	}

	@Override
	public BigInteger[] encryptKey(String key) throws InvalidKeyException {
		return servicioCifrador.encryptKey(key);
	}

	@Override
	public String encryptText(String message) throws InvalidKeyException {
		return servicioCifrador.encryptText(message);
	}

	@Override
	public BigInteger decryptKey(BigInteger[] encryptedMsg) throws InvalidKeyException {
		return servicioCifrador.decryptKey(encryptedMsg);
	}

	@Override
	public String decryptText(String encryptedMsg) throws InvalidKeyException {
		return servicioCifrador.decryptText(encryptedMsg);
	}

}