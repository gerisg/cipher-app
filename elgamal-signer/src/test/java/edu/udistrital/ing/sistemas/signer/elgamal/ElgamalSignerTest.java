package edu.udistrital.ing.sistemas.signer.elgamal;

import java.security.InvalidKeyException;
import java.security.SignatureException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ElgamalSignerTest {

	private ElgamalSigner signer;

	@Before
	public void init() {
		signer = new ElgamalSigner();
		signer.init("54654153459789879874564654");
	}

	@Test
	public void generateAndVerifySignature() {
		try {
			String str = "Hola mundo";

			// Generate signature
			byte[] signKey = signer.signKey(str);
			Assert.assertEquals(22, signKey.length);

			// Verify signature
			boolean verified = signer.signVerify(signKey, str);
			Assert.assertEquals(true, verified);

		} catch (InvalidKeyException | SignatureException e) {
			Assert.fail("Error validando firma");
		}
	}

	@Test
	public void nameTest() {
		Assert.assertEquals("Firmador Elgamal", signer.getName());
	}

}
