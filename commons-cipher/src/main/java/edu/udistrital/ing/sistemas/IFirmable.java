/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.udistrital.ing.sistemas;

import java.security.InvalidKeyException;
import java.security.SignatureException;

/**
 * 
 * @author wbejarano
 */
public interface IFirmable {

	public String getName();

	// Inicialización de variables
	public void init(String random_number);

	// Firma un mensaje
	public byte[] signKey(String str) throws InvalidKeyException, SignatureException;

	// Verifica la firma de un mensaje
	public boolean signVerify(byte[] signedb, String mensaje) throws InvalidKeyException, SignatureException;

}
