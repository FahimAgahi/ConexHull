package org.bihe.main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JPanel;

public class PointPanel extends JPanel implements MouseListener {
	public boolean showXY = false;
	ArrayList<Point> points = new ArrayList<>();

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setPaint(Color.BLUE);

		for (Point point : points) {
			g2d.fillOval((int) point.getX(), (int) point.getY(), 5, 5);
			if (showXY == true) {
				g2d.drawString("(" + point.x + "," + point.y + ")", (point.x), (point.y) + 20);
			}
		}
	}

	public void drawConvexWithBlindSearch() {
		ArrayList<Point> innerPoints = new ArrayList<>();
		for (Point q : points) {
			for (Point p1 : points) {
				for (Point p2 : points) {
					for (Point p3 : points) {
						if (angleTurn(p1, p2, q) < 0 && angleTurn(p2, p3, q) < 0 && angleTurn(p3, p1, q) < 0) {
							innerPoints.add(q);
						}
					}
				}
			}

		}

		Graphics2D g2d = (Graphics2D) this.getGraphics();
		ArrayList<Point> convexPoints = new ArrayList();
		convexPoints.addAll(points);
		convexPoints.removeAll(innerPoints);
		
		g2d.setPaint(Color.RED);
		for (Point point : convexPoints) {
			g2d.fillOval((int) point.getX(), (int) point.getY(), 5, 5);
			if (showXY == true) {
				g2d.drawString("(" + point.x + "," + point.y + ")", (point.x), (point.y) + 20);
			}
		}
		// sort
		sortByAngles(convexPoints);
		drawConvexHull(convexPoints);

	}

	public void drawConvexHull(ArrayList<Point> extrems) {
		Graphics g = this.getGraphics();
		g.setColor(Color.RED);
		for (Point p : extrems) {
			g.fillOval(p.x - 5, p.y - 5 , 10, 10);
		}

		for (int i = 0; i < extrems.size() - 1; i++) {
			((Graphics2D) g).setStroke(new BasicStroke(3));
			Point p1 = extrems.get(i);
			Point p2 = extrems.get(i + 1);
			g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
		}
		Point p1 = extrems.get(extrems.size() - 1);
		Point p2 = extrems.get(0);
		g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
	}

	public double angleTurn(Point p1, Point p2, Point p3) {
		return (double) (p2.getX() - p1.getX()) * (p3.getY() - p1.getY())
				- (p3.getX() - p1.getX()) * (p2.getY() - p1.getY());

	}
	
	public void sortByAngles(ArrayList<Point> convexPoints) {
		Collections.sort(convexPoints, Comparator.comparing(Point :: getY).reversed().thenComparing(Point ::getX));
		
		Point p = (Point) convexPoints.get(0).clone();
		
		Collections.sort(convexPoints, Comparator.comparing(
				x -> Math.atan2(x.getY() - p.getY(), x.getX() - p.getX())));
	
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g);
	}
	public void clear() {
		this.points.clear();
		repaint();
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
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
