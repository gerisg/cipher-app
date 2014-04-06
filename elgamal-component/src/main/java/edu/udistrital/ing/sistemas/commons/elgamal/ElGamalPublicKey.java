package edu.udistrital.ing.sistemas.commons.elgamal;

import java.math.BigInteger;
import java.security.PublicKey;

/**
 * ElGamal PublicKey Class
 * 
 * @author Ge ZHANG (2937207)
 * @login name: gz847
 * @version 1.00 07/08/11
 */
public class ElGamalPublicKey implements PublicKey {

	private static final long serialVersionUID = 7975151541692062391L;

	private BigInteger mY, mP, mG;

	public ElGamalPublicKey(BigInteger y, BigInteger g, BigInteger p) {
		mY = y;
		mG = g;
		mP = p;
	}

	public BigInteger getY() {
		return mY;
	}

	public BigInteger getG() {
		return mG;
	}

	public BigInteger getP() {
		return mP;
	}

	public String toString() {
		return mY + ":" + getG() + ":" + getP();
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
