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
import java.awt.Rectangle;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.Font;

public class GUI {

	private JFrame frmContentQaTesting;
	String folderPath;
	JLabel lblTweVersion;
	String genPath;
	private JTextField textFieldDate;
	private JTextField textFieldGenPath;
	private JTextField textFieldTWE;
	private JTextField textFieldServer;
	private JTextField textFieldPort;

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
		frmContentQaTesting.setBounds(new Rectangle(2000, 100, 394, 351));
		frmContentQaTesting.setTitle("TWE Automation");
		frmContentQaTesting.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmContentQaTesting.getContentPane().setLayout(null);

		final JButton btnBrowseTestFolder = new JButton("Browse");
		btnBrowseTestFolder.setBounds(12, 275, 96, 29);
		frmContentQaTesting.getContentPane().add(btnBrowseTestFolder);
		btnBrowseTestFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("C:\\Users\\kevin.vann\\Desktop"));
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int response = fileChooser.showOpenDialog(btnBrowseTestFolder);
				if (response == JFileChooser.APPROVE_OPTION) {
					folderPath = fileChooser.getSelectedFile().toString();
					lblTweVersion.setText("v" + fileChooser.getSelectedFile().getName().substring(0, 4));
				} else {
					folderPath = "";
					lblTweVersion.setText("");
				}
			}

		});

		JLabel lblTestFolder = new JLabel("Test Folder:");
		lblTestFolder.setBounds(218, 281, 70, 16);
		frmContentQaTesting.getContentPane().add(lblTestFolder);

		lblTweVersion = new JLabel("");
		lblTweVersion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTweVersion.setBounds(300, 281, 76, 16);
		frmContentQaTesting.getContentPane().add(lblTweVersion);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 390, 260);
		frmContentQaTesting.getContentPane().add(tabbedPane);

		JPanel panelFunctional = new JPanel();
		tabbedPane.addTab("Functional", null, panelFunctional, null);
		panelFunctional.setLayout(null);

		JLabel lblTweServer = new JLabel("TWE Server:");
		lblTweServer.setBounds(12, 45, 84, 16);
		panelFunctional.add(lblTweServer);

		final JLabel lblTaxType = new JLabel("Tax Type:");
		lblTaxType.setBounds(12, 109, 58, 16);
		panelFunctional.add(lblTaxType);

		final JComboBox<?> testTypeCombo = new JComboBox();
		testTypeCombo.setBounds(12, 128, 163, 22);
		panelFunctional.add(testTypeCombo);
		testTypeCombo.setModel(new DefaultComboBoxModel(new String[] { "New Codes", "Updated Codes", "Rates Changes",
				"New Codes\\BF", "Updated Codes\\BF", "Rates Changes\\BF" }));

		JButton runTestsButton = new JButton("Load Tests");
		runTestsButton.setBounds(216, 57, 145, 36);
		panelFunctional.add(runTestsButton);

		JButton shrinkFilesButton = new JButton("Shrink Files");
		shrinkFilesButton.setBounds(216, 110, 145, 36);
		panelFunctional.add(shrinkFilesButton);

		JButton moveReducedButton = new JButton("Move Reduced");
		moveReducedButton.setBounds(216, 163, 145, 36);
		panelFunctional.add(moveReducedButton);

		textFieldServer = new JTextField();
		textFieldServer.setBounds(12, 64, 106, 22);
		panelFunctional.add(textFieldServer);
		textFieldServer.setColumns(10);

		textFieldPort = new JTextField();
		textFieldPort.setBounds(130, 64, 45, 22);
		panelFunctional.add(textFieldPort);
		textFieldPort.setColumns(10);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(130, 45, 56, 16);
		panelFunctional.add(lblPort);
		moveReducedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!new File(folderPath + "\\" + testTypeCombo.getSelectedItem().toString()).exists()) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Invalid Directory");
				} else {

					TestFolders folder = new TestFolders(
							folderPath + "\\" + testTypeCombo.getSelectedItem().toString());
					try {
						folder.moveReducedFiles();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(frmContentQaTesting, "Moving Reduced Files Complete");
				}

			}
		});
		shrinkFilesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!new File(folderPath + "\\" + testTypeCombo.getSelectedItem().toString()).exists()) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Invalid Directory");
				} else if (!new File(folderPath + "\\" + testTypeCombo.getSelectedItem().toString()
						+ "\\Outputs\\File Shrinker V1.3.exe").exists()) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Could not find 'File Shrink V1.3.exe'");
				} else {
					FileShrinker fileShrink = null;
					try {
						fileShrink = new FileShrinker(folderPath + "\\" + testTypeCombo.getSelectedItem().toString());
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
					JOptionPane.showMessageDialog(frmContentQaTesting, "File Shrinking Complete");
				}
			}
		});
		runTestsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!new File(folderPath + "\\" + testTypeCombo.getSelectedItem().toString()).exists()) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Invalid Directory");
				} else if (textFieldServer.getText().isEmpty() || textFieldPort.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frmContentQaTesting, "Please enter required fields");
				}

				else {
					TWEInstance tweInstance1 = null;
					try {
						tweInstance1 = new TWEInstance(folderPath + "\\" + testTypeCombo.getSelectedItem().toString(),
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
					JOptionPane.showMessageDialog(frmContentQaTesting, "Test Loading Complete");
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
					tweGen.createCriterionTests(textFieldDate.getText(), textFieldTWE.getText());
				}
			}
		});
		btnCreateCriterionTest.setBounds(12, 149, 184, 38);
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
		btnBrowseGenPath.setBounds(337, 45, 36, 25);
		panelRegression.add(btnBrowseGenPath);

		JButton btnLoadEntered = new JButton("Load Entered");
		btnLoadEntered.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadRegressionTest("twecontentdev1", 8380, 1);
			}
		});
		btnLoadEntered.setBounds(255, 113, 118, 25);
		panelRegression.add(btnLoadEntered);

		JButton btnLoadReady = new JButton("Load Ready");
		btnLoadReady.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadRegressionTest("twecontentdev1", 8380, 2);
			}
		});
		btnLoadReady.setBounds(255, 87, 118, 25);
		panelRegression.add(btnLoadReady);

		textFieldDate = new JTextField();
		textFieldDate.setBounds(12, 114, 116, 22);
		panelRegression.add(textFieldDate);
		textFieldDate.setColumns(10);

		JLabel lblDatemmmyyyy = new JLabel("Date <MMMYYYY>:");
		lblDatemmmyyyy.setBounds(12, 92, 111, 16);
		panelRegression.add(lblDatemmmyyyy);

		textFieldGenPath = new JTextField();
		textFieldGenPath.setEditable(false);
		textFieldGenPath.setBounds(12, 46, 318, 22);
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
		lblGeneratorPath.setBounds(12, 25, 128, 16);
		panelRegression.add(lblGeneratorPath);

		JButton btn84i = new JButton("Load 84i");
		btn84i.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadRegressionTest("twecontentdev2", 8380, 3);
			}
		});
		btn84i.setBounds(255, 139, 118, 25);
		panelRegression.add(btn84i);

		JButton btn84m = new JButton("Load 84m");
		btn84m.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadRegressionTest("twecontentdev2", 8480, 4);
			}
		});
		btn84m.setBounds(255, 165, 118, 25);
		panelRegression.add(btn84m);

		JButton btn84ssti = new JButton("Load 84ssti");
		btn84ssti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadRegressionTest("twecontentdev2", 8580, 5);
			}
		});
		btn84ssti.setBounds(255, 192, 118, 25);
		panelRegression.add(btn84ssti);

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
				tweInstance1.runRegressionTests(type);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(frmContentQaTesting, "Test Loading Complete");
		}
	}
}
