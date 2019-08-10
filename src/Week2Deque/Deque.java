package Week2Deque;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private Node first, last;
	private int size;
	
	private class Node {
		Item value;
		Node next;
		Node previous;
	}
	
    // construct an empty deque
    public Deque() {
    	first = last = null;
    	size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
    	return size == 0;
    }

    // return the number of items on the deque
    public int size() {
    	return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
    	if (item == null)
    		throw new IllegalArgumentException();
		Node oldFirst = this.first;
		first = new Node();
		first.next = oldFirst;
		first.value = item;
		first.previous = null;
		if (size > 0)
			oldFirst.previous = first;
		else
			last = first;
		size++;
    }

    // add the item to the back
    public void addLast(Item item) {
    	if (item == null)
    		throw new IllegalArgumentException();
    	Node oldLast = last;
		last = new Node();
		last.previous = oldLast;
		last.value = item;
		last.next = null;
		if (size > 0) 
			oldLast.next = last;
        else 
        	first = last;
		size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
    	if (this.isEmpty())
    		throw new NoSuchElementException();
		Item item = first.value;
		if (size > 1) {
			this.first = this.first.next;
			this.first.previous = null;
		} else {
			first = null;
	        last = null;
		}

		size--;
		return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
    	if (this.isEmpty())
    		throw new NoSuchElementException();
		Item item = last.value;
		if (size > 1) {
			this.last = this.last.previous;
			this.last.next = null;
		} else {
			first = null;
	        last = null;
		}
		size--;
		return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
    	return new ListIterator();
    }
	
	private class ListIterator implements Iterator<Item> {
		private Node current;
		
		public ListIterator() {
			current = first;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		@Override
		public Item next() {
	        if (!this.hasNext())
	            throw new NoSuchElementException();
	        Item item = current.value;
	        current = current.next;
	        return item;
	        
		}
	}
	
	public static void main(String[] args) {
		//Empty
	}

}
