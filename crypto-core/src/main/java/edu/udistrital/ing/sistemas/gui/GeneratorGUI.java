package edu.udistrital.ing.sistemas.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import edu.udistrital.ing.sistemas.controller.ChainsController;
import edu.udistrital.ing.sistemas.utils.ChainsUtils;

public class GeneratorGUI extends Frame implements ActionListener {

	private static final long serialVersionUID = -4898957220768717522L;

	public static int rows = 100;
	public static int columns = 1000;
	public static Vector<String> columnNames;

	private JTabbedPane tabPanel;
	private JTextField rowsTxt;
	private JTextField columnsTxt;
	private JButton generateBtn;
	private JButton analyzeBtn;
	private JComboBox<String> generatorList;
	private NumberFormatter formatter;
	private JTable resultsTbl;
	private DefaultTableModel resultsModelTbl;

	private String generatorName;
	private ChainsController controller;

	public GeneratorGUI(ChainsController generatorController) {
		controller = generatorController;
	}

	public JPanel createComponent() {

		// Formatter
		formatter = new NumberFormatter(NumberFormat.getInstance());
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(1000000);

		// Dimension
		Dimension dimensionTxt = new Dimension(50, 20);

		// Column names
		columnNames = new Vector<String>(3);
		columnNames.add("ID");
		columnNames.add("BIT");
		columnNames.add("ENTERO");

		// Top bar components
		rowsTxt = new JFormattedTextField(formatter);
		rowsTxt.setPreferredSize(dimensionTxt);
		rowsTxt.setText("100");

		columnsTxt = new JFormattedTextField(formatter);
		columnsTxt.setPreferredSize(dimensionTxt);
		columnsTxt.setText("1000");

		generatorList = new JComboBox<String>(controller.getList());
		generatorList.setSelectedIndex(-1);
		generatorList.setActionCommand("select_generator");
		generatorList.addActionListener(this);

		generateBtn = new JButton("Generar");
		generateBtn.setVerticalTextPosition(AbstractButton.CENTER);
		generateBtn.setHorizontalTextPosition(AbstractButton.LEADING);
		generateBtn.setActionCommand("generate");
		generateBtn.addActionListener(this);
		generateBtn.setEnabled(false);

		analyzeBtn = new JButton("Analizar");
		analyzeBtn.setVerticalTextPosition(AbstractButton.CENTER);
		analyzeBtn.setHorizontalTextPosition(AbstractButton.LEADING);
		analyzeBtn.setActionCommand("analyze");
		analyzeBtn.addActionListener(this);
		analyzeBtn.setEnabled(false);

		// Top bar pane
		JPanel topBarPane = new JPanel();
		topBarPane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		topBarPane.add(new JLabel("Rows: "));
		topBarPane.add(rowsTxt);
		topBarPane.add(new JLabel("Columns: "));
		topBarPane.add(columnsTxt);
		topBarPane.add(generatorList);
		topBarPane.add(generateBtn);
		topBarPane.add(analyzeBtn);

		// Generated components
		resultsModelTbl = new DefaultTableModel(columnNames, 0);
		resultsTbl = new JTable(resultsModelTbl);
		JScrollPane scrollPane = new JScrollPane(resultsTbl);
		scrollPane.setPreferredSize(new Dimension(1000, 750));

		// Generated pane
		JPanel generatedChainsPane = new JPanel();
		generatedChainsPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		generatedChainsPane.add(scrollPane);

		// Main pane
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout(5, 5));
		pane.add(topBarPane, BorderLayout.NORTH);
		pane.add(generatedChainsPane, BorderLayout.CENTER);

		return pane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "change_tab":
			break;

		case "generate":
			try {
				// Load rows, columns values
				rows = (Integer) formatter.stringToValue(rowsTxt.getText());
				columns = (Integer) formatter.stringToValue(columnsTxt.getText());

				// Generate chains
				controller.generate(generatorName, rows, columns);

				// Load results
				showResults();

				// Enable analyze
				analyzeBtn.setEnabled(true);

			} catch (ParseException e1) {
				System.out.println("No se pudieron parsear los datos del generador.");
			}

			break;

		case "analyze":

			// Run NIST tests
			controller.runTest(columns, rows);

			// Go to next tab
			goNextTab();

			break;

		case "select_generator":

			Object generator = e.getSource();
			if (generator instanceof JComboBox<?>) {
				@SuppressWarnings("unchecked")
				JComboBox<String> cb = (JComboBox<String>) generator;

				// Selected generator
				generatorName = (String) cb.getSelectedItem();

				// Enable generator
				generateBtn.setEnabled(true);
			}

			break;
		}
	}

	/**
	 * El generador de cadenas aleatorias crea un archivo en el directorio
	 * temporales. Este método abre ese archivo y lo parsea para armar una tabla
	 * en la GUI
	 */
	private void showResults() {

		// Rebuild table
		Vector<Vector<String>> data = ChainsUtils.buildVectorData(controller.getChains());

		// Data
		resultsModelTbl.setDataVector(data, columnNames);
		resultsTbl.setModel(resultsModelTbl);

		// Size
		resultsTbl.getColumnModel().getColumn(0).setPreferredWidth(50);
		resultsTbl.getColumnModel().getColumn(0).setMinWidth(20);
		resultsTbl.getColumnModel().getColumn(1).setPreferredWidth(1000);
		resultsTbl.getColumnModel().getColumn(1).setMinWidth(800);
		resultsTbl.getColumnModel().getColumn(2).setPreferredWidth(500);
		resultsTbl.getColumnModel().getColumn(2).setMinWidth(400);

		resultsTbl.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	}

	private void goNextTab() {
		int indexOfTab = this.tabPanel.indexOfTab("Generar");

		this.tabPanel.setEnabledAt(indexOfTab + 1, true);
		this.tabPanel.setSelectedIndex(indexOfTab + 1);
	}

	public void setTabPanel(JTabbedPane tabbedPane) {
		this.tabPanel = tabbedPane;
	}

}