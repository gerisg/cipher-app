/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.udistrital.ing.sistemas.cipher.elgamal.component;

/*  
 * ElGamal PrivateKey Class  
 *  
 * @author Ge ZHANG (2937207)  
 * @login name: gz847  
 * @version 1.00 07/08/11*/

import java.math.BigInteger;
import java.security.PrivateKey;

public class ElGamalPrivateKey extends ElGamalKey implements PrivateKey {

	private static final long serialVersionUID = 1L;

	private BigInteger mK;

	protected ElGamalPrivateKey(BigInteger k, BigInteger g, BigInteger p) {
		super(g, p);
		mK = k;
	}

	protected BigInteger getK() {
		return mK;
	}

	public String toString() {
		return mK + ":" + getG() + ":" + getP();
	}
}
