package edu.udistrital.ing.sistemas.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import edu.udistrital.ing.sistemas.controller.ChainsController;
import edu.udistrital.ing.sistemas.sts.STSTest;

public class AnalyzerGUI extends Frame implements ActionListener {

	private static final long serialVersionUID = -9016052817791749651L;

	private static final String[] COLUMNS = new String[] { "Id", //
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
			"Serial", //
			"" };

	private static final Double ALPHA = new Double(0.01);

	private DefaultTableModel testsModelTbl;
	protected JTable testsTbl;
	private JTabbedPane tabPanel;
	private TableCellRenderer color;
	protected ImageIcon successIcon;
	protected ImageIcon failureIcon;
	protected JLabel results;
	protected JLabel selectedTitle;
	protected JLabel selectedChain;
	private JButton okBtn;

	private ChainsController controller;

	public AnalyzerGUI(ChainsController controller) {
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

		selectedTitle = new JLabel("Cadena seleccionada: ");
		pane.add(selectedTitle);

		selectedChain = new JLabel();
		pane.add(selectedChain);

		okBtn = new JButton("OK");
		okBtn.setActionCommand("use_chain");
		okBtn.addActionListener(this);
		okBtn.setEnabled(false);
		pane.add(okBtn);

		// Table
		// Colums: id, tests (15), results, select
		testsModelTbl = new DefaultTableModel(new Object[1][18], COLUMNS);
		testsTbl = new JTable(testsModelTbl);
		JScrollPane scrollPane = new JScrollPane(testsTbl);
		pane.add(scrollPane);

		color = new ColorCellRender(this);

		successIcon = new ImageIcon(ClassLoader.getSystemResource("img/success.png"));
		failureIcon = new ImageIcon(ClassLoader.getSystemResource("img/failure.png"));

		results = new JLabel();

		return pane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "change_tab":

			Map<String, List<String>> results = controller.getResults();

			// Matriz
			// id | test1 .. test15 | results | select
			testsModelTbl = new DefaultTableModel(new Object[GeneratorGUI.rows + 1][COLUMNS.length], COLUMNS);

			// Complete Tests Columns
			for (int column = 1; column < COLUMNS.length - 2; column++) {
				String name = COLUMNS[column];
				List<String> list = results.get(name);
				if (list.isEmpty()) // Last row
					testsModelTbl.setValueAt(STSTest.parsers.get(name).getMessage(), GeneratorGUI.rows, column);
				else
					for (int row = 0; row < GeneratorGUI.rows; row++)
						testsModelTbl.setValueAt(list.get(row), row, column);
			}

			for (int row = 0; row < GeneratorGUI.rows; row++) {

				// Complete ID Column
				testsModelTbl.setValueAt(row + 1, row, 0);

				// Complete Results Column
				@SuppressWarnings("unchecked")
				String result = calculateRowResult((Vector<String>) testsModelTbl.getDataVector().elementAt(row));
				testsModelTbl.setValueAt(result, row, 16);
			}

			testsTbl.setModel(testsModelTbl);
			testsTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			testsTbl.setPreferredScrollableViewportSize(new Dimension(testsTbl.getColumnCount() * 100, testsTbl.getRowHeight() * 16));

			testsTbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					int index = e.getFirstIndex();
					controller.setChain(index);
					selectedChain.setText(controller.getChain());
					okBtn.setEnabled(true);
				}
			});

			testsTbl.getColumnModel().getColumn(16).setCellRenderer(color);

			testsModelTbl.fireTableDataChanged();

			break;

		case "use_chain":
			goNextTab();
			break;
		}
	}

	private String calculateRowResult(Vector<String> rowVector) {
		// Sólo las columnas que tienen tests
		for (int i = 1; i < rowVector.size() - 2; i++)
			try {

				if (Double.valueOf(rowVector.elementAt(i)) < ALPHA)
					return "FAILURE";

			} catch (Exception e) {
				// Son tests que no se generaron
				System.out.println(e.getMessage());
			}

		return "SUCCESS";
	}

	private void goNextTab() {
		int indexOfTab = this.tabPanel.indexOfTab("Analizar");

		this.tabPanel.setEnabledAt(indexOfTab + 1, true);
		this.tabPanel.setSelectedIndex(indexOfTab + 1);
	}

	public void setTabPanel(JTabbedPane tabbedPane) {
		this.tabPanel = tabbedPane;
	}
}
