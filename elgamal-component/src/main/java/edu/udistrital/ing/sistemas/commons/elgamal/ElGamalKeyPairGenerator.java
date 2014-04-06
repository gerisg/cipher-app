package edu.udistrital.ing.sistemas.commons.elgamal;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.SecureRandom;
import java.util.Random;

/**
 * ElGamal KeypairGenerator Method
 * 
 * @author Ge ZHANG (2937207)
 * @login name: gz847
 * @version 1.00 07/08/11
 */
public class ElGamalKeyPairGenerator extends KeyPairGeneratorSpi {

	private static final BigInteger TWO = BigInteger.valueOf(2);
	private int mStrength;
	private SecureRandom mSecureRandom;

	private BigInteger p;
	private BigInteger k;

	public void initialize(int strength, SecureRandom random) {
		mStrength = strength;
		mSecureRandom = random;
	}

	public void initialize(int strength) {
		initialize(strength, null);
	}

	public KeyPair generateKeyPair() {

		if (mSecureRandom == null) {
			mStrength = 2048;
			mSecureRandom = new SecureRandom();
		}

		p = new BigInteger(mStrength, 99, mSecureRandom);
		BigInteger g = new BigInteger(mStrength - 1, mSecureRandom);
		k = new BigInteger(mStrength - 1, mSecureRandom);
		BigInteger y = g.modPow(k, p);

		ElGamalPublicKey publicKey = new ElGamalPublicKey(y, g, p);
		ElGamalPrivateKey privateKey = new ElGamalPrivateKey(k, g, p);

		return new KeyPair(publicKey, privateKey);
	}

	public KeyPair generateKeyPair(BigInteger q) {

		// p es el módulo
		// g es el generador
		// k es número aleatoreo para la clave privada
		p = calculateP(q);
		BigInteger g = calculateG(p);
		k = calculateK(p);
		BigInteger y = g.modPow(k, p);

		ElGamalPublicKey publicKey = new ElGamalPublicKey(y, g, p);
		ElGamalPrivateKey privateKey = new ElGamalPrivateKey(k, g, p);

		return new KeyPair(publicKey, privateKey);
	}

	public BigInteger calculateP(BigInteger q) {
		return q.isProbablePrime(90) ? q : q.nextProbablePrime();
	}

	/**
	 * SIP certificacion
	 */
	private BigInteger calculateG(BigInteger p) {
		Random random = new Random();

		BigInteger lowerBound = TWO;
		BigInteger upperBound = p.subtract(TWO);

		BigInteger result;
		do {
			result = new BigInteger(p.bitLength(), random);
			result = result.modPow(TWO, p);
		} while ((result.compareTo(lowerBound) < 0) || (result.compareTo(upperBound) > 0) || false == isGenerator(result, p));

		return result;
	}

	private BigInteger calculateK(BigInteger p) {
		Random random = new Random();

		BigInteger lowerBound = TWO;
		BigInteger upperBound = p.subtract(TWO);

		BigInteger result;
		do {
			result = new BigInteger(p.bitLength(), random);
		} while ((result.compareTo(lowerBound) < 0) || (result.compareTo(upperBound) > 0));

		return result;
	}

	private boolean isGenerator(BigInteger generator, BigInteger p) {
		// G^q != 1 y G^2 != 1, Meneses o menezes criptografia handbook
		// Todo modular.

		if (generator.modPow(p, p).compareTo(BigInteger.ONE) == 0) {
			System.exit(0);
			return false;
		}

		if (generator.modPow(TWO, p).compareTo(BigInteger.ONE) == 0) {
			System.exit(0);
			return false;
		}

		return true;
	}

	/**
	 * Es el módulo
	 */
	public BigInteger getP() {
		return p;
	}

	/**
	 * Numero aleatorio utilizado para generar las keys
	 */
	public BigInteger getK() {
		return k;
	}

}