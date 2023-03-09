package org.example;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Error {
	private JFrame frame;
	private JLabel label1, label2;
	private String Error;

	/**
	 * Create the frame.
	 */
	public Error(String Error) {
		this.Error = Error;
		initialize();
	}
	
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(193, 255, 255));
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.setBounds(500, 300, 400, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		Button button = new Button("OK");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		
		button.setBackground(new Color(0, 128, 255));
		button.setFont(new Font("Dialog", Font.BOLD, 20));
		button.setBounds(170, 100, 60, 30);
		if(this.Error.equalsIgnoreCase("Error")) {
			label1 = new JLabel("An error has occured on parsing the equations");
			label2 = new JLabel("please recheck them and try again");
			label1.setBounds(30, 20, 350, 42);
		}else if(this.Error.equalsIgnoreCase("Error1")) {
			label1 = new JLabel("The   System   has   no   solution");
			label2 = new JLabel("please recheck them and try again");
			label1.setBounds(70, 20, 350, 42);
		}else if(this.Error.equalsIgnoreCase("Error2")){
			label1 = new JLabel("The   solution   didn't converge");
			label2 = new JLabel("please recheck the initial conditions");
			label1.setBounds(70, 20, 350, 42);
		}else {
			label1 = new JLabel("An error has occured");
			label2 = new JLabel("please recheck the inputs");
			label1.setBounds(70, 20, 350, 42);
		}
		//label1 = new JLabel("An error has occured on parsing the equations");
		label1.setBackground(new Color(0, 128, 255));
		label1.setFont(new Font("Dialog", Font.BOLD, 14));
		//label1.setBounds(30, 20, 350, 42);
		
		//label2 = new JLabel("please recheck them and try again");
		label2.setBackground(new Color(0, 128, 255));
		label2.setFont(new Font("Dialog", Font.BOLD, 14));
		label2.setBounds(70, 40, 300, 42); 
		
		frame.getContentPane().add(button);
		frame.getContentPane().add(label1);
		frame.getContentPane().add(label2);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
