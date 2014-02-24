package edu.udistrital.ing.sistemas.gui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorCellRender extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1890028162837754629L;

	private AnalyzerGUI analyzerGUI;
	private JLabel label;

	public ColorCellRender(AnalyzerGUI analyzerGUI) {
		this.analyzerGUI = analyzerGUI;
		this.label = new JLabel();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value == null)
			return component;

		if (value.equals("SUCCESS")) {
			label.setIcon(analyzerGUI.successIcon);
			analyzerGUI.results = label;
		}

		else if (value.equals("FAILURE")) {
			label.setIcon(analyzerGUI.failureIcon);
			analyzerGUI.results = label;
		}

		analyzerGUI.testsTbl.repaint();

		return label;
	}
}
