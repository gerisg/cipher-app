package edu.udistrital.ing.sistemas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import edu.udistrital.ing.sistemas.cipher.elgamal.ElgamalCipher;
import edu.udistrital.ing.sistemas.components.Cifrable;
import edu.udistrital.ing.sistemas.components.Firmable;
import edu.udistrital.ing.sistemas.components.Generable;
import edu.udistrital.ing.sistemas.components.IComponent;
import edu.udistrital.ing.sistemas.generator.acwa.GeneradorACWA;
import edu.udistrital.ing.sistemas.signer.elgamal.ElgamalSigner;

public class Crypto {

	Map<String, IComponent> components = new HashMap<>();

	public Crypto() {
		registerComponents();
	}

	private void registerComponents() {
		components.put("acwa-generator", new GeneradorACWA());
		components.put("elgamal-cipher", new ElgamalCipher());
		components.put("elgamal-signer", new ElgamalSigner());
	}

	void generate(String name) {

		Generable generator = (Generable) components.get(name);
		generator.generarSecuenciasAleatorias();

		try (BufferedReader br = new BufferedReader(new FileReader(generator.getAbsoluteRoute()))) {

			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null)
				System.out.println(sCurrentLine);

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	void cipher(String name, String seed) {

		Cifrable cipher = (Cifrable) components.get(name);
		cipher.init(seed);

		try {

			String textoCifrado = cipher.encryptText("Hola mundo");
			System.out.println("Texto cifrado: " + textoCifrado);

			String textoDesCifrado = cipher.decryptText(textoCifrado);
			System.out.println("Texto descifrado: " + textoDesCifrado);

		} catch (InvalidKeyException e) {
			System.out.println(e);
		}
	}

	void sign(String name, String seed) {

		Firmable signer = (Firmable) components.get(name);
		signer.init(seed);
		try {

			byte[] signed = signer.signKey("Hola mundo");
			System.out.println("Firma: " + Arrays.toString(signed));

			Boolean verified = signer.signVerify(signed, "Hola mundo");
			System.out.println("Verificado: " + verified);

		} catch (InvalidKeyException | SignatureException e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {

		Crypto app = new Crypto();

		app.generate("acwa-generator");
		app.cipher("elgamal-cipher", "54654153459789879874564654");
		app.sign("elgamal-signer", "54654153459789879874564654");
	}

}
