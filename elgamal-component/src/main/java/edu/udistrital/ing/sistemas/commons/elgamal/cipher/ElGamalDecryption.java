package edu.udistrital.ing.sistemas.commons.elgamal.cipher;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.util.StringTokenizer;

import edu.udistrital.ing.sistemas.commons.elgamal.ElGamalPrivateKey;

/**
 * ElGamal Encryption Method
 * 
 * @author Ge ZHANG (2937207)
 * @login name: gz847
 * @version 1.00 07/08/11
 */
public class ElGamalDecryption {

	private ElGamalPrivateKey mKey;

	public void engineInitDecrypt(PrivateKey key) throws InvalidKeyException {

		if (!(key instanceof ElGamalPrivateKey))
			throw new InvalidKeyException("Invalid ElGamalPrivateKey.");

		mKey = (ElGamalPrivateKey) key;
	}

	public String engineTextDecrypt(String c) {

		StringBuilder plain = new StringBuilder();
		BigInteger[] temp = new BigInteger[2];

		StringTokenizer st = new StringTokenizer(c, "(),");
		while (st.hasMoreTokens()) {
			temp[0] = new BigInteger(st.nextToken());
			temp[1] = new BigInteger(st.nextToken());
			plain.append((char) (engineDecrypt(temp)).intValue());
		}

		return plain.toString();
	}

	public BigInteger engineDecrypt(BigInteger[] result) {

		BigInteger k = mKey.getK();
		BigInteger p = mKey.getP();
		BigInteger a = result[0];
		BigInteger c = result[1];
		BigInteger temp = a.modPow(k, p).modInverse(p);

		return c.multiply(temp).mod(p);
	}
}
