package edu.udistrital.ing.sistemas.cipher.elgamal.controller;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.SignatureException;

import edu.udistrital.ing.sistemas.IFirmable;
import edu.udistrital.ing.sistemas.cipher.elgamal.component.ElGamalKeyPairGenerator;
import edu.udistrital.ing.sistemas.cipher.elgamal.component.ElGamalPrivateKey;
import edu.udistrital.ing.sistemas.cipher.elgamal.component.ElGamalPublicKey;
import edu.udistrital.ing.sistemas.cipher.elgamal.component.ElGamalSignature;
import edu.udistrital.ing.sistemas.cipher.elgamal.tools.MD5;

/**
 * @author wbejarano
 */
public class ElgamalSignatureController implements IFirmable {

	private ElGamalSignature esign;
	private ElGamalPrivateKey eprik;
	private ElGamalPublicKey epubk;

	public void init(String randomNumber) {

		ElGamalKeyPairGenerator ekpg = new ElGamalKeyPairGenerator();
		ekpg.initialize(16, new SecureRandom());

		BigInteger seed = new BigInteger(randomNumber);
		KeyPair epair = seed.equals(new BigInteger("0")) ? ekpg.generateKeyPair() : ekpg.generateKeyPair(seed);

		eprik = (ElGamalPrivateKey) epair.getPrivate();
		epubk = (ElGamalPublicKey) epair.getPublic();
	}

	public byte[] signKey(String str) throws InvalidKeyException, SignatureException {

		System.out.println("Message : " + str);

		esign = new ElGamalSignature();
		esign.engineInitSign(eprik);

		String hashStr = MD5.md5(str);
		esign.engineUpdate(hashStr.getBytes(), 0, hashStr.length());

		return esign.engineSign();
	}

	public boolean signVerify(byte[] signedb, String mensaje) throws InvalidKeyException, SignatureException {

		String hashDecryptStr = MD5.md5(mensaje);
		System.out.println("Hashed decrypted message: " + hashDecryptStr);

		esign.engineInitVerify(epubk);
		esign.engineUpdate(hashDecryptStr.getBytes(), 0, hashDecryptStr.length());

		return esign.engineVerify(signedb);
	}

	public String getName() {
		return "Firmador Elgamal";
	}

}