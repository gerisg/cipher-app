/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.udistrital.ing.sistemas.cipher.elgamal.component;

/*  
 * ElGamal PublicKey Class  
 *  
 * @author Ge ZHANG (2937207)  
 * @login name: gz847  
 * @version 1.00 07/08/11*/
import java.math.BigInteger;
import java.security.PublicKey;

public class ElGamalPublicKey extends ElGamalKey implements PublicKey {

	private static final long serialVersionUID = 1L;

	private BigInteger mY;

	protected ElGamalPublicKey(BigInteger y, BigInteger g, BigInteger p) {
		super(g, p);
		mY = y;
	}

	protected BigInteger getY() {
		return mY;
	}

	public String toString() {
		return mY + ":" + getG() + ":" + getP();
	}
}
