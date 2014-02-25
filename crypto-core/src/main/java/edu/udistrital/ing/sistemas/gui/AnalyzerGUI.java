package edu.udistrital.ing.sistemas.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import edu.udistrital.ing.sistemas.controller.ChainsController;
import edu.udistrital.ing.sistemas.utils.TestsUtils;

public class AnalyzerGUI extends Frame implements ActionListener {

	private static final long serialVersionUID = -9016052817791749651L;
	private static Vector<String> columnNames;

	public JLabel results;
	protected JTable testsTbl;
	private TableCellRenderer color;
	private JTabbedPane tabPanel;
	private DefaultTableModel testsModelTbl;
	private JTextArea selectedChain;
	private JTextArea messageArea;
	private JButton okBtn;

	private ChainsController controller;

	public AnalyzerGUI(ChainsController controller) {
		this.controller = controller;
	}

	public Component createComponent() {

		// ColumnNames
		columnNames = new Vector<String>();
		columnNames.add("Id");
		columnNames.add("ApproximateEntropy");
		columnNames.add("CumulativeSums");
		columnNames.add("Frequency");
		columnNames.add("LongestRun");
		columnNames.add("OverlappingTemplate");
		columnNames.add("RandomExcursionsVariant");
		columnNames.add("Runs");
		columnNames.add("Universal");
		columnNames.add("BlockFrequency");
		columnNames.add("FFT");
		columnNames.add("LinearComplexity");
		columnNames.add("NonOverlappingTemplate");
		columnNames.add("RandomExcursions");
		columnNames.add("Rank");
		columnNames.add("Serial");
		columnNames.add("Result");

		// Icon and colors to table results
		color = new ColorCellRender(this);

		// Top bar components
		selectedChain = new JTextArea(10, 10);
		selectedChain.setEditable(false);
		selectedChain.setLineWrap(true);
		JScrollPane selectedChainScrollPane = new JScrollPane(selectedChain);
		selectedChainScrollPane.setPreferredSize(new Dimension(800, 100));
		selectedChainScrollPane.setMaximumSize(new Dimension(1000, 100));

		okBtn = new JButton("OK");
		okBtn.setActionCommand("use_chain");
		okBtn.addActionListener(this);
		okBtn.setEnabled(false);

		// Top bar pane
		JPanel topBarPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		topBarPane.add(new JLabel("Cadena seleccionada: "));
		topBarPane.add(selectedChainScrollPane);
		topBarPane.add(okBtn);

		// Test results components
		testsModelTbl = new DefaultTableModel(columnNames, 0);
		testsTbl = new JTable(testsModelTbl);
		JScrollPane scrollPane = new JScrollPane(testsTbl);
		scrollPane.setPreferredSize(new Dimension(1200, 600));
		scrollPane.setMaximumSize(new Dimension(1550, 600));

		// Test results pane
		JPanel testResultsPane = new JPanel();
		testResultsPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		testResultsPane.add(scrollPane);

		// Test errors components
		messageArea = new JTextArea(10, 10);
		messageArea.setEditable(false);
		messageArea.setLineWrap(true);
		JScrollPane messagesScrollPane = new JScrollPane(messageArea);
		messagesScrollPane.setPreferredSize(new Dimension(800, 50));
		messagesScrollPane.setMaximumSize(new Dimension(1000, 50));

		// Test errors pane
		JPanel testErrorsPane = new JPanel();
		testErrorsPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		testErrorsPane.add(new JLabel("Errors and Warnings: "));
		testErrorsPane.add(messagesScrollPane);

		// Main pane
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout(5, 5));
		pane.add(topBarPane, BorderLayout.NORTH);
		pane.add(testResultsPane, BorderLayout.CENTER);
		pane.add(testErrorsPane, BorderLayout.SOUTH);

		return pane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {

		case "change_tab":
			showTestResults(controller.getResults());
			break;

		case "use_chain":
			goNextTab();
			break;
		}
	}

	private void showTestResults(Map<String, List<String>> results) {

		TestsUtils testUtils = new TestsUtils();
		testUtils.buildVectors(results);

		Vector<Vector<String>> data = testUtils.getData();
		Vector<String> columns = testUtils.getColumnNames();

		// Errors Message
		StringBuilder message = new StringBuilder();
		Map<String, String> errors = testUtils.getErrors();
		for (Entry<String, String> e : errors.entrySet())
			message.append("\nTest: " + e.getKey() + "\nMessage: " + e.getValue() + "\n");
		messageArea.setText(message.toString());

		// Data
		testsModelTbl.setDataVector(data, columns);

		// Table
		testsTbl.setModel(testsModelTbl);
		testsTbl.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		testsTbl.getColumnModel().getColumn(columns.size() - 1).setCellRenderer(color);
		testsTbl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				// Save chain selected on table
				controller.setChain(e.getFirstIndex());

				// Show chain selected
				selectedChain.setText(controller.getChain());

				// Enable OK button
				okBtn.setEnabled(true);
			}
		});
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