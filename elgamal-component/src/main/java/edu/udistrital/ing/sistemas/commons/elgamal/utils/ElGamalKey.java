package edu.udistrital.ing.sistemas.commons.elgamal.utils;

import java.math.BigInteger;
import java.security.Key;

/**
 * ElGamal Key Class
 * http://en.pudn.com/downloads85/sourcecode/crypt/detail328862_en.html#
 * 
 * @author Ge ZHANG (2937207)
 * @login name: gz847
 * @version 1.00 07/08/11
 */
public class ElGamalKey implements Key {

	private static final long serialVersionUID = 1L;

	private BigInteger mP, mG;

	public ElGamalKey() {
	}

	public ElGamalKey(BigInteger g, BigInteger p) {
		mG = g;
		mP = p;
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
