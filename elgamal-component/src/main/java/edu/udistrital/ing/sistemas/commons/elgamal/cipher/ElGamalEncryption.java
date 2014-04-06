package edu.udistrital.ing.sistemas.commons.elgamal.cipher;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.StringTokenizer;

import edu.udistrital.ing.sistemas.commons.elgamal.utils.ElGamalKey;
import edu.udistrital.ing.sistemas.commons.elgamal.utils.ElGamalPrivateKey;
import edu.udistrital.ing.sistemas.commons.elgamal.utils.ElGamalPublicKey;

/**
 * ElGamal Encryption Method
 * 
 * @author Ge ZHANG (2937207)
 * @login name: gz847
 * @version 1.00 07/08/11
 */
public class ElGamalEncryption {

	private ElGamalKey mKey;
	private BigInteger kOne = BigInteger.valueOf(1);

	public void engineInitEncrypt(PublicKey key) throws InvalidKeyException {
		if (!(key instanceof ElGamalPublicKey))
			throw new InvalidKeyException("Invalid ElGamalPublicKey.");
		mKey = (ElGamalKey) key;
	}

	public void engineInitDecrypt(PrivateKey key) throws InvalidKeyException {
		if (!(key instanceof ElGamalPrivateKey))
			throw new InvalidKeyException("Invalid ElGamalPrivateKey.");
		mKey = (ElGamalKey) key;
	}

	public BigInteger[] engineEncrypt(BigInteger M) {

		BigInteger y = ((ElGamalPublicKey) mKey).getY();
		BigInteger g = mKey.getG();
		BigInteger p = mKey.getP();

		BigInteger k;
		do {
			k = new BigInteger(p.bitLength() - 1, new SecureRandom());
		} while (k.gcd(p).equals(kOne) == false);

		BigInteger a = g.modPow(k, p);
		BigInteger temp = y.modPow(k, p);
		BigInteger C = (M.multiply(temp)).mod(p);

		BigInteger[] result = new BigInteger[2];
		result[0] = a;
		result[1] = C;

		return result;
	}

	/**
	 * Version of encrypt which uses the default public key
	 */
	public String engineTextEncrypt(String message) {

		StringBuilder sb = new StringBuilder();
		byte[] b = message.getBytes();
		BigInteger[][] cipher = new BigInteger[b.length][2];

		for (int i = 0; i < b.length; i++) {
			BigInteger[] encryptedKey = engineEncrypt(new BigInteger("" + b[i] + ""));
			cipher[i][0] = encryptedKey[0];
			cipher[i][1] = encryptedKey[1];
		}

		for (int i = 0; i < b.length; i++)
			sb.append("(").append(cipher[i][0]).append(",").append(cipher[i][1]).append(")");

		return sb.toString();
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

		BigInteger k = ((ElGamalPrivateKey) mKey).getK();
		BigInteger p = mKey.getP();
		BigInteger a = result[0];
		BigInteger c = result[1];
		BigInteger temp = a.modPow(k, p).modInverse(p);

		return c.multiply(temp).mod(p);
	}
}
