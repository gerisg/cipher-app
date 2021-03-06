/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.udistrital.ing.sistemas.components;

import java.security.InvalidKeyException;
import java.security.SignatureException;

/**
 * @author wbejarano
 */
public interface Firmable extends IComponent {

	// Inicialización de variables
	public void init(String randomNumber);

	// Firma un mensaje
	public byte[] signKey(String str) throws InvalidKeyException, SignatureException;

	// Verifica la firma de un mensaje retornando el HASH
	public String signVerify(byte[] signedb, String mensaje) throws InvalidKeyException, SignatureException;
	
}
