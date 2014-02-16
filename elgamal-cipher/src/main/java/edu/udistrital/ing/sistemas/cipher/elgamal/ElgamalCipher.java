package edu.udistrital.ing.sistemas.cipher.elgamal;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.SecureRandom;

import edu.udistrital.ing.sistemas.commons.elgamal.ElGamalKeyPairGenerator;
import edu.udistrital.ing.sistemas.commons.elgamal.ElGamalPrivateKey;
import edu.udistrital.ing.sistemas.commons.elgamal.ElGamalPublicKey;
import edu.udistrital.ing.sistemas.components.Cifrable;

/**
 * @author wbejarano
 */
public class ElgamalCipher implements Cifrable {

	private ElGamalEncryption encrypt;
	private ElGamalPrivateKey eprik;
	private ElGamalPublicKey epubk;

	public void init(String randomNumber) {

		ElGamalKeyPairGenerator ekpg = new ElGamalKeyPairGenerator();
		ekpg.initialize(32, new SecureRandom());

		BigInteger seed = new BigInteger(randomNumber);
		KeyPair epair = seed.equals(BigInteger.ZERO) ? ekpg.generateKeyPair() : ekpg.generateKeyPair(seed);

		eprik = (ElGamalPrivateKey) epair.getPrivate();
		epubk = (ElGamalPublicKey) epair.getPublic();
	}

	public BigInteger[] encryptKey(String key) throws InvalidKeyException {
		encrypt = new ElGamalEncryption();
		encrypt.engineInitEncrypt(epubk);
		BigInteger msgNum = new BigInteger(key);
		BigInteger[] encryptedKey = encrypt.engineEncrypt(msgNum);
		System.out.println("Encrypted message key: " + encryptedKey[0] + "," + encryptedKey[1]);
		return encryptedKey;
	}

	public String encryptText(String message) throws InvalidKeyException {
		encrypt = new ElGamalEncryption();
		encrypt.engineInitEncrypt(epubk);
		return encrypt.engineTextEncrypt(message);
	}

	public BigInteger decryptKey(BigInteger[] encryptedMsg) throws InvalidKeyException {
		encrypt.engineInitDecrypt(eprik);
		return encrypt.engineDecrypt(encryptedMsg);
	}

	public String decryptText(String encryptedMsg) throws InvalidKeyException {
		encrypt.engineInitDecrypt(eprik);
		return encrypt.engineTextDecrypt(encryptedMsg);
	}

	@Override
	public String getName() {
		return "Cifrador Elgamal";
	}
}