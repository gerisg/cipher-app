/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Manifest configuration
//Manifest-Version: 1.0
//Ant-Version: Apache Ant 1.9.1
//Created-By: 1.7.0_25-b30 (Oracle Corporation)
//Class-Path: lib/GeneradorInterfaceDefinition.jar
//X-COMMENT: Main-Class will be added automatically by build
/*
 Main-Class: edu.udistrital.ing.sistemas.componente.generador.generadorACWA
 Implementation-type: generator
 Commercial-Name: Generador ACWA
 slug: generador-acwa
 */

package edu.udistrital.ing.sistemas.generator.acwa;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.udistrital.ing.sistemas.components.Generable;

/**
 * @author wbejarano
 */
public class GeneradorACWA implements Generable {

	private static final String DIR = "data";
	private static final String FILE = "secuenciaNumerosAleatorios.txt";

	private int rows = 100;
	private int columns = 100;

	private String absoluteRoute;

	@Override
	public void generarSecuenciasAleatorias() {
		try {

			// Inicializar matriz
			WolframACWA wolframAc = new WolframACWA(this.rows, this.columns);
			wolframAc.evolucionar();
			absoluteRoute = wolframAc.imprimir(DIR, FILE);

		} catch (IOException ex) {
			Logger.getLogger(GeneradorACWA.class.getName()).log(Level.SEVERE, "Error imprimiendo secuencia.", ex);
		}
	}

	@Override
	public String getAbsoluteRoute() {
		return absoluteRoute;
	}

	@Override
	public String getNombre() {
		return "Generador ACWA";
	}
}
