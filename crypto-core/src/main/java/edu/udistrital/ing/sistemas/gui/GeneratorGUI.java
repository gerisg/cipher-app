package edu.udistrital.ing.sistemas.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.udistrital.ing.sistemas.Crypto;
import edu.udistrital.ing.sistemas.controller.GeneratorController;

public class GeneratorGUI extends Frame implements ActionListener {

	private static final long serialVersionUID = -4898957220768717522L;

	private GeneratorController controller;

	private JComboBox<String> generatorList;
	private DefaultTableModel resultsModelTbl;
	private JTable resultsTbl;

	public GeneratorGUI(GeneratorController generatorController) {
		controller = generatorController;
	}

	public JPanel createComponent() {

		// Create the "cards".
		JPanel pane = new JPanel() {
			private static final long serialVersionUID = 4750698681300011956L;

			public Dimension getPreferredSize() {
				Dimension size = super.getPreferredSize();
				size.width += 200;
				return size;
			}
		};
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());

		// Combobox
		String[] generators = controller.getList();
		generatorList = new JComboBox<String>(generators);
		generatorList.setSelectedIndex(-1);
		generatorList.addActionListener(this);
		p.add(generatorList, BorderLayout.PAGE_START);

		// Button
		JButton analyzeBtn = new JButton("Generar");
		analyzeBtn.setVerticalTextPosition(AbstractButton.CENTER);
		analyzeBtn.setHorizontalTextPosition(AbstractButton.LEADING);
		analyzeBtn.addActionListener(this);
		p.add(analyzeBtn, BorderLayout.PAGE_START);

		pane.add(p);

		// Table
		resultsModelTbl = new DefaultTableModel(new Object[100][2], new String[] { "Bit", "Entero" });
		resultsTbl = new JTable(resultsModelTbl);
		JScrollPane scrollPane = new JScrollPane(resultsTbl);
		pane.add(scrollPane);

		return pane;
	}

	public Icon getIcon() {
		return createImageIcon("img/crypto.png");
	}

	private static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Crypto.class.getResource(path);
		if (imgURL != null)
			return new ImageIcon(imgURL);
		return null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String generatorName = null;

		Object obj = e.getSource();
		if (obj instanceof JComboBox<?>) {
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>) obj;
			generatorName = (String) cb.getSelectedItem();
		} else {
			generatorName = (String) generatorList.getSelectedItem();
		}

		controller.generate(generatorName);

		loadResults();
	}

	private void loadResults() {

		Object[][] data = controller.loadData();

		for (int i = 0; i < data.length; i++) {
			resultsModelTbl.setValueAt(data[i][0], i, 0);
			resultsModelTbl.setValueAt(data[i][1], i, 1);
		}

		resultsTbl.setModel(resultsModelTbl);
	}
}