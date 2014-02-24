/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.udistrital.ing.sistemas.components;

/**
 * @author wbejarano
 */
public interface Generable extends IComponent {

	public String getNombre();

	public void generarSecuenciasAleatorias(int rows, int columns);

	public String getAbsoluteRoute();
}
