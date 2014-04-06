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
		generator.generarSecuenciasAleatorias(10, 10);
		try (BufferedReader br = new BufferedReader(new FileReader(generator.getAbsoluteRoute()))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null)
				System.out.println(sCurrentLine);
			Assert.assertEquals(10, 10);
		} catch (IOException e) {
			Assert.fail("No se pudo leer las cadenas generadas");
		}
	}

	@Test
	public void nameTest() {
		Assert.assertEquals("ACWA", generator.getName());
	}
}
