package edu.udistrital.ing.sistemas.security;

import edu.udistrital.ing.sistemas.IGenerable;

/**
 * @author wbejarano
 */
public class Generador implements IGenerable {

	private IGenerable servicioGenerador;
	private static Generador instance;

	private Generador(IGenerable generador) throws Exception {
		servicioGenerador = generador;
	}

	public static Generador getInstance(IGenerable componenteGenerador) throws Exception {

		if (instance == null)
			instance = new Generador(componenteGenerador);

		return instance;
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