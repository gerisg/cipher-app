/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.udistrital.ing.sistemas.security;

import java.security.InvalidKeyException;
import java.security.SignatureException;

import edu.udistrital.ing.sistemas.IFirmable;

/**
 * 
 * @author wbejarano
 */
public class Firmador implements IFirmable {

	private static Firmador instance;
	private IFirmable servicioFirmador;

	private Firmador(IFirmable componentFirmable, String seed) {
		servicioFirmador = componentFirmable;
		servicioFirmador.init(seed);
	}

	public static Firmador getInstance(IFirmable componentFirmable, String seed) {
		if (instance == null) {
			instance = new Firmador(componentFirmable, seed);
		} else {
			instance.reloadFirmador(componentFirmable, seed);
		}
		return instance;
	}

	private void reloadFirmador(IFirmable componentFirmable, String seed) {
		servicioFirmador = componentFirmable;
		servicioFirmador.init(seed);
	}

	@Override
	public void init(String random_number) {
		servicioFirmador.init(random_number);
	}

	@Override
	public byte[] signKey(String str) throws InvalidKeyException, SignatureException {
		return servicioFirmador.signKey(str);
	}

	@Override
	public boolean signVerify(byte[] signedb, String mensaje) throws InvalidKeyException, SignatureException {
		return servicioFirmador.signVerify(signedb, mensaje);
	}

	@Override
	public String getName() {
		return servicioFirmador.getName();
	}
}
