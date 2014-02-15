/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.udistrital.ing.sistemas.cipher.elgamal.component;

/*  
 * ElGamal KeypairGenerator Method  
 *  
 * @author Ge ZHANG (2937207)  
 * @login name: gz847  
 * @version 1.00 07/08/11*/
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGeneratorSpi;
import java.security.SecureRandom;
import java.util.Random;

public class ElGamalKeyPairGenerator extends KeyPairGeneratorSpi {

	private int mStrength = 0;
	private SecureRandom mSecureRandom = null;
	protected BigInteger mSecureBiginteger;
	private static final BigInteger TWO = BigInteger.valueOf(2);

	public void initialize(int strength, SecureRandom random) {
		mStrength = strength;
		mSecureRandom = random;
	}

	public void initialize(int strength) {
		mStrength = strength;

	}

	public KeyPair generateKeyPair() {
		if (mSecureRandom == null) {
			mStrength = 2048;
			mSecureRandom = new SecureRandom();
		}
		BigInteger p = new BigInteger(mStrength, 99, mSecureRandom);
		BigInteger g = new BigInteger(mStrength - 1, mSecureRandom);
		BigInteger k = new BigInteger(mStrength - 1, mSecureRandom);
		BigInteger y = g.modPow(k, p);

		ElGamalPublicKey publicKey = new ElGamalPublicKey(y, g, p);
		ElGamalPrivateKey privateKey = new ElGamalPrivateKey(k, g, p);
		return new KeyPair(publicKey, privateKey);
	}

	public KeyPair generateKeyPair(BigInteger q) {
		// mSecureBiginteger = q;
		//
		// if (mSecureRandom == null) {
		// mStrength = 1024;
		// mSecureRandom = new SecureRandom();
		// }

		// p -- Es el módulo
		BigInteger p = calculateP(q);
		// BigInteger p = q;
		// BigInteger p = new BigInteger(mStrength, 16, mSecureRandom);

		// g es el generador
		BigInteger g = calculateG(p);
		// BigInteger g = new BigInteger(mStrength - 1, mSecureRandom);

		// k = numero aleatoreo para la clave privada
		// BigInteger k = new BigInteger(mStrength - 1, mSecureRandom);
		BigInteger k = calculateK(p);

		// Clave pública
		BigInteger y = g.modPow(k, p);

		ElGamalPublicKey publicKey = new ElGamalPublicKey(y, g, p);
		ElGamalPrivateKey privateKey = new ElGamalPrivateKey(k, g, p);
		return new KeyPair(publicKey, privateKey);
	}

	public BigInteger calculateP(BigInteger q) {

		if (q.isProbablePrime(90)) {
			return q;
		} else {
			return q.nextProbablePrime();
		}
	}

	// SIP certificacion
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
			// System.out.println(generator.modPow(q, p).toString());
			System.exit(0);

			return false;
		}
		if (generator.modPow(TWO, p).compareTo(BigInteger.ONE) == 0) {
			System.exit(0);
			return false;

		}
		return true;
	}
}