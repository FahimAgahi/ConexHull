package org.bihe.main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Checkbox;

import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import org.bihe.main.PointPanel;

public class mainPanel {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainPanel window = new mainPanel();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainPanel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 555, 429);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		panel.setBackground(new Color(191, 230, 244));
		// 184, 229, 245
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JRadioButton rdbtnBlindSearch = new JRadioButton("Blind Search ");
		rdbtnBlindSearch.setBounds(419, 32, 112, 23);
		panel.add(rdbtnBlindSearch);

		JRadioButton rdbtnGrahamsScan = new JRadioButton("Graham Scan");
		rdbtnGrahamsScan.setBounds(419, 102, 123, 23);
		panel.add(rdbtnGrahamsScan);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Quick Hull");
		rdbtnNewRadioButton.setBounds(419, 67, 99, 23);
		panel.add(rdbtnNewRadioButton);

		JButton btnApply = new JButton("Apply");
		btnApply.setBounds(429, 146, 102, 29);
		panel.add(btnApply);

		PointPanel panel_1 = new PointPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(16, 19, 391, 362);
		panel_1.addMouseListener(panel_1);
		panel.add(panel_1);

		JCheckBox chckbxShowX = new JCheckBox("Show X & Y");
		chckbxShowX.setBounds(419, 358, 102, 23);
		panel.add(chckbxShowX);
		chckbxShowX.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel_1.showXY = chckbxShowX.isSelected();
				panel_1.repaint();

			}
		});

		JLabel lblNewLabel = new JLabel("Click on the square above to draw a point");
		lblNewLabel.setForeground(new Color(119, 136, 153));
		lblNewLabel.setBounds(75, 385, 268, 16);
		panel.add(lblNewLabel);

	}

}