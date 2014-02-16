/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.udistrital.ing.sistemas;

import java.math.BigInteger;
import java.security.InvalidKeyException;

/**
 * @author wbejarano
 */
public interface ICifrable {

	// Devuelve el nombre del cifrador
	public String getName();

	// Inicialización de variables
	public void init(String random_number);

	// Cifra un número grande (Bginteger)
	public BigInteger[] encryptKey(String key) throws InvalidKeyException;

	// Cifra un texto plano
	public String encryptText(String message) throws InvalidKeyException;

	// Decifra un mensaje en números grandes
	public BigInteger decryptKey(BigInteger[] encryptedmsg) throws InvalidKeyException;

	// Decifra un texto cifrado
	public String decryptText(String encryptedmsg) throws InvalidKeyException;

}
