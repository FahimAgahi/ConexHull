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
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

import org.w3c.dom.Node;

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
			g.fillOval(p.x - 5, p.y - 5, 10, 10);
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
		Collections.sort(convexPoints, Comparator.comparing(Point::getY).reversed().thenComparing(Point::getX));

		Point p = (Point) convexPoints.get(0).clone();

		Collections.sort(convexPoints, Comparator.comparing(x -> Math.atan2(x.getY() - p.getY(), x.getX() - p.getX())));

	}

	public void drawConvexWithGrahamScan() {

		ArrayList<Point> clonedPoints = (ArrayList<Point>) points.clone();

		sortGrahamScan(clonedPoints);

		Point startPoint = (Point) clonedPoints.get(clonedPoints.size() - 1);

		clonedPoints.remove(clonedPoints.size() - 1);

		LinkedList<Point> linkedListPoints = new LinkedList<>();

		linkedListPoints.add(startPoint);

		int i = 0;
		boolean lastTimeRemoved = false;
		while (true) {
			
			if(i == clonedPoints.size())
				break;
			
			if(lastTimeRemoved == false)
				linkedListPoints.add(clonedPoints.get(i));

			if (linkedListPoints.size() >= 3) {

				int size = linkedListPoints.size();

				Point first = linkedListPoints.get(size - 3);
				Point center = linkedListPoints.get(size - 2);
				Point last = linkedListPoints.get(size - 1);

				double degree = angleTurn(first, last, center);

				if (degree < 0) {
					linkedListPoints.remove(center);
					lastTimeRemoved = true;
				} else {
					lastTimeRemoved = false;
					i++;
				}
			}
		}

		ArrayList<Point> p = new ArrayList<>();
		p.addAll(linkedListPoints);
		drawConvexHull(p);
		
	}
	
	public void quikHull() {
		
		//sort
		ArrayList<Point> points= (ArrayList<Point>) this.points.clone();
		Collections.sort(points, Comparator.comparing(Point::getX));
		ArrayList<Point> result = new ArrayList<>();
		ArrayList<Point> S1 = new ArrayList<>();
		ArrayList<Point> S2 = new ArrayList<>();

		Point min = points.get(0);
		Point max = points.get(points.size()-1);
		points.remove(min);
		points.remove(max);

		for(Point p : points) {
			if(isRight(min, max, p)) {
				S1.add(p);
			}else {
				S2.add(p);
			}
		}

		result.add(min);
		result.addAll(findHull(min, max, S1));
		result.add(max);
		result.addAll(findHull(max, min, S2));
		
		//draw lines
		drawConvexHull(result);
	}
	
	public boolean isRight(Point a, Point b, Point c){
	     return ((b.x - a.x)*(c.y - a.y) - (b.y - a.y)*(c.x - a.x)) > 0;
	}
	
	//find hull
	private ArrayList<Point>  findHull (Point a, Point b, ArrayList<Point> points) {
		ArrayList<Point> result = new ArrayList<>();
		ArrayList<Point> S1 = new ArrayList<>();
		ArrayList<Point> S2 = new ArrayList<>();
		
		if(points.size() == 0) {
			return result;
		}
		
		//find center of ab line
		int furthestid=0;
	    double furthestdis=0;
		
	    //calculate distance
		for(int c=0;c<points.size();c++){
	        if(calculate_distance(a.x,a.y,b.x,b.y,points.get(c).x,points.get(c).y)>furthestdis){
	            furthestid = c;
	            furthestdis = calculate_distance(a.x,a.y,b.x,b.y,points.get(c).x,points.get(c).y);
	        }
	    }
		Point c = points.get(furthestid);
		points.remove(c);
		//inside points
		points.removeIf(p -> isInside(p.x, p.y, a.x, a.y, b.x, b.y, c.x, c.y));

		for(Point p : points) {
			if(isRight(a, c, p)) {
				S1.add(p);
			}else {
				S2.add(p);
			}
		}
		
		result.addAll(findHull(a, c, S1));
		result.add(c);
		result.addAll(findHull(c, b, S2));
		return result;
	}
	
	public static double calculate_distance (int x1,int y1,int x2 ,int y2, int x0 ,int y0){
        return Math.abs((y2-y1) * x0 - (x2-x1) * y0 + x2*y1 - y2*x1) / Math.sqrt(Math.pow(y2-y1, 2) + Math.pow(x2-x1, 2));
	}
	
	public boolean isInside( int x, int y, int x1, int y1, int x2, int y2, int x3, int y3 ) {
		  float p1 = (x-x1)*(y3-y1) - (x3-x1)*(y-y1), 
		    p2 = (x-x2)*(y1-y2) - (x1-x2)*(y-y2), 
		    p3 = (x-x3)*(y2-y3) - (x2-x3)*(y-y3);
		  return (p1>0 && p2>0  && p3>0) || (p1<0 && p2<0 && p3<0);
	}
	
	private void sortGrahamScan(ArrayList<Point> points) {

		Collections.sort(points, Comparator.comparing(Point::getY).reversed().thenComparing(Point::getX));

		Point startPoint = (Point) points.get(0).clone();

		points.remove(0);

		Collections.sort(points, Comparator.comparing(
				x -> Math.toDegrees(Math.atan2(x.getY() - startPoint.getY(), x.getX() - startPoint.getX()))));

		Collections.reverse(points);

		points.add(startPoint);

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
