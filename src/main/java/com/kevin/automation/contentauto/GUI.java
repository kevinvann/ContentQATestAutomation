package com.kevin.automation.contentauto;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.AWTException;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;

public class GUI {

	private JFrame frmContentQaTesting;
	String folderPath;
	String genPath;
	private JTextField textFieldDate;
	private JTextField textFieldGenPath;
	private JTextField textFieldTWE;
	private JTextField textFieldServer;
	private JTextField textFieldPort;
	private JTextField textUpdatePath;

	/**
	 * Launch the application.
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmContentQaTesting.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {

		frmContentQaTesting = new JFrame();
		frmContentQaTesting.setResizable(false);
		frmContentQaTesting.setBounds(200, 200, 394, 351);
		frmContentQaTesting.setTitle("TWE Automation");
		frmContentQaTesting.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmContentQaTesting.getContentPane().setLayout(null);

		final JButton btnBrowseTestFolder = new JButton("Browse");
		btnBrowseTestFolder.setBounds(10, 279, 36, 25);
		frmContentQaTesting.getContentPane().add(btnBrowseTestFolder);
		btnBrowseTestFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("T:\\WIL\\Departments\\TaxContentQA\\Test Artifacts & Results\\TWE Content Update"));
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int response = fileChooser.showOpenDialog(btnBrowseTestFolder);
				if (response == JFileChooser.APPROVE_OPTION) {
					folderPath = fileChooser.getSelectedFile().toString();
					textUpdatePath.setText(folderPath.toString());
				} else {
					folderPath = "";
					textUpdatePath.setText("");
				}
			}

		});

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 390, 260);
		frmContentQaTesting.getContentPane().add(tabbedPane);

		JPanel panelFunctional = new JPanel();
		tabbedPane.addTab("Functional", null, panelFunctional, null);
		panelFunctional.setLayout(null);

		JLabel lblTweServer = new JLabel("TWE Server:");
		lblTweServer.setBounds(12, 29, 84, 25);
		panelFunctional.add(lblTweServer);

		final JLabel lblTaxType = new JLabel("Update Document Type:");
		lblTaxType.setBounds(12, 102, 152, 16);
		panelFunctional.add(lblTaxType);

		final JComboBox<?> comboUpdateDocType = new JComboBox();
		comboUpdateDocType.setBounds(12, 125, 163, 22);
		panelFunctional.add(comboUpdateDocType);
		comboUpdateDocType.setModel(new DefaultComboBoxModel(new String[] { "New Codes", "Updated Codes", "Rates Changes",
				"New Codes\\BF", "Updated Codes\\BF", "Rates Changes\\BF" }));

		final JButton btnRunTests = new JButton("Run Tests");
		btnRunTests.setBounds(226, 54, 145, 25);
		panelFunctional.add(btnRunTests);

		final JButton btnShrinkFiles = new JButton("Shrink Files");
		btnShrinkFiles.setBounds(226, 92, 145, 25);
		panelFunctional.add(btnShrinkFiles);

		final JButton btnMoveReduced = new JButton("Move Reduced");
		btnMoveReduced.setBounds(226, 130, 145, 25);
		panelFunctional.add(btnMoveReduced);

		textFieldServer = new JTextField();
		textFieldServer.setBounds(12, 55, 106, 22);
		panelFunctional.add(textFieldServer);
		textFieldServer.setColumns(10);

		textFieldPort = new JTextField();
		textFieldPort.setBounds(130, 55, 45, 22);
		panelFunctional.add(textFieldPort);
		textFieldPort.setColumns(10);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(130, 29, 56, 25);
		panelFunctional.add(lblPort);
		
		final JCheckBox chckbxBf = new JCheckBox("BF");
		chckbxBf.setBounds(226, 187, 43, 25);
		panelFunctional.add(chckbxBf);
		
		JButton btnRunAll = new JButton("Run All");
		btnRunAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = 0;
				if(chckbxBf.isSelected()) {
					i = 3;
				}
				for(int x = i; x < i + 3; x++) {
				comboUpdateDocType.setSelectedIndex(x);
				btnRunTests.doClick();
				btnShrinkFiles.doClick();
				btnMoveReduced.doClick();
				}
				JOptionPane.showMessageDialog(frmContentQaTesting, "Process Completed");
			}
		});
		btnRunAll.setBounds(272, 181, 99, 36);
		panelFunctional.add(btnRunAll);
		
		JLabel lblRunByDocType = new JLabel("Run by Update Doc Type:");
		lblRunByDocType.setBounds(226, 29, 145, 16);
		panelFunctional.add(lblRunByDocType);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(226, 171, 145, 8);
		panelFunctional.add(separator);
		

		btnMoveReduced.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!new File(folderPath + "\\" + comboUpdateDocType.getSelectedItem().toString()).exists()) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Invalid Directory");
				} else {

					UpdateFolder folder = new UpdateFolder(
							folderPath + "\\" + comboUpdateDocType.getSelectedItem().toString());
					try {
						folder.moveReducedFiles();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		btnShrinkFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!new File(folderPath + "\\" + comboUpdateDocType.getSelectedItem().toString()).exists()) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Invalid Directory");
				} else if (!new File(folderPath + "\\" + comboUpdateDocType.getSelectedItem().toString()
						+ "\\Outputs\\File Shrinker V1.3.exe").exists()) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Could not find 'File Shrink V1.3.exe'");
				} else {
					FileShrinker fileShrink = null;
					try {
						fileShrink = new FileShrinker(folderPath + "\\" + comboUpdateDocType.getSelectedItem().toString());
					} catch (AWTException e) {
						e.printStackTrace();
					}
					fileShrink.startFileShrinker();
					try {
						fileShrink.shrinkFiles();
					} catch (AWTException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnRunTests.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!new File(folderPath + "\\" + comboUpdateDocType.getSelectedItem().toString()).exists()) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Invalid Directory");
					System.exit(1);
				} else if (textFieldServer.getText().isEmpty() || textFieldPort.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Please enter required fields");
				}

				else {
					TWEInstance tweInstance1 = null;
					try {
						tweInstance1 = new TWEInstance(folderPath + "\\" + comboUpdateDocType.getSelectedItem().toString(),
								textFieldServer.getText(), Integer.parseInt(textFieldPort.getText()));
					} catch (AWTException e) {
						e.printStackTrace();
					}
					tweInstance1.logIn();
					try {
						tweInstance1.goToCalculation();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					tweInstance1.goToBatchCalculation();
					try {
						tweInstance1.runInputTests(0);
					} catch (AWTException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		});

		JPanel panelRegression = new JPanel();
		tabbedPane.addTab("Regression", null, panelRegression, null);
		panelRegression.setLayout(null);

		JButton btnCreateCriterionTest = new JButton("Create Criterion Tests");
		btnCreateCriterionTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TWEGenerator tweGen = null;
				if (textFieldDate.getText().equals("") || textFieldTWE.getText().equals("")) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Please enter required fields");
				} else if (!new File(folderPath + "\\Regression\\Criterion Tests\\Inputs").exists()) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Invalid test folder");
				} else if (!new File(genPath + "\\TWE Generator V.4.exe").exists()) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Could not find 'Tax Generator V.4.exe'");
				} else {
					try {
						tweGen = new TWEGenerator(genPath);
					} catch (AWTException e1) {
						e1.printStackTrace();
					}
					tweGen.openGenerator();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					try {
						tweGen.createCriterionTests(textFieldDate.getText(), textFieldTWE.getText(), folderPath + "\\Regression\\Criterion Tests\\Inputs");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnCreateCriterionTest.setBounds(12, 149, 184, 25);
		panelRegression.add(btnCreateCriterionTest);

		final JButton btnBrowseGenPath = new JButton("Browse Generator Folder");
		btnBrowseGenPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("C:\\Users\\kevin.vann\\Desktop"));
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int response = fileChooser.showOpenDialog(btnBrowseGenPath);
				if (response == JFileChooser.APPROVE_OPTION) {
					genPath = fileChooser.getSelectedFile().toString();
					textFieldGenPath.setText(genPath);
				} else {
					genPath = "";
					textFieldGenPath.setText("");
				}
			}
		});
		btnBrowseGenPath.setBounds(337, 50, 36, 25);
		panelRegression.add(btnBrowseGenPath);

		JButton btnRunReady = new JButton("Run Ready");
		btnRunReady.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadRegressionTest("twecontentdev1", 8380, 1);
			}
		});
		btnRunReady.setBounds(255, 113, 118, 25);
		panelRegression.add(btnRunReady);

		JButton btnRunEntered = new JButton("Run Entered");
		btnRunEntered.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadRegressionTest("twecontentdev1", 8380, 2);
			}
		});
		btnRunEntered.setBounds(255, 87, 118, 25);
		panelRegression.add(btnRunEntered);

		textFieldDate = new JTextField();
		textFieldDate.setBounds(12, 114, 116, 22);
		panelRegression.add(textFieldDate);
		textFieldDate.setColumns(10);

		JLabel lblDatemmmyyyy = new JLabel("Date <MMMYYYY>:");
		lblDatemmmyyyy.setBounds(12, 92, 111, 16);
		panelRegression.add(lblDatemmmyyyy);

		textFieldGenPath = new JTextField();
		textFieldGenPath.setEditable(false);
		textFieldGenPath.setBounds(12, 50, 318, 25);
		panelRegression.add(textFieldGenPath);
		textFieldGenPath.setColumns(10);

		JLabel lblTwe = new JLabel("TWE#:");
		lblTwe.setBounds(140, 92, 56, 16);
		panelRegression.add(lblTwe);

		textFieldTWE = new JTextField();
		textFieldTWE.setBounds(140, 114, 56, 22);
		panelRegression.add(textFieldTWE);
		textFieldTWE.setColumns(10);

		JLabel lblGeneratorPath = new JLabel("Test Generator Path:");
		lblGeneratorPath.setBounds(12, 28, 128, 16);
		panelRegression.add(lblGeneratorPath);

		JButton btn84i = new JButton("Run 84i");
		btn84i.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadRegressionTest("twecontentdev2", 8380, 3);
			}
		});
		btn84i.setBounds(255, 139, 118, 25);
		panelRegression.add(btn84i);

		JButton btn84m = new JButton("Run 84m");
		btn84m.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadRegressionTest("twecontentdev2", 8480, 4);
			}
		});
		btn84m.setBounds(255, 165, 118, 25);
		panelRegression.add(btn84m);

		JButton btn84ssti = new JButton("Run 84ssti");
		btn84ssti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadRegressionTest("twecontentdev2", 8580, 5);
			}
		});
		btn84ssti.setBounds(255, 192, 118, 25);
		panelRegression.add(btn84ssti);
		
		JButton btnMoveCrit = new JButton("Move Criterion Tests");
		btnMoveCrit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TWEGenerator tweGen = null;
				if (textFieldDate.getText().equals("") || textFieldTWE.getText().equals("")) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Please enter required fields");
				} else if (!new File(folderPath + "\\Regression\\Criterion Tests\\Inputs").exists()) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Invalid test folder");
				} else if (!new File(genPath + "\\TWE Generator V.4.exe").exists()) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Could not find 'Tax Generator V.4.exe'");
				} else {
					try {
						tweGen = new TWEGenerator(genPath);
					} catch (AWTException e1) {
						e1.printStackTrace();
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					try {
						tweGen.moveAndRename(textFieldDate.getText(), textFieldTWE.getText(), folderPath + "\\Regression\\Criterion Tests\\Inputs");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnMoveCrit.setBounds(12, 187, 184, 25);
		panelRegression.add(btnMoveCrit);
		
		textUpdatePath = new JTextField();
		textUpdatePath.setEditable(false);
		textUpdatePath.setBounds(60, 279, 314, 25);
		frmContentQaTesting.getContentPane().add(textUpdatePath);
		textUpdatePath.setColumns(10);

	}

	private void loadRegressionTest(String server, int port, int type) {
		if (!new File(folderPath + "\\Regression\\Criterion Tests").exists()) {
			JOptionPane.showMessageDialog(frmContentQaTesting, "Invalid Directory");
		} else {
			TWEInstance tweInstance1 = null;
			try {
				tweInstance1 = new TWEInstance(folderPath + "\\Regression\\Criterion Tests", server, port);
			} catch (AWTException e) {
				e.printStackTrace();
			}
			tweInstance1.logIn();
			try {
				tweInstance1.goToCalculation();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tweInstance1.goToBatchCalculation();
			try {
				try {
					tweInstance1.runInputTests(type);
				} catch (AWTException e) {
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(frmContentQaTesting, "Test Loading Complete");
		}
	}
}
