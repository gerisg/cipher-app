package edu.udistrital.ing.sistemas;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.udistrital.ing.sistemas.cipher.elgamal.ElgamalCipher;
import edu.udistrital.ing.sistemas.components.IComponent;
import edu.udistrital.ing.sistemas.controller.ChainsController;
import edu.udistrital.ing.sistemas.controller.CipherController;
import edu.udistrital.ing.sistemas.controller.SignerController;
import edu.udistrital.ing.sistemas.generator.acwa.GeneradorACWA;
import edu.udistrital.ing.sistemas.gui.AnalyzerGUI;
import edu.udistrital.ing.sistemas.gui.CryptoGUI;
import edu.udistrital.ing.sistemas.gui.GeneratorGUI;
import edu.udistrital.ing.sistemas.signer.elgamal.ElgamalSigner;

/**
 * Clase principal de la aplicación "Crypto", esta se encarga de: <br />
 * - Instanciar los controladores <br />
 * - Mantener la lista de componentes <br />
 * - Inicializar la GUI <br />
 * 
 * @author ggallardo
 * 
 */
public class Crypto {

	private final static String LOOKANDFEEL = ""; // System,Motif,GTK+,Metal

	private ChainsController generatorController;
	private CipherController cipherController;
	private SignerController signerController;

	private Crypto() {
		Map<String, IComponent> components = registerComponents();

		generatorController = new ChainsController(components);
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
		frame.setVisible(true);
		frame.setExtendedState(frame.getExtendedState() | JFrame.NORMAL);
	}

	/**
	 * Se agrega cada una de las UIs al panel principal. Cada UI posee un
	 * tabpanel que la representa.
	 */
	private void addComponentToPane(Container pane) {
		final JTabbedPane tabbedPane = new JTabbedPane();

		final GeneratorGUI generatorGUI = new GeneratorGUI(generatorController);
		tabbedPane.addTab("Generar", generatorGUI.createComponent());
		tabbedPane.setEnabledAt(0, true);
		generatorGUI.setTabPanel(tabbedPane);

		final AnalyzerGUI analyzeGUI = new AnalyzerGUI(generatorController);
		tabbedPane.addTab("Analizar", analyzeGUI.createComponent());
		tabbedPane.setEnabledAt(1, false);
		analyzeGUI.setTabPanel(tabbedPane);

		final CryptoGUI cryptoGUI = new CryptoGUI(cipherController, signerController);
		tabbedPane.addTab("Cifrar", cryptoGUI.createComponent());
		tabbedPane.setEnabledAt(2, false);
		cryptoGUI.setTabPanel(tabbedPane);

		// Listener que inicia tareas a realizarse en los cambios de tabs
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				switch (sourceTabbedPane.getSelectedIndex()) {
				case 0:
					generatorGUI.actionPerformed(new ActionEvent(new Object(), 0, "change_tab"));
					break;
				case 1:
					analyzeGUI.actionPerformed(new ActionEvent(new Object(), 0, "change_tab"));
					break;
				case 2:
					cryptoGUI.actionPerformed(new ActionEvent(generatorController.getChain(), 0, "change_tab"));
					break;
				}
			}
		};
		tabbedPane.addChangeListener(changeListener);

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
			System.out.println("No pude obtener la apariencia especificada (" + lookAndFeel + "), por alguna razon.");
			System.out.println("Usando la apariencia predeterminada.");
		}
	}

	public ChainsController getGeneratorController() {
		return generatorController;
	}

	// Acá empieza todo...
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createGUI();
			}
		});
	}
}