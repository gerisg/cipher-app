/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
import edu.udistrital.ing.sistemas.cipher.elgamal.component.ElGamalSignature;

/**
 * 
 * @author wbejarano
 * 
 */
public class ElgamalCipherController implements ICifrable {

	// Decalaraci{on de variables globales
	static ElGamalSignature esign;
	static ElGamalEncryption encrypt;
	static ElGamalKeyPairGenerator ekpg;
	static KeyPair epair;
	static SecureRandom s;
	static BigInteger seed;
	private ElGamalPrivateKey eprik;
	private ElGamalPublicKey epubk;

	public void init(String random_number) {

		seed = new BigInteger(random_number);

		ekpg = new ElGamalKeyPairGenerator();
		ekpg.initialize(32, new SecureRandom());

		if (seed.compareTo(new BigInteger("0")) == 0) {
			epair = ekpg.generateKeyPair();
		} else {
			epair = ekpg.generateKeyPair(seed);
		}
		eprik = (ElGamalPrivateKey) epair.getPrivate();
		epubk = (ElGamalPublicKey) epair.getPublic();

		// System.out.println("Private Key: k = " + eprik.getK() + ", g = " +
		// eprik.getG() + ", p = " + eprik.getP());
		// System.out.println("Public Key: y = " + epubk.getY() + ", g = " +
		// epubk.getG() + ", p = " + epubk.getP());
	}

	public BigInteger[] encryptKey(String key) throws InvalidKeyException {
		encrypt = new ElGamalEncryption();
		encrypt.engineInitEncrypt(epubk);
		BigInteger msg_num = new BigInteger(key);
		BigInteger[] encryptedKey = encrypt.engineEncrypt(msg_num);
		System.out.println("Encrpyted Message key: " + encryptedKey[0] + "," + encryptedKey[1]);
		return encryptedKey;
	}

	public String encryptText(String message) throws InvalidKeyException {
		encrypt = new ElGamalEncryption();
		encrypt.engineInitEncrypt(epubk);
		String encryptedText = encrypt.engineTextEncrypt(message);
		// System.out.println("Encrpyted Message Text: " + encryptedText);
		return encryptedText;
	}

	public BigInteger decryptKey(BigInteger[] encryptedmsg) throws InvalidKeyException {
		BigInteger C;
		encrypt.engineInitDecrypt(eprik);
		C = encrypt.engineDecrypt(encryptedmsg);
		// System.out.println("Decrypted Message: " + C);
		return C;
	}

	public String decryptText(String encryptedmsg) throws InvalidKeyException {
		String C;
		encrypt.engineInitDecrypt(eprik);
		C = encrypt.engineTextDecrypt(encryptedmsg);
		// System.out.println("Decrypted Text Message: " + C);
		return C;
	}

	@Override
	public String getName() {
		return "Cifrador Elgamal";
	}
}
