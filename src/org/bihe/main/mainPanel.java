package org.bihe.main;

import java.awt.EventQueue;
import java.awt.Graphics2D;
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
import javax.swing.ButtonGroup;

public class mainPanel {

	private JFrame frmConvexhull;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainPanel window = new mainPanel();
					window.frmConvexhull.setVisible(true);
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
		frmConvexhull = new JFrame();
		frmConvexhull.setTitle("Convexhull");
		frmConvexhull.setResizable(false);
		frmConvexhull.setBounds(100, 100, 555, 429);
		frmConvexhull.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();

		panel.setBackground(new Color(191, 230, 244));
		// 184, 229, 245
		frmConvexhull.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JRadioButton rdbtnBlindSearch = new JRadioButton("Blind Search ");
		buttonGroup.add(rdbtnBlindSearch);
		rdbtnBlindSearch.setBounds(419, 32, 112, 23);
		rdbtnBlindSearch.setBackground(new Color(191, 230, 244));
		panel.add(rdbtnBlindSearch);

		JRadioButton rdbtnGrahamsScan = new JRadioButton("Graham Scan");
		buttonGroup.add(rdbtnGrahamsScan);
		rdbtnGrahamsScan.setBounds(419, 102, 123, 23);
		rdbtnGrahamsScan.setBackground(new Color(191, 230, 244));
		panel.add(rdbtnGrahamsScan);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("Quick Hull");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setBounds(419, 67, 99, 23);
		rdbtnNewRadioButton.setBackground(new Color(191, 230, 244));
		panel.add(rdbtnNewRadioButton);

		PointPanel panel_1 = new PointPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(16, 19, 391, 362);
		panel_1.addMouseListener(panel_1);
		panel.add(panel_1);

		JCheckBox chckbxShowX = new JCheckBox("Show X & Y");
		chckbxShowX.setBounds(419, 358, 102, 23);
		chckbxShowX.setBackground(new Color(191, 230, 244));
		panel.add(chckbxShowX);
		chckbxShowX.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				panel_1.showXY = chckbxShowX.isSelected();
				panel_1.repaint();

			}
		});
		
		//apply
		JButton btnApply = new JButton("Apply");
		btnApply.setBounds(429, 146, 102, 29);
		
		btnApply.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnBlindSearch.isSelected()) {
					panel_1.drawConvexWithBlindSearch();
				}else if(rdbtnGrahamsScan.isSelected()) {
					panel_1.drawConvexWithGrahamScan();
				}else if(rdbtnNewRadioButton.isSelected()) {
					//Quick hull
					panel_1.quikHull();
				}
				
			}
		});
		panel.add(btnApply);
		
		//clear
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(429, 190, 102, 29);
		
		btnClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel_1.clear();
				
			}
		});
		panel.add(btnClear);
		
		JLabel lblNewLabel = new JLabel("Click on the square above to draw a point");
		lblNewLabel.setForeground(new Color(119, 136, 153));
		lblNewLabel.setBounds(75, 383, 268, 16);
		panel.add(lblNewLabel);

	}

}