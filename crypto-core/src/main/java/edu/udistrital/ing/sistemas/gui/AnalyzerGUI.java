package edu.udistrital.ing.sistemas.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.udistrital.ing.sistemas.controller.GeneratorController;

public class AnalyzerGUI extends Frame implements ActionListener {

	private static final long serialVersionUID = -9016052817791749651L;

	private GeneratorController controller;

	public AnalyzerGUI(GeneratorController controller) {
		this.controller = controller;
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

		// Button
		JButton analyzeBtn = new JButton("Iniciar Tests");
		analyzeBtn.setVerticalTextPosition(AbstractButton.CENTER);
		analyzeBtn.setHorizontalTextPosition(AbstractButton.LEADING);
		analyzeBtn.addActionListener(this);
		pane.add(analyzeBtn, BorderLayout.PAGE_START);

		// Table
		String[] tests = new String[] { "ApproximateEntropy", "CumulativeSums", "Frequency", "LongestRun", "OverlappingTemplate", "RandomExcursionsVariant",
				"Runs", "Universal", "BlockFrequency", "FFT", "LinearComplexity", "NonOverlappingTemplate", "RandomExcursions", "Rank", "Serial" };
		DefaultTableModel testsModelTbl = new DefaultTableModel(new Object[10][15], tests);
		JTable testsTbl = new JTable(testsModelTbl);
		JScrollPane scrollPane = new JScrollPane(testsTbl);
		pane.add(scrollPane);

		return pane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controller.runTest();
		System.out.println("Tests ejecutados");
	}
}
