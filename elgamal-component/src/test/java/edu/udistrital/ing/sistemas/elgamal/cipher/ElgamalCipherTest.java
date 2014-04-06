package edu.udistrital.ing.sistemas.elgamal.cipher;

import java.math.BigInteger;
import java.security.InvalidKeyException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import edu.udistrital.ing.sistemas.commons.elgamal.cipher.ElgamalCipher;

public class ElgamalCipherTest {

	private ElgamalCipher cipher;

	@Before
	public void init() {
		cipher = new ElgamalCipher();
		cipher.init("54654153459789879874564654");
	}

	@Test
	public void encryptDecryptKey() {
		try {

			String key = "123456789";
			BigInteger[] encode = cipher.encryptKey(key);
			BigInteger decode = cipher.decryptKey(encode);

			Assert.assertEquals(new BigInteger(key), decode);

		} catch (InvalidKeyException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void encryptDecryptText() {
		try {

			String msg = "Hola mundo";
			String encode = cipher.encryptText(msg);
			String decode = cipher.decryptText(encode);

			Assert.assertEquals(msg, decode);

		} catch (InvalidKeyException e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void nameTest() {
		Assert.assertEquals("Elgamal", cipher.getName());
	}
}