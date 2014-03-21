package edu.udistrital.ing.sistemas.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import edu.udistrital.ing.sistemas.controller.ChainsController;
import edu.udistrital.ing.sistemas.utils.TestsUtils;

public class AnalyzerGUI extends Frame implements ActionListener {

	private static final long serialVersionUID = -9016052817791749651L;

	public JLabel results;
	protected JTable testsTbl;
	private TableCellRenderer color;
	private JTabbedPane tabPanel;
	private DefaultTableModel testsModelTbl;
	private JTextArea selectedChain;
	private JTextArea messageArea;
	private JButton okBtn;
	private JButton freqBtn;
	private JButton reportBtn;

	private ChainsController controller;
	private List<Integer> invalidChains;

	public AnalyzerGUI(ChainsController controller) {
		this.controller = controller;
	}

	public Component createComponent() {

		// Icon and colors to table results
		color = new ColorCellRender(this);

		// Top bar components
		selectedChain = new JTextArea(10, 10);
		selectedChain.setEditable(false);
		selectedChain.setLineWrap(true);
		JScrollPane selectedChainScrollPane = new JScrollPane(selectedChain);
		selectedChainScrollPane.setPreferredSize(new Dimension(500, 80));

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
		testsModelTbl = new DefaultTableModel() {
			private static final long serialVersionUID = 3778467886741762872L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		testsTbl = new JTable(testsModelTbl);
		JScrollPane scrollPane = new JScrollPane(testsTbl, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(780, 350));

		// Test results pane
		JPanel testResultsPane = new JPanel();
		testResultsPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		testResultsPane.add(scrollPane);

		// Test errors components
		messageArea = new JTextArea(10, 10);
		messageArea.setEditable(false);
		messageArea.setLineWrap(true);
		JScrollPane messagesScrollPane = new JScrollPane(messageArea);
		messagesScrollPane.setPreferredSize(new Dimension(350, 80));

		freqBtn = new JButton("Freq");
		freqBtn.setActionCommand("freq");
		freqBtn.addActionListener(this);
		freqBtn.setEnabled(true);

		reportBtn = new JButton("Report");
		reportBtn.setActionCommand("report");
		reportBtn.addActionListener(this);
		reportBtn.setEnabled(true);

		// Test errors pane
		JPanel testErrorsPane = new JPanel();
		testErrorsPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		testErrorsPane.add(new JLabel("Errors and Warnings: "));
		testErrorsPane.add(messagesScrollPane);
		testErrorsPane.add(freqBtn);
		testErrorsPane.add(reportBtn);

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

			// Clean chain
			selectedChain.setText("");
			controller.removeChain();

			showTestResults(controller.getResults());
			break;

		case "use_chain":

			int chainSelected = controller.getChain().getIndex() - 1;
			if (invalidChains.contains(chainSelected))
				JOptionPane.showMessageDialog(null, "La cadena seleccionada no aprueba los tests", "Cadena no válida", JOptionPane.ERROR_MESSAGE);
			else
				goNextTab();

			break;

		case "freq":
			try {

				showDialog(controller.getFreq(), "freq.txt");

			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}

			break;

		case "report":
			try {

				showDialog(controller.getReport(), "finalAnalysisReport.txt");

			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}

			break;
		}
	}

	private void showDialog(String freq, String title) {
		JTextArea areaFreq = new JTextArea(freq);

		JScrollPane scrollPane = new JScrollPane(areaFreq, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(700, 500));

		JPanel pane = new JPanel();
		pane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		pane.add(scrollPane);

		JDialog dialog = new JDialog();
		dialog.setTitle(title);
		dialog.add(pane);
		dialog.setSize(700, 500);
		dialog.setLocation(0, 0);
		dialog.setVisible(true);
	}

	private void showTestResults(Map<String, List<String>> results) {

		TestsUtils testUtils = new TestsUtils();
		testUtils.buildVectors(results);

		Vector<Vector<String>> data = testUtils.getData();
		Vector<String> columns = testUtils.getColumnNames();

		// Invalid chains
		invalidChains = testUtils.getFailures();

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

		ListSelectionModel lsm = testsTbl.getSelectionModel();
		lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lsm.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				ListSelectionModel lsm = (ListSelectionModel) e.getSource();

				if (lsm.isSelectionEmpty())
					return;

				// Save chain selected on table
				controller.setChain(lsm.getMinSelectionIndex());

				// Show chain selected
				selectedChain.setText(controller.getChain().getText());

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