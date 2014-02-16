package edu.udistrital.ing.sistemas.cipher.elgamal.controller;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.SecureRandom;

import edu.udistrital.ing.sistemas.ICifrable;
import edu.udistrital.ing.sistemas.cipher.elgamal.component.ElGamalEncryption;
import edu.udistrital.ing.sistemas.cipher.elgamal.component.ElGamalKeyPairGenerator;
import edu.udistrital.ing.sistemas.cipher.elgamal.component.ElGamalPrivateKey;
import edu.udistrital.ing.sistemas.cipher.elgamal.component.ElGamalPublicKey;

/**
 * @author wbejarano
 */
public class ElgamalCipherController implements ICifrable {

	private ElGamalEncryption encrypt;
	private ElGamalPrivateKey eprik;
	private ElGamalPublicKey epubk;

	public void init(String randomNumber) {

		ElGamalKeyPairGenerator ekpg = new ElGamalKeyPairGenerator();
		ekpg.initialize(32, new SecureRandom());

		BigInteger seed = new BigInteger(randomNumber);
		KeyPair epair = seed.equals(new BigInteger("0")) ? ekpg.generateKeyPair() : ekpg.generateKeyPair(seed);

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