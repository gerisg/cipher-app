/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.udistrital.ing.sistemas.security;

import edu.udistrital.ing.sistemas.IGenerable;

/**
 * 
 * @author wbejarano
 */
public class Generador implements IGenerable {

	private IGenerable servicioGenerador;
	private static Generador instance;

	public static Generador getInstance(IGenerable componenteGenerador) throws Exception {

		if (instance == null)
			instance = new Generador(componenteGenerador);
		else
			instance.reloadGenerador(componenteGenerador);

		return instance;
	}

	private void reloadGenerador(IGenerable componenteGenerador) {
		servicioGenerador = componenteGenerador;
	}

	private Generador(IGenerable Generador) throws Exception {
		servicioGenerador = Generador;
	}

	@Override
	public String getNombre() {
		return servicioGenerador.getNombre();
	}

	@Override
	public void generarSecuenciasAleatorias() {
		servicioGenerador.generarSecuenciasAleatorias();
	}

	@Override
	public String getAbsoluteRoute() {
		return servicioGenerador.getAbsoluteRoute();
	}

}
