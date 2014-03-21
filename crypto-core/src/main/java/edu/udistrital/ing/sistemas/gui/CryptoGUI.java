package edu.udistrital.ing.sistemas.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.udistrital.ing.sistemas.controller.ChainsController.Chain;
import edu.udistrital.ing.sistemas.controller.CipherController;
import edu.udistrital.ing.sistemas.controller.SignerController;

public class CryptoGUI extends Frame implements ActionListener {

	private static final long serialVersionUID = -8829904881281090682L;

	private CipherController cipherController;
	private SignerController signerController;

	private JComboBox<String> cipherList;
	private JLabel gField;
	private JLabel publicField;
	private JLabel privateField;
	private JLabel modulePField;
	private JLabel randomKField;
	private JButton cipherBtn;
	private JButton unCipherBtn;
	private JButton signBtn;
	private JButton verifyBtn;
	private JDialog dialog;

	private JTextArea msgArea;
	private JTextArea cipherArea;
	private JTextArea unCipherArea;

	private String userText;
	private String encryptedText;
	private String decryptedText;
	private byte[] signature;

	public CryptoGUI(CipherController cipherController, SignerController signerController) {
		this.cipherController = cipherController;
		this.signerController = signerController;
	}

	public Component createComponent() {

		// Options components
		cipherList = new JComboBox<String>(cipherController.getList());
		cipherList.setSelectedIndex(-1);
		cipherList.setActionCommand("select_cipher");
		cipherList.addActionListener(this);

		publicField = new JLabel();
		privateField = new JLabel();
		modulePField = new JLabel();
		randomKField = new JLabel();
		gField = new JLabel();
		gField.setPreferredSize(new Dimension(1000, 10));

		// Top bar pane
		JPanel selectCipherPane = new JPanel();
		selectCipherPane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		selectCipherPane.add(new JLabel("Cifrador: "));
		selectCipherPane.add(cipherList);

		JPanel pubKPane = new JPanel();
		pubKPane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		pubKPane.add(new JLabel("Public key: "));
		pubKPane.add(publicField);

		JPanel prvKPane = new JPanel();
		prvKPane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		prvKPane.add(new JLabel("Private key: "));
		prvKPane.add(privateField);

		JPanel pPane = new JPanel();
		pPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		pPane.add(new JLabel("P: "));
		pPane.add(modulePField);

		JPanel kPane = new JPanel();
		kPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		kPane.add(new JLabel("K: "));
		kPane.add(randomKField);

		JPanel keysPane = new JPanel();
		keysPane.setLayout(new BoxLayout(keysPane, BoxLayout.Y_AXIS));
		keysPane.add(pubKPane);
		keysPane.add(prvKPane);

		JPanel varsPane = new JPanel();
		varsPane.setLayout(new BoxLayout(varsPane, BoxLayout.Y_AXIS));
		varsPane.add(pPane);
		varsPane.add(kPane);

		JPanel generatePane = new JPanel();
		generatePane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		generatePane.add(new JLabel("G: "));
		generatePane.add(gField);

		JPanel topBarPane = new JPanel();
		topBarPane.setLayout(new BorderLayout(5, 5));
		topBarPane.add(selectCipherPane, BorderLayout.NORTH);
		topBarPane.add(keysPane, BorderLayout.EAST);
		topBarPane.add(varsPane, BorderLayout.WEST);
		topBarPane.add(generatePane, BorderLayout.SOUTH);

		// Cipher bar components
		cipherBtn = new JButton("Cifrar");
		cipherBtn.setActionCommand("encrypt");
		cipherBtn.addActionListener(this);
		cipherBtn.setEnabled(false);

		unCipherBtn = new JButton("Descifrar");
		unCipherBtn.setActionCommand("decrypt");
		unCipherBtn.addActionListener(this);
		unCipherBtn.setEnabled(false);

		// Cipher content components
		msgArea = new JTextArea(10, 10);
		msgArea.setEditable(true);
		msgArea.setLineWrap(true);

		JScrollPane msgAreaScrollPane = new JScrollPane(msgArea);
		msgAreaScrollPane.setMaximumSize(new Dimension(800, 800));

		cipherArea = new JTextArea(10, 10);
		cipherArea.setEditable(false);
		cipherArea.setBackground(new Color(229, 255, 204));
		cipherArea.setLineWrap(true);

		JScrollPane cipherAreaScrollPane = new JScrollPane(cipherArea);
		cipherAreaScrollPane.setMaximumSize(new Dimension(800, 800));

		unCipherArea = new JTextArea(10, 10);
		unCipherArea.setEditable(false);
		unCipherArea.setBackground(new Color(224, 255, 255));
		unCipherArea.setLineWrap(true);

		JScrollPane unCipherAreaScrollPane = new JScrollPane(unCipherArea);
		unCipherAreaScrollPane.setMaximumSize(new Dimension(800, 800));

		// Msg pane
		JPanel userMsgPane = new JPanel();
		userMsgPane.setLayout(new BoxLayout(userMsgPane, BoxLayout.Y_AXIS));
		userMsgPane.add(new JLabel("Mensaje claro"));
		userMsgPane.add(msgAreaScrollPane);

		JPanel cipherMsgPane = new JPanel();
		cipherMsgPane.setLayout(new BoxLayout(cipherMsgPane, BoxLayout.Y_AXIS));
		cipherMsgPane.add(new JLabel("Mensaje cifrado"));
		cipherMsgPane.add(cipherAreaScrollPane);
		cipherMsgPane.add(cipherBtn);

		JPanel unCipherMsgPane = new JPanel();
		unCipherMsgPane.setLayout(new BoxLayout(unCipherMsgPane, BoxLayout.Y_AXIS));
		unCipherMsgPane.add(new JLabel("Mensaje descifrado"));
		unCipherMsgPane.add(unCipherAreaScrollPane);
		unCipherMsgPane.add(unCipherBtn);

		// Cipher pane
		JPanel cipherPane = new JPanel();
		cipherPane.setLayout(new GridLayout(0, 3, 20, 20));
		cipherPane.add(userMsgPane);
		cipherPane.add(cipherMsgPane);
		cipherPane.add(unCipherMsgPane);

		// Bottom components
		JComboBox<String> signerList = new JComboBox<String>(signerController.getList());
		signerList.setSelectedIndex(-1);
		signerList.setActionCommand("select_signer");
		signerList.addActionListener(this);

		signBtn = new JButton("Firmar");
		signBtn.setActionCommand("sign");
		signBtn.addActionListener(this);
		signBtn.setEnabled(false);

		verifyBtn = new JButton("Verificar");
		verifyBtn.setActionCommand("verify");
		verifyBtn.addActionListener(this);
		verifyBtn.setEnabled(false);

		// Bottom pane
		JPanel bottomBarPane = new JPanel();
		bottomBarPane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		bottomBarPane.add(new JLabel("Firmador: "));
		bottomBarPane.add(signerList);
		bottomBarPane.add(signBtn);
		bottomBarPane.add(verifyBtn);

		// Main pane
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout(5, 5));
		pane.add(topBarPane, BorderLayout.NORTH);
		pane.add(new JPanel(), BorderLayout.EAST);
		pane.add(cipherPane, BorderLayout.CENTER);
		pane.add(bottomBarPane, BorderLayout.SOUTH);

		return pane;
	}

	public void setTabPanel(JTabbedPane tabbedPane) {
		// Por ahora no se necesita utilizar el TabPane general
	}

	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "change_tab":

			cleanGUI();

			// Guarda la cadena seleccionada
			Chain chainSelected = (Chain) e.getSource();
			gField.setText(chainSelected.getIndex() + " - " + chainSelected.getText());
			gField.setVisible(true);

			break;

		case "encrypt":
			try {
				// Leer texto ingresado
				userText = msgArea.getText();

				// Cifrar
				encryptedText = cipherController.encryptText(userText);

				// Mostrar texto cifrado
				cipherArea.setText(encryptedText);

				// Habilitar descifrado
				unCipherBtn.setEnabled(true);

			} catch (InvalidKeyException e1) {
				System.out.println(e1.getMessage());
			}

			break;

		case "decrypt":
			try {
				// Descifrar
				decryptedText = cipherController.decryptText(encryptedText);

				// Mostrar texto descifrado
				unCipherArea.setText(decryptedText);

			} catch (InvalidKeyException e1) {
				System.out.println(e1.getMessage());
			}

			break;

		case "sign":
			try {
				// Firmar
				signature = signerController.sign(decryptedText);

				// Habilitar verificación
				verifyBtn.setEnabled(true);

				dialog = new JDialog();
				dialog.setTitle("Firma generada");
				dialog.add(new JTextField("Firma: " + Arrays.toString(signature)));
				dialog.setSize(500, 100);
				dialog.setLocationRelativeTo(cipherArea); // Middle screen
				dialog.setVisible(true);

			} catch (InvalidKeyException | SignatureException e1) {
				System.out.println(e1.getMessage());
			}

			break;

		case "verify":
			try {

				// Verificar
				String hash = signerController.verify(signature, decryptedText);

				dialog = new JDialog();
				dialog.setTitle("Firma verificada");
				dialog.add(new JTextField(new String("Hash: " + hash)));
				dialog.setSize(500, 100);
				dialog.setLocationRelativeTo(cipherArea); // Middle screen
				dialog.setVisible(true);

			} catch (InvalidKeyException | SignatureException e1) {
				System.out.println(e1.getMessage());
			}

			break;

		case "select_cipher":

			Object cipher = e.getSource();
			if (cipher instanceof JComboBox<?>) {

				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>) cipher;
				cipherController.select((String) cb.getSelectedItem());

				publicField.setText(cipherController.getPublicKey());
				privateField.setText(cipherController.getPrivateKey());
				modulePField.setText(cipherController.getModuleP());
				randomKField.setText(cipherController.getRandomK());

				cipherBtn.setEnabled(true);
			}

			break;

		case "select_signer":

			Object signer = e.getSource();
			if (signer instanceof JComboBox<?>) {

				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>) signer;
				signerController.select((String) cb.getSelectedItem());

				signBtn.setEnabled(true);
			}

			break;
		}
	}

	private void cleanGUI() {

		cipherList.setSelectedIndex(0);

		// Text area
		String empty = new String();
		msgArea.setText(empty);
		cipherArea.setText(empty);
		unCipherArea.setText(empty);
	}
}