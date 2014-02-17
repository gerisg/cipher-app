package edu.udistrital.ing.sistemas.gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AnalyzeGUI {

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

		// Table
		String[] tests = new String[] { "ApproximateEntropy", "CumulativeSums", "Frequency", "LongestRun", "OverlappingTemplate", "RandomExcursionsVariant",
				"Runs", "Universal", "BlockFrequency", "FFT", "LinearComplexity", "NonOverlappingTemplate", "RandomExcursions", "Rank", "Serial" };
		DefaultTableModel testsModelTbl = new DefaultTableModel(new Object[10][15], tests);
		JTable testsTbl = new JTable(testsModelTbl);
		JScrollPane scrollPane = new JScrollPane(testsTbl);
		pane.add(scrollPane);

		return pane;
	}
}
