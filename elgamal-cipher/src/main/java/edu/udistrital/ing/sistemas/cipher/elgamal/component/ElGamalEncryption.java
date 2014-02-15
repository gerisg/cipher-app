/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.udistrital.ing.sistemas.cipher.elgamal.component;

/*  
 * ElGamal Encryption Method  
 *  
 * @author Ge ZHANG (2937207)  
 * @login name: gz847  
 * @version 1.00 07/08/11*/
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.StringTokenizer;

public class ElGamalEncryption {

	protected ElGamalKey mKey;
	protected static BigInteger kOne = BigInteger.valueOf(1);

	public void engineInitEncrypt(PublicKey key) throws InvalidKeyException {
		if (!(key instanceof ElGamalPublicKey)) {
			throw new InvalidKeyException("Invalid ElGamalPublicKey.");
		}
		mKey = (ElGamalKey) key;
	}

	public void engineInitDecrypt(PrivateKey key) throws InvalidKeyException {
		if (!(key instanceof ElGamalPrivateKey)) {
			throw new InvalidKeyException("Invalid ElGamalPrivateKey.");
		}
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

	// version of encrypt which uses the default public key

	public String engineTextEncrypt(String message) {
		byte[] b = message.getBytes();
		BigInteger[][] cipher = new BigInteger[b.length][2];
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			// System.out.println(b[i]);
			BigInteger[] encryptedKey = engineEncrypt(new BigInteger("" + b[i] + ""));
			cipher[i][0] = encryptedKey[0];
			cipher[i][1] = encryptedKey[1];
		}
		for (int i = 0; i < b.length; i++) {
			sb.append("(");
			sb.append(cipher[i][0]);
			sb.append(",");
			sb.append(cipher[i][1]);
			sb.append(")");
		}
		return (new String(sb));

	}

	public String engineTextDecrypt(String c) {
		StringTokenizer st = new StringTokenizer(c, "(),");
		BigInteger[] temp = new BigInteger[2];
		StringBuffer plain = new StringBuffer();
		while (st.hasMoreTokens()) {
			temp[0] = new BigInteger(st.nextToken());
			temp[1] = new BigInteger(st.nextToken());
			plain.append((char) (engineDecrypt(temp)).intValue());
		}
		return new String(plain);
	}

	public BigInteger engineDecrypt(BigInteger[] result) {
		BigInteger k = ((ElGamalPrivateKey) mKey).getK();
		BigInteger p = mKey.getP();

		BigInteger a = result[0];
		BigInteger C = result[1];
		BigInteger temp = a.modPow(k, p).modInverse(p);

		return C.multiply(temp).mod(p);
	}
}
