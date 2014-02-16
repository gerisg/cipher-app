package edu.udistrital.ing.sistemas.cipher.elgamal.tools;

import java.io.IOException;
import java.math.BigInteger;

/**
 * La clase utils se debe pasar al generador de números aleatorios
 */
public class Utils {

	/**
	 * Runs another process according to the given command for example -
	 * command="java Server.jar"
	 * 
	 * @param command
	 *            - the command which runs the wanted process
	 * @return the created process, of null upon exception
	 */
	public static Process runProcess(String command) {
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return p;
	}

	public static boolean isPrime(BigInteger n) {
		return n.isProbablePrime(100);
	}

}