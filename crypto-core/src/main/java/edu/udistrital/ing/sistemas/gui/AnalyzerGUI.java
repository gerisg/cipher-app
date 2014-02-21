package edu.udistrital.ing.sistemas.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

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

	private static final String NUMBER_OF_BITS = "1000";
	private static final String NUMBER_OF_LINES = "100";

	public static final String[] TESTS_NAME = new String[] { //
	"ApproximateEntropy", //
			"CumulativeSums",//
			"Frequency", //
			"LongestRun", //
			"OverlappingTemplate", //
			"RandomExcursionsVariant", //
			"Runs", //
			"Universal", //
			"BlockFrequency", //
			"FFT", //
			"LinearComplexity", //
			"NonOverlappingTemplate", //
			"RandomExcursions", //
			"Rank", //
			"Serial" //
	};

	private GeneratorController controller;
	private DefaultTableModel testsModelTbl;
	private JTable testsTbl;

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
		testsModelTbl = new DefaultTableModel(new Object[100][15], TESTS_NAME);
		testsTbl = new JTable(testsModelTbl);
		JScrollPane scrollPane = new JScrollPane(testsTbl);
		pane.add(scrollPane);

		return pane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		controller.runTest(NUMBER_OF_BITS, NUMBER_OF_LINES);
		System.out.println("TESTS ENDED");

		Map<String, List<String>> results = controller.getResults();

		int column = 0;
		for (String name : TESTS_NAME) {

			List<String> values = results.get(name);
			System.out.println(name + " " + values.size());

			for (int row = 0; row < values.size(); row++)
				testsModelTbl.setValueAt(values.get(row), row, column);

			column++;
		}

		testsTbl.setModel(testsModelTbl);
	}
}
