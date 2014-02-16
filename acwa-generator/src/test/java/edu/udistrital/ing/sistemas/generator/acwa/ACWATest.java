package edu.udistrital.ing.sistemas.generator.acwa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ACWATest {

	private GeneradorACWA generator;

	@Before
	public void init() {
		generator = new GeneradorACWA();
	}

	@Test
	public void generateTest() {
		generator.generarSecuenciasAleatorias();
		try (BufferedReader br = new BufferedReader(new FileReader(generator.getAbsoluteRoute()))) {
			String sCurrentLine;
			int lines = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				lines++;
			}
			Assert.assertEquals(100, lines);
		} catch (IOException e) {
			Assert.fail("No se pudo leer las cadenas generadas");
		}
	}

	@Test
	public void nameTest() {
		Assert.assertEquals("Generador ACWA", generator.getNombre());
	}
}
