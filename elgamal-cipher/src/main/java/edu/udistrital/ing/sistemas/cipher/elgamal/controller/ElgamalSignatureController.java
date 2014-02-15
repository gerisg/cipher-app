/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.udistrital.ing.sistemas.cipher.elgamal.controller;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.SignatureException;

import edu.udistrital.ing.sistemas.IFirmable;
import edu.udistrital.ing.sistemas.cipher.elgamal.component.ElGamalEncryption;
import edu.udistrital.ing.sistemas.cipher.elgamal.component.ElGamalKeyPairGenerator;
import edu.udistrital.ing.sistemas.cipher.elgamal.component.ElGamalPrivateKey;
import edu.udistrital.ing.sistemas.cipher.elgamal.component.ElGamalPublicKey;
import edu.udistrital.ing.sistemas.cipher.elgamal.component.ElGamalSignature;
import edu.udistrital.ing.sistemas.cipher.elgamal.tools.MD5;

/*
 Main-Class: edu.udistrital.ing.sistemas.Elgamal.controller.ElgamalCipherController
 Implementation-type: cipher
 Commercial-Name: Cifrador Elgamal
 slug: cifrador-elgamal
 Sign-Main-Class: edu.udistrital.ing.sistemas.Elgamal.controller.ElgamalSignatureController
 Sign-Implementation-type: cipher
 Sign-Commercial-Name: Cifrador Elgamal
 Sign-slug: firmador-elgamal

 /**
 *
 * @author wbejarano
 *
 */
public class ElgamalSignatureController implements IFirmable {

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
		ekpg.initialize(16, new SecureRandom());

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

	public byte[] signKey(String str) throws InvalidKeyException, SignatureException {
		esign = new ElGamalSignature();
		esign.engineInitSign(eprik);

		String hash_str = MD5.md5(str);
		System.out.println("Message : " + str);
		esign.engineUpdate(hash_str.getBytes(), 0, hash_str.length());
		byte[] signedb = esign.engineSign();
		// System.out.println("Signed Message : " + new BigInteger(signedb));
		return signedb;
	}

	public boolean signVerify(byte[] signedb, String mensaje) throws InvalidKeyException, SignatureException {
		String hash_decrypt_str = MD5.md5(mensaje);
		System.out.println("Hashed decrypted message: " + hash_decrypt_str);
		// Verificat mensaje
		esign.engineInitVerify(epubk);
		esign.engineUpdate(hash_decrypt_str.getBytes(), 0, hash_decrypt_str.length());
		boolean veri = esign.engineVerify(signedb);
		if (veri) {
			// System.out.println("Verification Succeeded!");
			return true;
		} else {
			// System.out.println("Verification Failed!");
			return false;
		}
	}

	public String getName() {
		return "Firmador Elgamal";
	}
}
