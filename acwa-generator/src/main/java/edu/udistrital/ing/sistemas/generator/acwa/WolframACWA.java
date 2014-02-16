/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.udistrital.ing.sistemas.generator.acwa;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * @author wbejarano
 */
public class WolframACWA {

	private final int[][] matriz;
	private final int filas;
	private final int columnas;

	public WolframACWA(int filas, int columnas) {
		this.filas = filas;
		this.columnas = columnas;
		this.matriz = new int[this.filas + 1][this.columnas + 1];

		populate();
	}

	private void populate() {
		Random randomNum = new Random();
		for (int x = 0; x <= this.filas; x++) {
			for (int y = 0; y <= this.columnas; y++) {
				matriz[x][y] = randomNum.nextInt(0x2);
			}
		}
	}

	public void evolucionar() {

		int neighborLeft;
		int neighborRight;

		// filas
		for (int x = 0; x <= this.filas; x++) {
			// columnas
			for (int y = 0; y <= this.columnas; y++) {

				// Se seleccionan los vecinos de la célula según posición
				if (y == 0) {
					// Si la celula está en la primera columna
					neighborLeft = x;
					neighborRight = y + 1;
				} else if (y == x) {
					// Si la célula es ella misma
					neighborLeft = y - 1;
					neighborRight = 1;
				} else if (y == this.columnas) {
					// Si la célula es la última columna
					neighborLeft = y - 1;
					neighborRight = 1;
				} else {
					// Si la célula es cualquier otra a las anteriores
					neighborLeft = y - 1;
					neighborRight = y + 1;
				}

				matriz[x][y] = aplicarRegla(matriz[x][neighborLeft], matriz[x][neighborRight], matriz[x][y]);
			}
		}
	}

	private int aplicarRegla(int iz, int de, int cell) {

		int pattern = 0;

		Random randomNum = new Random();
		switch (randomNum.nextInt(5)) {
		case 0:
			// regla WA
			pattern = (((iz ^ de) + (de * ~cell)) % 2);
			break;
		case 1:
			// regla 30
			pattern = ((1 + iz + cell + de + (cell * de)) % 2);
			break;
		case 2:
			// regla 45
			pattern = ((1 + iz + de + (cell * de)) % 2);
			break;
		case 3:
			// regla 75
			pattern = ((1 + iz + (cell * de)) % 2);
			break;
		case 4:
			// regla 90
			pattern = ((iz + de) % 2);
			break;
		}

		return pattern;
	}

	public String imprimir(String pathFile) throws IOException {

		String fila;
		File archivo = new File(pathFile);

		try (PrintWriter grabador = new PrintWriter(archivo)) {
			for (int x = 0; x < this.filas; x++) {
				fila = "";
				for (int y = 0; y < this.columnas; y++)
					fila += matriz[x][y];
				grabador.println(fila.replace("-", ""));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return archivo.getCanonicalPath();
	}
}