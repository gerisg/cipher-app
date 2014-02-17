package edu.udistrital.ing.sistemas.gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import edu.udistrital.ing.sistemas.controller.CipherController;
import edu.udistrital.ing.sistemas.controller.SignerController;

public class CryptoGUI {

	private CipherController cipherController;
	private SignerController signerController;

	public CryptoGUI(CipherController cipherController, SignerController signerController) {
		this.cipherController = cipherController;
		this.signerController = signerController;
	}

	public Component createComponent() {

		JPanel pane = new JPanel() {

			private static final long serialVersionUID = 1L;

			public Dimension getPreferredSize() {
				Dimension size = super.getPreferredSize();
				size.width += 200;
				return size;
			}
		};
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		return pane;
	}

}
