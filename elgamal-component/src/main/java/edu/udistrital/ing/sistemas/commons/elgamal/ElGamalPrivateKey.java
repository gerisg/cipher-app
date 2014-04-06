package edu.udistrital.ing.sistemas.commons.elgamal;

import java.math.BigInteger;
import java.security.PrivateKey;

/**
 * ElGamal PrivateKey Class
 * 
 * @author Ge ZHANG (2937207)
 * @login name: gz847
 * @version 1.00 07/08/11
 */
public class ElGamalPrivateKey implements PrivateKey {

	private static final long serialVersionUID = 2523700417246967018L;

	private BigInteger mK, mG, mP;

	public ElGamalPrivateKey(BigInteger k, BigInteger g, BigInteger p) {
		mK = k;
		mG = g;
		mP = p;
	}

	public BigInteger getK() {
		return mK;
	}

	public String toString() {
		return mK + ":" + getG() + ":" + getP();
	}

	public BigInteger getG() {
		return mG;
	}

	public BigInteger getP() {
		return mP;
	}

	public String getAlgorithm() {
		return "ElGamal";
	}

	public String getFormat() {
		return "NONE";
	}

	public byte[] getEncoded() {
		return null;
	}
}