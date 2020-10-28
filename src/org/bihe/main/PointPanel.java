package org.bihe.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class PointPanel extends JPanel implements MouseListener {

	ArrayList<Point> points = new ArrayList<>();

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setPaint(Color.BLUE);

//		int w = getWidth();
//		int h = getHeight();
//
//		Random r = new Random();
//
//		for (int i = 0; i < 2000; i++) {
//
//			int x = Math.abs(r.nextInt()) % w;
//			int y = Math.abs(r.nextInt()) % h;
//			g2d.drawLine(x, y, x, y);
//			
//		}
//		
//		

		for (Point point : points) {
			g2d.fillOval((int)point.getX(), (int)point.getY(), 5, 5);
		}
		
		
		
		
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		points.add(e.getPoint());
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
