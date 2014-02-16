package edu.udistrital.ing.sistemas;

import java.util.Arrays;

import edu.udistrital.ing.sistemas.security.Cifrador;
import edu.udistrital.ing.sistemas.security.Firmador;
import edu.udistrital.ing.sistemas.security.Generador;

/**
 * @author wbejarano
 */
public class CipherIDE {

	public static void main(String[] args) throws Exception {

		ComponentLoader cargadorComponentes = new ComponentLoader();

		// Generador
		Generador generador = Generador.getInstance(cargadorComponentes.getObjectGenerador("generador-acwa"));
		generador.generarSecuenciasAleatorias();

		// Cifrador
		Cifrador cifrador = Cifrador.getInstance((ICifrable) cargadorComponentes.getObjectCifrador("cifrador-elgamal"), "54654153459789879874564654");
		String textoCifrado = cifrador.encryptText("Hola mundo");
		System.out.println("Texto cifrado: " + textoCifrado);
		String textoDesCifrado = cifrador.decryptText(textoCifrado);
		System.out.println("Texto Descifrado: " + textoDesCifrado);

		// Firmador
		Firmador firmador = Firmador.getInstance((IFirmable) cargadorComponentes.getObjectFirmador("firmador-elgamal"), "87490709874398564389659827436598743");
		byte[] firma = firmador.signKey("Hola mundo");
		System.out.println("Firma: " + Arrays.toString(firma));
		Boolean verificado = firmador.signVerify(firma, "Hola mundo");
		System.out.println("Verificado: " + verificado);

	}

}
