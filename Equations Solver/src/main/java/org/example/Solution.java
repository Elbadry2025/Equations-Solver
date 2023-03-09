package org.example;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Solution {

	private JFrame frame;
	private double[] ans = {0};
	private JLabel[] results = new JLabel[10];
	private int n;

	/**
	 * Create the frame.
	 */
	public Solution(double[] ans) {
		this.n = ans.length;
		this.ans = ans;
		initialize();
	}
	
	private void initialize() {
		int x = 25, y = 65, width = 300, height = 35;
		int x2 = 25, y2 = 105;
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(193, 255, 255));
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.setBounds(75, 200, 1400, 300);
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
		button.setBounds(625, 192, 128, 42);
		frame.getContentPane().add(button);
		frame.setVisible(true);
		
		for(int i = 0; i < n; i++) {
			String str = "X" + String.valueOf(i + 1) + " = " + String.valueOf(ans[i]);
			results[i] = new JLabel(str);
			results[i].setFont(new Font("Tahoma", Font.PLAIN, 15));
			if(i>4) {
				results[i].setBounds(x2, y2, width, height);
				x2+= 300;
			}else {
				results[i].setBounds(x, y, width, height);
			}
			frame.getContentPane().add(results[i]);
			x += 300; 
		}
	}

}
