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

import edu.udistrital.ing.sistemas.controller.CipherController;
import edu.udistrital.ing.sistemas.controller.SignerController;

public class CryptoGUI extends Frame implements ActionListener {

	private static final long serialVersionUID = -8829904881281090682L;

	private CipherController cipherController;
	private SignerController signerController;

	private JTextField cField;
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
		JComboBox<String> cipherList = new JComboBox<String>(cipherController.getList());
		cipherList.setSelectedIndex(-1);
		cipherList.setActionCommand("select_cipher");
		cipherList.addActionListener(this);

		JTextField pField = new JTextField();
		pField.addActionListener(this);
		pField.setText("     ");
		pField.setEditable(false);

		JTextField qField = new JTextField();
		qField.addActionListener(this);
		qField.setText("     ");
		qField.setEditable(false);

		cField = new JTextField();
		cField.addActionListener(this);
		cField.setEditable(false);

		// Options pane
		JPanel optionsPane = new JPanel();
		optionsPane.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
		optionsPane.add(new JLabel("Cifrador: "));
		optionsPane.add(cipherList);
		optionsPane.add(new JLabel("P: "));
		optionsPane.add(pField);
		optionsPane.add(new JLabel("Q: "));
		optionsPane.add(qField);
		optionsPane.add(new JLabel("C: "));
		optionsPane.add(cField);

		// Top bar pane
		JPanel topBarPane = new JPanel();
		topBarPane.setLayout(new BoxLayout(topBarPane, BoxLayout.Y_AXIS));
		topBarPane.add(optionsPane);

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
		bottomBarPane.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
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

			// Guarda la cadena seleccionada
			String chainSelected = (String) e.getSource();
			cField.setText(chainSelected);

			System.out.println(chainSelected);

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
				dialog.setTitle("Signature");
				dialog.add(new JLabel("Firma del mensaje: "));
				dialog.add(new JTextField(new String(signature)));
				dialog.setSize(200, 200);
				dialog.setLocationRelativeTo(verifyBtn);
				dialog.setVisible(true);

			} catch (InvalidKeyException | SignatureException e1) {
				System.out.println(e1.getMessage());
			}

			break;

		case "verify":
			try {

				// TODO show verify!
				signerController.verify(signature, decryptedText);

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
}