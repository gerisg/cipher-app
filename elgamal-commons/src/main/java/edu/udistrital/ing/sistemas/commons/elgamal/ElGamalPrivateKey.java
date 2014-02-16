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
public class ElGamalPrivateKey extends ElGamalKey implements PrivateKey {

	private static final long serialVersionUID = 1L;

	private BigInteger mK;

	protected ElGamalPrivateKey(BigInteger k, BigInteger g, BigInteger p) {
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
