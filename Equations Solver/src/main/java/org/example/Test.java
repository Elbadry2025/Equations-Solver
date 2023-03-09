package org.example;

import org.python.util.PythonInterpreter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Vector;
import java.beans.PropertyChangeEvent;

public class Test {

	private JFrame frame, phaseSelection, frame2;
	private JTextField[][] coef = new JTextField[10][11];
	private JLabel[][] labels = new JLabel[10][10];
	//
	private JTextField[] initialGuess = new JTextField[10];
	private JLabel[] initialGuess_Label = new JLabel[10];
	//

	private JTextField ErrorTextField, txtms, iterationsDone, equationField, epsilonField, lowerBoundField, upperBoundField, XiField, XiiField, initialField, textField, txtms2,textField_1;
	private JLabel LUText, IterationsNumberText, IterationsNumberText2, ErrorText, PrecisionText, PrecisionText2, DigitsLabel, DigitsLabel2, lblNewLabel_2, equationLabel, epsilon, lowerBound, upperBound, Xi, Xii, initial, lblX, lblNewLabel2, lblIterations;
	private JComboBox LUComboBox;
	private JSpinner IterationsNumberSpinner, IterationsNumberSpinner2, PrecisionDigitsSpinner, PrecisionDigitsSpinner2;
	private JButton button, button2, phase1Button, phase2Button, backButton1, backButton2;
	double t1,t2, er;
	private JLabel lblNewLabel_1;
	JCheckBox chckbxNewCheckBox;
	double[] iGuess = new double[10];
	int it ;
	int x = 20, y = 280, width = 80, height = 20;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test window = new Test();
					window.phaseSelection.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		phaseSelection = new JFrame();
		phaseSelection.getContentPane().setBackground(new Color(193, 255, 255));
		phaseSelection.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		phaseSelection.setBounds(500, 275, 500, 250);
		phaseSelection.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		phaseSelection.getContentPane().setLayout(null);
		phaseSelection.setVisible(true);

		Cursor cursor = new Cursor(Cursor.HAND_CURSOR);

		phase1Button = new JButton("Phase One");
		phase1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				phaseSelection.setVisible(false);
				frame.setVisible(true);
			}
		});
		phase1Button.setBackground(new Color(0, 128, 255));
		phase1Button.setFont(new Font("Dialog", Font.BOLD, 20));
		phase1Button.setCursor(cursor);
		phase1Button.setBounds(75, 65, 138, 63);
		phase1Button.setFocusable(false);
		phaseSelection.getContentPane().add(phase1Button);

		phase2Button = new JButton("Phase Two");
		phase2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				phaseSelection.setVisible(false);
				frame2.setVisible(true);
			}
		});
		phase2Button.setBackground(new Color(0, 128, 255));
		phase2Button.setFont(new Font("Dialog", Font.BOLD, 20));
		phase2Button.setCursor(cursor);
		phase2Button.setBounds(275, 65, 138, 63);
		phase2Button.setFocusable(false);
		phaseSelection.getContentPane().add(phase2Button);

		//---------------------------------------------------------------------------------

		//phase one

		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(193, 255, 255));
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.setBounds(0, 0, 1920, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(false);

		for(int i = 0; i < 10; i++) {
			x = 20;
			for(int j = 0; j < 11; j++) {
				coef[i][j] = new JTextField();
				coef[i][j].setBackground(new Color(240, 240, 240));
				coef[i][j].setFont(new Font("Tahoma", Font.PLAIN, 18));
				coef[i][j].setText("0");
				coef[i][j].setBounds(x, y, width, height);
				coef[i][j].setVisible(false);
				frame.getContentPane().add(coef[i][j]);
				if(j != 10) {
					labels[i][j] = new JLabel();
					labels[i][j].setFont(new Font("Tahoma", Font.PLAIN, 20));
					labels[i][j].setBounds(x + 80, y, 70, height);
					labels[i][j].setVisible(false);
					frame.getContentPane().add(labels[i][j]);
				}
				x += 135;
			}
			y += 50;
		}
		//
		x = 20; y =220;
		for(int i = 0; i < 10; i++) {
			initialGuess[i] = new JTextField();
			initialGuess[i].setBackground(new Color(240, 240, 240));
			initialGuess[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
			initialGuess[i].setText("0");
			if(i==9) initialGuess[i].setBounds(x+51, y, width, height);
			else initialGuess[i].setBounds(x+40, y, width, height);
			initialGuess[i].setVisible(false);
			frame.getContentPane().add(initialGuess[i]);
			initialGuess[i].setVisible(false);

			initialGuess_Label[i] = new JLabel();
			initialGuess_Label[i].setFont(new Font("Tahoma", Font.PLAIN, 20));
			initialGuess_Label[i].setBounds(x , y, 70, height);
			initialGuess_Label[i].setVisible(false);
			frame.getContentPane().add(initialGuess_Label[i]);
			initialGuess_Label[i].setVisible(false);
			x += 135;


		}

		lblNewLabel_1 = new JLabel("Initial Guess");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setBounds(20, 171, 198, 26);
		frame.getContentPane().add(lblNewLabel_1);

		chckbxNewCheckBox = new JCheckBox("Enable Scaling");
		chckbxNewCheckBox.setBackground(new Color(193, 255, 255));
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		chckbxNewCheckBox.setBounds(217, 126, 144, 38);
		frame.getContentPane().add(chckbxNewCheckBox);

		lblNewLabel_2 = new JLabel("Iterations done");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(1078, 131, 144, 26);
		frame.getContentPane().add(lblNewLabel_2);
		lblNewLabel_2.setVisible(false);

		iterationsDone = new JTextField();
		iterationsDone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		iterationsDone.setBounds(1232, 130, 96, 29);
		frame.getContentPane().add(iterationsDone);
		iterationsDone.setColumns(10);
		iterationsDone.setVisible(false);
		//

		ErrorText = new JLabel("Choose the absolute relative error");
		ErrorText.setFont(new Font("Tahoma", Font.BOLD, 14));
		ErrorText.setBounds(847, 23, 260, 26);
		frame.getContentPane().add(ErrorText);
		ErrorText.setVisible(false);

		ErrorTextField = new JTextField();
		ErrorTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		ErrorTextField.setText("0.0001");
		ErrorTextField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ErrorTextField.setBounds(847, 45, 242, 26);
		frame.getContentPane().add(ErrorTextField);
		ErrorTextField.setVisible(false);

		IterationsNumberText = new JLabel("Choose number of iterations");
		IterationsNumberText.setFont(new Font("Tahoma", Font.BOLD, 14));
		IterationsNumberText.setBounds(567, 23, 205, 26);
		frame.getContentPane().add(IterationsNumberText);
		IterationsNumberText.setVisible(false);

		IterationsNumberSpinner = new JSpinner();
		IterationsNumberSpinner.setModel(new SpinnerNumberModel(1, 1, 100, 1));
		IterationsNumberSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		IterationsNumberSpinner.setBounds(567, 45, 242, 26);
		frame.getContentPane().add(IterationsNumberSpinner);
		IterationsNumberSpinner.setVisible(false);

		LUText = new JLabel("Choose the LU format\r\n");
		LUText.setFont(new Font("Tahoma", Font.BOLD, 14));
		LUText.setBounds(567, 23, 198, 26);
		frame.getContentPane().add(LUText);
		LUText.setVisible(false);

		LUComboBox = new JComboBox();
		LUComboBox.setBackground(new Color(255, 255, 255));
		LUComboBox.setModel(new DefaultComboBoxModel(new String[] {"Dolittle", "Crout", "Cholesky"}));
		LUComboBox.setSelectedIndex(0);
		LUComboBox.setFont(new Font("Segoe UI", Font.BOLD, 16));
		LUComboBox.setCursor(cursor);
		LUComboBox.setBounds(567, 45, 242, 26);
		frame.getContentPane().add(LUComboBox);
		LUComboBox.setVisible(false);

		JLabel equationsText = new JLabel("Enter number of equations");
		equationsText.setFont(new Font("Tahoma", Font.BOLD, 14));
		equationsText.setBounds(20, 45, 198, 26);
		frame.getContentPane().add(equationsText);

		JSpinner equationsSpinner = new JSpinner();
		equationsSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		equationsSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				setBoxes((int)equationsSpinner.getValue());
			}
		});
		equationsSpinner.setModel(new SpinnerNumberModel(2, 2, 10, 1));
		equationsSpinner.setBounds(217, 45, 45, 26);
		frame.getContentPane().add(equationsSpinner);

		JLabel methodText = new JLabel("Choose solving method");
		methodText.setFont(new Font("Tahoma", Font.BOLD, 14));
		methodText.setBounds(293, 23, 198, 26);
		frame.getContentPane().add(methodText);

		JComboBox methodComboBox = new JComboBox();
		methodComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMethod((String)methodComboBox.getSelectedItem(), (int)equationsSpinner.getValue());
			}
		});
		methodComboBox.setBackground(new Color(255, 255, 255));
		methodComboBox.setCursor(cursor);
		methodComboBox.setFont(new Font("Segoe UI", Font.BOLD, 16));
		methodComboBox.setModel(new DefaultComboBoxModel(new String[] {"Gauss Elimination", "Gauss Jordan", "LU Decomposition", "Gauss Seidel", "Jacobi Iteration"}));
		methodComboBox.setSelectedIndex(0);
		methodComboBox.setBounds(293, 45, 242, 26);
		frame.getContentPane().add(methodComboBox);

		PrecisionText = new JLabel("Choose the Precision");
		PrecisionText.setFont(new Font("Tahoma", Font.BOLD, 14));
		PrecisionText.setBounds(514, 106, 242, 26);
		frame.getContentPane().add(PrecisionText);
		PrecisionText.setVisible(true);

		PrecisionDigitsSpinner = new JSpinner();
		PrecisionDigitsSpinner.setModel(new SpinnerNumberModel(1, 1, 16, 1));
		PrecisionDigitsSpinner.setFont(new Font("Tahoma", Font.PLAIN, 18));
		PrecisionDigitsSpinner.setBounds(514, 132, 50, 26);
		frame.getContentPane().add(PrecisionDigitsSpinner);
		PrecisionDigitsSpinner.setVisible(true);

		DigitsLabel = new JLabel("Digits");
		DigitsLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		DigitsLabel.setBounds(574, 131, 68, 26);
		DigitsLabel.setVisible(true);
		frame.getContentPane().add(DigitsLabel);

		button = new JButton("Solve");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solve((String)methodComboBox.getSelectedItem(), (int)equationsSpinner.getValue(), (String)LUComboBox.getSelectedItem(), chckbxNewCheckBox.isSelected());
			}
		});
		button.setBackground(new Color(0, 128, 255));
		button.setFont(new Font("Dialog", Font.BOLD, 30));
		button.setCursor(cursor);
		button.setBounds(1277, 11, 138, 63);
		button.setFocusable(false);
		frame.getContentPane().add(button);

		backButton1 = new JButton("Back");
		backButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				phaseSelection.setVisible(true);
				frame.setVisible(false);
			}
		});
		backButton1.setBackground(new Color(0, 128, 255));
		backButton1.setFont(new Font("Dialog", Font.BOLD, 20));
		backButton1.setCursor(cursor);
		backButton1.setBounds(1277, 180, 138, 63);
		backButton1.setFocusable(false);
		frame.getContentPane().add(backButton1);

		Canvas canvas = new Canvas();
		canvas.setBackground(new Color(0, 0, 0));
		canvas.setBounds(0, 250, 1920, 2);
		frame.getContentPane().add(canvas);

		JLabel lblNewLabel = new JLabel("RunTime");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(802, 122, 88, 45);
		frame.getContentPane().add(lblNewLabel);

		txtms = new JTextField();
		txtms.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtms.setBounds(900, 130, 96, 29);
		frame.getContentPane().add(txtms);
		txtms.setColumns(10);

		setBoxes(2);

		//---------------------------------------------------------------------------------

		//phase two

		frame2 = new JFrame();
		frame2.getContentPane().setBackground(new Color(193, 255, 255));
		frame2.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame2.setBounds(0, 0, 1920, 1080);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.getContentPane().setLayout(null);
		frame2.setVisible(false);

		backButton2 = new JButton("Back");
		backButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				phaseSelection.setVisible(true);
				frame2.setVisible(false);
			}
		});
		backButton2.setBackground(new Color(0, 128, 255));
		backButton2.setFont(new Font("Dialog", Font.BOLD, 20));
		backButton2.setCursor(cursor);
		backButton2.setBounds(1277, 180, 138, 63);
		backButton2.setFocusable(false);
		frame2.getContentPane().add(backButton2);

		equationLabel = new JLabel("Enter the equation");
		equationLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		equationLabel.setBounds(20, 45, 140, 26);
		frame2.getContentPane().add(equationLabel);

		equationField = new JTextField();
		equationField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		equationField.setBounds(165, 45, 500, 26);
		frame2.getContentPane().add(equationField);

		JLabel methodText2 = new JLabel("Choose solving method");
		methodText2.setFont(new Font("Tahoma", Font.BOLD, 14));
		methodText2.setBounds(793, 23, 198, 26);
		frame2.getContentPane().add(methodText2);

		JComboBox methodComboBox2 = new JComboBox();
		methodComboBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setMethod2((String)methodComboBox2.getSelectedItem());
			}
		});
		methodComboBox2.setBackground(new Color(255, 255, 255));
		methodComboBox2.setCursor(cursor);
		methodComboBox2.setFont(new Font("Segoe UI", Font.BOLD, 16));
		methodComboBox2.setModel(new DefaultComboBoxModel(new String[] {"Bisection", "False-Position", "Fixed Point", "Newton-Raphson", "Secant"}));
		methodComboBox2.setSelectedIndex(0);
		methodComboBox2.setBounds(793, 45, 242, 26);
		frame2.getContentPane().add(methodComboBox2);

		PrecisionText2 = new JLabel("Choose the Precision");
		PrecisionText2.setFont(new Font("Tahoma", Font.BOLD, 14));
		PrecisionText2.setBounds(514, 109, 242, 26);
		frame2.getContentPane().add(PrecisionText2);

		PrecisionDigitsSpinner2 = new JSpinner();
		PrecisionDigitsSpinner2.setModel(new SpinnerNumberModel(1, 1, 16, 1));
		PrecisionDigitsSpinner2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		PrecisionDigitsSpinner2.setBounds(514, 132, 50, 26);
		frame2.getContentPane().add(PrecisionDigitsSpinner2);

		DigitsLabel2 = new JLabel("Digits");
		DigitsLabel2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		DigitsLabel2.setBounds(574, 131, 68, 26);
		DigitsLabel2.setVisible(true);
		frame2.getContentPane().add(DigitsLabel2);

		button2 = new JButton("Solve");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solve2((String)methodComboBox2.getSelectedItem());
			}
		});
		button2.setBackground(new Color(0, 128, 255));
		button2.setFont(new Font("Dialog", Font.BOLD, 30));
		button2.setCursor(cursor);
		button2.setBounds(1277, 11, 138, 63);
		button2.setFocusable(false);
		frame2.getContentPane().add(button2);

		epsilon = new JLabel("Epsilon");
		epsilon.setFont(new Font("Tahoma", Font.BOLD, 14));
		epsilon.setBounds(50, 109, 68, 26);
		frame2.getContentPane().add(epsilon);

		epsilonField = new JTextField();
		epsilonField.setHorizontalAlignment(SwingConstants.RIGHT);
		epsilonField.setText("0.0001");
		epsilonField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		epsilonField.setBounds(50, 131, 120, 26);
		frame2.getContentPane().add(epsilonField);

		IterationsNumberText2 = new JLabel("Choose number of iterations");
		IterationsNumberText2.setFont(new Font("Tahoma", Font.BOLD, 14));
		IterationsNumberText2.setBounds(250, 109, 250, 26);
		frame2.getContentPane().add(IterationsNumberText2);

		IterationsNumberSpinner2 = new JSpinner();
		IterationsNumberSpinner2.setModel(new SpinnerNumberModel(50, 1, 100, 1));
		IterationsNumberSpinner2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		IterationsNumberSpinner2.setBounds(250, 131, 68, 26);
		frame2.getContentPane().add(IterationsNumberSpinner2);

		lowerBound = new JLabel("Lower Bound");
		lowerBound.setFont(new Font("Tahoma", Font.BOLD, 14));
		lowerBound.setBounds(750, 109, 100, 26);
		frame2.getContentPane().add(lowerBound);

		lowerBoundField = new JTextField();
		lowerBoundField.setHorizontalAlignment(SwingConstants.RIGHT);
		lowerBoundField.setText("0");
		lowerBoundField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lowerBoundField.setBounds(750, 131, 120, 26);
		frame2.getContentPane().add(lowerBoundField);

		upperBound = new JLabel("Upper Bound");
		upperBound.setFont(new Font("Tahoma", Font.BOLD, 14));
		upperBound.setBounds(950, 109, 100, 26);
		frame2.getContentPane().add(upperBound);

		upperBoundField = new JTextField();
		upperBoundField.setHorizontalAlignment(SwingConstants.RIGHT);
		upperBoundField.setText("1");
		upperBoundField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		upperBoundField.setBounds(950, 131, 120, 26);
		frame2.getContentPane().add(upperBoundField);

		Xii = new JLabel("Xi-1");
		Xii.setFont(new Font("Tahoma", Font.BOLD, 14));
		Xii.setBounds(750, 109, 100, 26);
		frame2.getContentPane().add(Xii);
		Xii.setVisible(false);

		XiiField = new JTextField();
		XiiField.setHorizontalAlignment(SwingConstants.RIGHT);
		XiiField.setText("0");
		XiiField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		XiiField.setBounds(750, 131, 120, 26);
		frame2.getContentPane().add(XiiField);
		XiiField.setVisible(false);

		Xi = new JLabel("Xi");
		Xi.setFont(new Font("Tahoma", Font.BOLD, 14));
		Xi.setBounds(950, 109, 100, 26);
		frame2.getContentPane().add(Xi);
		Xi.setVisible(false);

		XiField = new JTextField();
		XiField.setHorizontalAlignment(SwingConstants.RIGHT);
		XiField.setText("1");
		XiField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		XiField.setBounds(950, 131, 120, 26);
		frame2.getContentPane().add(XiField);
		XiField.setVisible(false);

		initial = new JLabel("Initial Guess");
		initial.setFont(new Font("Tahoma", Font.BOLD, 14));
		initial.setBounds(850, 109, 100, 26);
		frame2.getContentPane().add(initial);
		initial.setVisible(false);

		initialField = new JTextField();
		initialField.setHorizontalAlignment(SwingConstants.RIGHT);
		initialField.setText("0");
		initialField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		initialField.setBounds(850, 131, 120, 26);
		frame2.getContentPane().add(initialField);
		initialField.setVisible(false);

		lblX = new JLabel("X = ");
		lblX.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblX.setBounds(50, 217, 29, 26);
		frame2.getContentPane().add(lblX);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setBounds(77, 217, 198, 26);
		frame2.getContentPane().add(textField);

		lblIterations = new JLabel("Iterations");
		lblIterations.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblIterations.setBounds(368, 217, 88, 26);
		frame2.getContentPane().add(lblIterations);

		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField_1.setBounds(444, 217, 82, 26);
		frame2.getContentPane().add(textField_1);

		lblNewLabel2 = new JLabel("RunTime");
		lblNewLabel2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel2.setBounds(635, 210, 88, 45);
		frame2.getContentPane().add(lblNewLabel2);


		txtms2 = new JTextField();
		txtms2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtms2.setBounds(725, 217, 96, 29);
		frame2.getContentPane().add(txtms2);
		txtms2.setColumns(10);
	}

	private void setBoxes(int n) {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 11; j++) {
				if(i >= n || j >= n + 1) {
					coef[i][j].setVisible(false);
					coef[i][j].setText("0");
				}
				if(j < 10) {
					labels[i][j].setText("");
					labels[i][j].setVisible(false);
				}
			}
		}
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n + 1; j++) {
				coef[i][j].setVisible(true);
				if(j < n - 1)labels[i][j].setText("X" + String.valueOf(j + 1) + " + ");
				else if(j == n - 1) labels[i][j].setText("X" + String.valueOf(j + 1) + " = ");
				if(j < n) labels[i][j].setVisible(true);
			}
		}
		if(ErrorText.isVisible()) setInitialGuess(n);
	}
	//
	private void setInitialGuess(int n) {
		for(int i = 0; i < 10; i++) {
			if(i >= n ) {
				initialGuess[i].setVisible(false);
				initialGuess[i].setText("0");
			}
			initialGuess_Label[i].setText("");
			initialGuess_Label[i].setVisible(false);
			lblNewLabel_1.setVisible(false);
		}
		for(int i = 0; i < n; i++) {
			lblNewLabel_1.setVisible(true);
			initialGuess[i].setVisible(true);
			initialGuess_Label[i].setText("X" + String.valueOf(i + 1)+"=");
			initialGuess_Label[i].setVisible(true);

		}
	}
	//

	private void setMethod(String method, int n) {
		LUText.setVisible(false);
		LUComboBox.setVisible(false);
		IterationsNumberText.setVisible(false);
		IterationsNumberSpinner.setVisible(false);
		ErrorText.setVisible(false);
		ErrorTextField.setVisible(false);
		lblNewLabel_2.setVisible(false);
		iterationsDone.setVisible(false);
		setInitialGuess(0);
		if(method == "LU Decomposition") {
			LUText.setVisible(true);
			LUComboBox.setVisible(true);
		}
		else if(method == "Gauss Seidel" || method == "Jacobi Iteration") {
			setInitialGuess(n);
			IterationsNumberText.setVisible(true);
			IterationsNumberSpinner.setVisible(true);
			ErrorText.setVisible(true);
			ErrorTextField.setVisible(true);
			lblNewLabel_2.setVisible(true);
			iterationsDone.setVisible(true);
		}
	}

	private void setMethod2(String method) {
		try {
			lowerBound.setVisible(false);
			lowerBoundField.setVisible(false);
			upperBound.setVisible(false);
			upperBoundField.setVisible(false);
			Xi.setVisible(false);
			XiField.setVisible(false);
			Xii.setVisible(false);
			XiiField.setVisible(false);
			initial.setVisible(false);
			initialField.setVisible(false);
			if(method == "Bisection" || method == "False-Position") {
				lowerBound.setVisible(true);
				lowerBoundField.setVisible(true);
				upperBound.setVisible(true);
				upperBoundField.setVisible(true);
			}else if(method == "Secant") {
				Xi.setVisible(true);
				XiField.setVisible(true);
				Xii.setVisible(true);
				XiiField.setVisible(true);
			}else if(method == "Newton-Raphson" || method == "Fixed Point") {
				initial.setVisible(true);
				initialField.setVisible(true);
			}
		} catch (NullPointerException e) {
		}
	}

	//the function executed when the solve button is pressed
	private void solve(String method, int n, String LUType, boolean enableScaling) {
		if(method == "Gauss Seidel" || method == "Jacobi Iteration"){
			//getting initial guess inputs if the method is iterative
			for(int i=0 ; i<n ;i++) {
				iGuess[i]= Double.parseDouble(initialGuess[i].getText());
			}
			it = (int)IterationsNumberSpinner.getValue();   //number of iterations that the user chose
			er = Double.parseDouble(ErrorTextField.getText());    //absolute relative error that the user chose
		}
		double mat[][] = new double[n][n + 1];    //preparing the coefficients matrix to be used in different methods
		double specialMat[][] = new double[n][n];    //dividing the matrix to n * n coefficients and b vector
		double b[] = new double[n];    //b vector
		String validateMat[][] = new String[n][n + 1];    //used to validate that the matrix has no errors 
		int significantDigits = (int)PrecisionDigitsSpinner.getValue();    //getting the significant digits that the user chose
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n + 1; j++) {
				validateMat[i][j] = coef[i][j].getText();
			}
		}
		ValidateMatrix validateMatrix;
		if(method == "Gauss Seidel" || method == "Jacobi Iteration") {
			validateMatrix = new ValidateMatrix(validateMat,true);
		}else {
			validateMatrix = new ValidateMatrix(validateMat,false);
		}
		//ValidateMatrix validateMatrix = new ValidateMatrix(validateMat);
		validateMat = validateMatrix.validate();
		if(validateMat[0][0].equalsIgnoreCase("Error")) {
			Error errorWindow = new Error("Error");
			return;
		}
		if(validateMat[0][0].equalsIgnoreCase("Error1")) {
			Error errorWindow = new Error("Error1");
			return;
		}
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n + 1; j++) {
				mat[i][j] = Double.parseDouble(validateMat[i][j]);
				if(j != n) specialMat[i][j] = Double.parseDouble(validateMat[i][j]);
				if(j == n) b[i] = Double.parseDouble(validateMat[i][j]);
			}
		}
		Solve obj = new Solve(method, LUType, mat, specialMat, b, significantDigits,iGuess, it, er, enableScaling);
		t1 = System.currentTimeMillis();   //start timer
		double[] ans = obj.chooseMethod();
		t2 = System.currentTimeMillis() - t1;   //end timer
		if(method == "Gauss Seidel" || method == "Jacobi Iteration") {   //printing number of iterations done in iterative methods
			iterationsDone.setText(Integer.toString(obj.Iterations()));
		}
		new Solution(ans);   //viewing results in new window
		txtms.setText(Double.toString(t2)+"ms");   //showing the runtime
	}
	public void solve2(String method) {
		String expression = equationField.getText();
		double Xl = 0, Xu = 0, Xo = 0, Xi = 0;
		double error = Double.parseDouble(epsilonField.getText());
		int digits = (int)PrecisionDigitsSpinner2.getValue();
		int iterations = (int)IterationsNumberSpinner2.getValue();
		boolean converge = true;
		if(method == "Bisection" || method == "False-Position") {
			Xl = Double.parseDouble(lowerBoundField.getText());
			Xu = Double.parseDouble(upperBoundField.getText());
		}else if(method == "Fixed Point" || method == "Newton-Raphson") {
			Xo = Double.parseDouble(initialField.getText());
		}else {
			Xi = Double.parseDouble(XiiField.getText());
			Xo = Double.parseDouble(XiField.getText());
		}
		Solve2 obj = new Solve2(method, expression, digits, Xo, Xi, Xl, Xu, iterations, error);
		t1 = System.currentTimeMillis();   //start timer
		double ans = 0;
		try {
			ans = obj.chooseMethod();
		}catch (Exception e) {
			Error errorWindow = new Error("Error3");
		}
		converge  = obj.getConverge();
		t2 = System.currentTimeMillis() - t1;   //end timer
		// printing number of iterations done
		textField.setText(String.valueOf(ans));   //viewing results in new window
		textField_1.setText(Integer.toString(obj.Iterations()));    //showing number of iterations done
		txtms2.setText(String.valueOf(t2)+"ms");   //showing the runtime
		if(!converge) {
			Error errorWindow = new Error("Error2");
		}
		try {
			FileWriter myWriter = new FileWriter("D:\\Study\\Semester 3\\Numerical\\Project\\Submitted\\filename.txt");
			myWriter.write(method + "," + expression + "," + String.valueOf(Xl) + "," + String.valueOf(Xu));
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec("cmd.exe /c python ../work/pyth.py");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}