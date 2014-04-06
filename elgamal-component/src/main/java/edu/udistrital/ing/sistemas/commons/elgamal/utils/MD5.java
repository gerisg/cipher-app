/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.udistrital.ing.sistemas.commons.elgamal.utils;

/*  
 * MD5 Hashing Class and Method  
 *  
 * @author Ge ZHANG (2937207)  
 * @login name: gz847  
 * @version 1.00 07/08/11
 * 
 * */
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MD5 {

	public final static String md5(String s) {

		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		String hashStr = new String();

		try {

			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			byte[] strTemp = s.getBytes();
			mdTemp.update(strTemp);

			byte[] md = mdTemp.digest();
			int j = md.length;

			char str[] = new char[j * 2];
			int k = 0;

			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}

			hashStr = new String(str);

		} catch (Exception e) {
			Logger.getLogger(MD5.class.getName()).log(Level.SEVERE, "", e);
		}

		return hashStr;
	}
}