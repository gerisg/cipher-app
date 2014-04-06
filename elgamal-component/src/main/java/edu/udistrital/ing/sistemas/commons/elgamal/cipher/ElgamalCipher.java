package edu.udistrital.ing.sistemas.commons.elgamal.cipher;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.SecureRandom;

import edu.udistrital.ing.sistemas.commons.elgamal.utils.ElGamalKeyPairGenerator;
import edu.udistrital.ing.sistemas.commons.elgamal.utils.ElGamalPrivateKey;
import edu.udistrital.ing.sistemas.commons.elgamal.utils.ElGamalPublicKey;
import edu.udistrital.ing.sistemas.components.Cifrable;

/**
 * @author wbejarano
 */
public class ElgamalCipher implements Cifrable {

	private ElGamalEncryption encrypt;
	private ElGamalPrivateKey eprik;
	private ElGamalPublicKey epubk;
	private ElGamalKeyPairGenerator ekpg;

	public void init(String randomNumber) {

		ekpg = new ElGamalKeyPairGenerator();
		ekpg.initialize(32, new SecureRandom());

		BigInteger seed = new BigInteger(randomNumber);
		KeyPair epair = seed.equals(BigInteger.ZERO) ? ekpg.generateKeyPair() : ekpg.generateKeyPair(seed);

		eprik = (ElGamalPrivateKey) epair.getPrivate();
		epubk = (ElGamalPublicKey) epair.getPublic();
	}

	public BigInteger[] encryptKey(String key) throws InvalidKeyException {
		encrypt = new ElGamalEncryption();
		encrypt.engineInitEncrypt(epubk);
		return encrypt.engineEncrypt(new BigInteger(key));
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
	public String getPrivateKey() {
		return eprik.toString();
	}

	@Override
	public String getPublicKey() {
		return epubk.toString();
	}

	@Override
	public BigInteger getModuleP() {
		return ekpg.getP();
	}

	@Override
	public BigInteger getRandomK() {
		return ekpg.getK();
	}

	@Override
	public String getName() {
		return "Elgamal";
	}

	@Override
	public Type getType() {
		return Type.cipher;
	}
}