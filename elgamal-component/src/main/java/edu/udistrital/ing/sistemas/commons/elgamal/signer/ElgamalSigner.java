package edu.udistrital.ing.sistemas.commons.elgamal.signer;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.SignatureException;

import edu.udistrital.ing.sistemas.commons.elgamal.ElGamalKeyPairGenerator;
import edu.udistrital.ing.sistemas.commons.elgamal.ElGamalPrivateKey;
import edu.udistrital.ing.sistemas.commons.elgamal.ElGamalPublicKey;
import edu.udistrital.ing.sistemas.commons.elgamal.utils.MD5;
import edu.udistrital.ing.sistemas.components.Firmable;

/**
 * @author wbejarano
 */
public class ElgamalSigner implements Firmable {

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

		esign = new ElGamalSignature();
		esign.engineInitSign(eprik);

		String hashStr = MD5.md5(str);
		esign.engineUpdate(hashStr.getBytes(), 0, hashStr.length());

		return esign.engineSign();
	}

	public String signVerify(byte[] signedb, String mensaje) throws InvalidKeyException, SignatureException {

		String hashDecryptStr = MD5.md5(mensaje);

		esign.engineInitVerify(epubk);
		esign.engineUpdate(hashDecryptStr.getBytes(), 0, hashDecryptStr.length());

		boolean engineVerify = esign.engineVerify(signedb);
		if (engineVerify)
			return hashDecryptStr;

		return "INVALID SIGNATURE";
	}

	@Override
	public String getName() {
		return "Elgamal";
	}

	@Override
	public Type getType() {
		return Type.signer;
	}

}