package edu.udistrital.ing.sistemas.commons.elgamal.utils;

import java.math.BigInteger;
import java.security.PrivateKey;

/**
 * ElGamal PrivateKey Class
 * 
 * @author Ge ZHANG (2937207)
 * @login name: gz847
 * @version 1.00 07/08/11
 */
public class ElGamalPrivateKey extends ElGamalKey implements PrivateKey {

	private static final long serialVersionUID = 1L;

	private BigInteger mK;

	public ElGamalPrivateKey() {
		super();
	}
	
	public ElGamalPrivateKey(BigInteger k, BigInteger g, BigInteger p) {
		super(g, p);
		mK = k;
	}

	public BigInteger getK() {
		return mK;
	}

	public String toString() {
		return mK + ":" + getG() + ":" + getP();
	}
}
