package org.bihe.main;

import java.awt.Point;

public class LinkedList {

	private int size = 0;

	static class Node {
		Item item;
		Node next;
		Node previous;

		public Node(Point key, Point data) {
			this.item = new Item(key, data);
			next = null;
			previous = null;
		}
	}

	public Node head = null;
	public Node tail = null;

	public boolean insert(Point key, Point data) {
		Node node = new Node(key, data);
		node.next = null;
		if (head == null) {
			head = node;
		} else {
			Node last = head;
			while (last.next != null) {
				if (last.item.key.equals(key))
					return false;// unique key instead of search
				last = last.next;
			}
			node.previous = last;
			last.next = node;
		}
		tail = node;
		size++;
		return true;
	}

	public boolean delete(Point key) {
		if (head == null)
			return false;
		if (head.item.key.equals(key)) {
			if (head.equals(tail)) {
				tail = head.next;
				tail.previous = null;
			}
			head = head.next;
			head.previous = null;
			size--;
			return true;
		}
		Node prev = head;
		Node pointer = head.next;
		while (pointer != null) {
			if (pointer.item.key.equals(key)) {

				prev.next = pointer.next;
				prev.next.previous = prev;
				if (tail.equals(pointer)) {
					tail = pointer.next;
				}
				size--;
				return true;
			}
			prev = pointer;
			pointer = pointer.next;
		}
		return false;
	}

	public Point search(Point key) {
		Node pointer = head;
		while (pointer != null) {
			if (pointer.item.key.equals(key)) {
				return pointer.item.data;
			}
			pointer = pointer.next;
		}
		return null;
	}

	public int size() {

		return size;
	}

	public void print() {
		Node pointer = head;
		while (pointer != null) {
			System.out.print(pointer.item + " ");
			pointer = pointer.next;
		}
		System.out.println();
	}

}
