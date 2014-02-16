package edu.udistrital.ing.sistemas.security;

import java.security.InvalidKeyException;
import java.security.SignatureException;

import edu.udistrital.ing.sistemas.IFirmable;

/**
 * @author wbejarano
 */
public class Firmador implements IFirmable {

	private IFirmable servicioFirmador;
	private static Firmador instance;

	private Firmador(IFirmable componentFirmable, String seed) {
		servicioFirmador = componentFirmable;
		init(seed);
	}

	public static Firmador getInstance(IFirmable componentFirmable, String seed) {

		if (instance == null)
			instance = new Firmador(componentFirmable, seed);

		return instance;
	}

	@Override
	public void init(String randomNumber) {
		servicioFirmador.init(randomNumber);
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