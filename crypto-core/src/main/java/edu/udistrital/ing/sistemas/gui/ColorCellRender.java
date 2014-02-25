package edu.udistrital.ing.sistemas.gui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorCellRender extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1890028162837754629L;

	private AnalyzerGUI analyzerGUI;
	private JLabel label;

	private ImageIcon successIcon;
	private ImageIcon failureIcon;

	public ColorCellRender(AnalyzerGUI analyzerGUI) {

		this.analyzerGUI = analyzerGUI;

		this.label = new JLabel();
		this.successIcon = new ImageIcon(ClassLoader.getSystemResource("img/success.png"));
		this.failureIcon = new ImageIcon(ClassLoader.getSystemResource("img/failure.png"));
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value == null)
			return component;

		if (value.equals("SUCCESS")) {
			label.setIcon(successIcon);
			analyzerGUI.results = label;
		}

		else if (value.equals("FAILURE")) {
			label.setIcon(failureIcon);
			analyzerGUI.results = label;
		}

		analyzerGUI.testsTbl.repaint();

		return label;
	}
}
