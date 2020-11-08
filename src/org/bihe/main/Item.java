package org.bihe.main;

import java.awt.Point;

public class Item {

	public Point key;
	public Point data;

	public Item() {
		// key=0;
		// data=0;
	}

	public Item(Point key, Point data) {
		this.key = key;
		this.data = data;
	}

	public String toString() {
		return key + " -> " + data;
	}
}
