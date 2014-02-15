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

import edu.udistrital.ing.sistemas.IGenerable;

/**
 * 
 * @author wbejarano
 */
public class generadorACWA implements IGenerable {

	private int rows = 100;
	private int columns = 100;
	private static final String path_to_file = "data/";
	private static final String nombre_archivo = "secuenciaNumerosAleatorios.txt";
	private String absolute_route;

	@Override
	public void generarSecuenciasAleatorias() {
		// inicializar matriz
		WolframACWA wolframAc = new WolframACWA(this.rows, this.columns);
		wolframAc.evolucionar();
		try {
			absolute_route = wolframAc.imprimir(path_to_file + nombre_archivo);
		} catch (IOException ex) {
			Logger.getLogger(generadorACWA.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public String getAbsoluteRoute() {
		return absolute_route;
	}

	@Override
	public String getNombre() {
		return "Generardor ACWA";
	}
}
