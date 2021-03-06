/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.udistrital.ing.sistemas.components;

import java.math.BigInteger;
import java.security.InvalidKeyException;

/**
 * @author wbejarano
 */
public interface Cifrable extends IComponent {

	// Inicialización de variables
	public void init(String randomNumber);

	// Cifra un número grande (Bginteger)
	public BigInteger[] encryptKey(String key) throws InvalidKeyException;

	// Cifra un texto plano
	public String encryptText(String message) throws InvalidKeyException;

	// Decifra un mensaje en números grandes
	public BigInteger decryptKey(BigInteger[] encryptedMsg) throws InvalidKeyException;

	// Decifra un texto cifrado
	public String decryptText(String encryptedMsg) throws InvalidKeyException;

	// Clave privada en texto
	public String getPrivateKey();

	// Clave publica en texto
	public String getPublicKey();

	// Obtener modulo P
	public BigInteger getModuleP();

	// Obtener random K
	public BigInteger getRandomK();

}
