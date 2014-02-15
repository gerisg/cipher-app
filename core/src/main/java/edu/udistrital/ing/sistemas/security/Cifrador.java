/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.udistrital.ing.sistemas.security;

import java.math.BigInteger;
import java.security.InvalidKeyException;

import edu.udistrital.ing.sistemas.ICifrable;

/*
 * @author wbejarano
 */
public class Cifrador implements ICifrable {

	private static Cifrador instance;
	private ICifrable servicioCifrador;

	private Cifrador(ICifrable componenteCifrable) {
		servicioCifrador = componenteCifrable;
	}

	public static Cifrador getInstance(ICifrable componenteCifrable, String seed) {
		if (instance == null) {
			instance = new Cifrador(componenteCifrable);
			instance.init(seed);
		} else {
			instance.reloadCifrador(componenteCifrable, seed);
		}
		return instance;
	}

	private void reloadCifrador(ICifrable componenteCifrable, String seed) {
		servicioCifrador = componenteCifrable;
		instance.init(seed);
	}

	@Override
	public String getName() {
		return servicioCifrador.getName();
	}

	@Override
	public void init(String random_number) {
		servicioCifrador.init(random_number);
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
	public BigInteger decryptKey(BigInteger[] encryptedmsg) throws InvalidKeyException {
		return servicioCifrador.decryptKey(encryptedmsg);
	}

	@Override
	public String decryptText(String encryptedmsg) throws InvalidKeyException {
		return servicioCifrador.decryptText(encryptedmsg);
	}

}
