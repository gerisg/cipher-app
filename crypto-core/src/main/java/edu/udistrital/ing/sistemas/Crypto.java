package edu.udistrital.ing.sistemas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import edu.udistrital.ing.sistemas.cipher.elgamal.ElgamalCipher;
import edu.udistrital.ing.sistemas.components.IComponent;
import edu.udistrital.ing.sistemas.controller.CipherController;
import edu.udistrital.ing.sistemas.controller.GeneratorController;
import edu.udistrital.ing.sistemas.controller.SignerController;
import edu.udistrital.ing.sistemas.generator.acwa.GeneradorACWA;
import edu.udistrital.ing.sistemas.gui.AnalyzerGUI;
import edu.udistrital.ing.sistemas.gui.CryptoGUI;
import edu.udistrital.ing.sistemas.gui.GeneratorGUI;
import edu.udistrital.ing.sistemas.signer.elgamal.ElgamalSigner;

public class Crypto {

	private final static String LOOKANDFEEL = ""; // System,Motif,GTK+,Metal

	private GeneratorController generatorController;
	private CipherController cipherController;
	private SignerController signerController;

	private Crypto() {
		Map<String, IComponent> components = registerComponents();

		generatorController = new GeneratorController(components);
		cipherController = new CipherController(components);
		signerController = new SignerController(components);
	}

	private Map<String, IComponent> registerComponents() {
		Map<String, IComponent> components = new HashMap<>();

		components.put("acwa-generator", new GeneradorACWA());
		components.put("elgamal-cipher", new ElgamalCipher());
		components.put("elgamal-signer", new ElgamalSigner());

		return components;
	}

	/**
	 * Crear GUI y mostrarla. Para seguridad de hilos, este método debería
	 * invocarse siempre desde el hilo de despachado de eventos.
	 */
	private static void createGUI() {

		// LookandFeel
		initLookAndFeel();
		JFrame.setDefaultLookAndFeelDecorated(true);

		// Frame
		JFrame frame = new JFrame("Cryptography");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		Crypto crypto = new Crypto();
		crypto.addComponentToPane(frame.getContentPane());

		// Show GUI
		frame.pack();
		frame.setMinimumSize(new Dimension(1200, 400));
		frame.setVisible(true);
	}

	private void addComponentToPane(Container pane) {
		final JTabbedPane tabbedPane = new JTabbedPane();

		GeneratorGUI generatorGUI = new GeneratorGUI(generatorController);
		tabbedPane.addTab("Generar", generatorGUI.createComponent());

		AnalyzerGUI analyzeGUI = new AnalyzerGUI(generatorController);
		tabbedPane.addTab("Analizar", analyzeGUI.createComponent());

		CryptoGUI cryptoGUI = new CryptoGUI(cipherController, signerController);
		tabbedPane.addTab("Cifrar", cryptoGUI.createComponent());

		pane.add(tabbedPane, BorderLayout.CENTER);
	}

	private static void initLookAndFeel() {
		String lookAndFeel = null;

		switch (LOOKANDFEEL) {
		case "Metal":
			lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
			break;
		case "System":
			lookAndFeel = UIManager.getSystemLookAndFeelClassName();
			break;
		case "Motif":
			lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
			break;
		case "GTK+":
			lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
			break;
		default:
			lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
			break;
		}

		try {
			UIManager.setLookAndFeel(lookAndFeel);

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			System.err.println("No pude obtener la apariencia especificada (" + lookAndFeel + "), por alguna razon.");
			System.err.println("Usando la apariencia predeterminada.");
		}
	}

	public GeneratorController getGeneratorController() {
		return generatorController;
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createGUI();
			}
		});
	}

}
