package edu.udistrital.ing.sistemas.commons.elgamal.utils;

import java.math.BigInteger;
import java.security.PublicKey;

/**
 * ElGamal PublicKey Class
 * 
 * @author Ge ZHANG (2937207)
 * @login name: gz847
 * @version 1.00 07/08/11
 */
public class ElGamalPublicKey extends ElGamalKey implements PublicKey {

	private static final long serialVersionUID = 1L;

	private BigInteger mY;

	public ElGamalPublicKey(BigInteger y, BigInteger g, BigInteger p) {
		super(g, p);
		mY = y;
	}

	public BigInteger getY() {
		return mY;
	}

	public String toString() {
		return mY + ":" + getG() + ":" + getP();
	}
}
