package Week2RandomizedQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>  {

	private static final int DEFAULT_CAPACITY = 20;

	private Item[] queue;
	private int size;
	
	
	// construct an empty randomized queue
    public RandomizedQueue() {
    	this(DEFAULT_CAPACITY);
    }

	// construct an empty randomized queue
	private RandomizedQueue(int capacity) {
		this.queue = (Item[]) new Object[capacity];
		this.size = 0;
    }
    
    // is the randomized queue empty?
    public boolean isEmpty() {
    	return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
    	return size;
    }

    // add the item
    public void enqueue(Item item) {
    	if (item == null)
    		throw new IllegalArgumentException();
		if (size == queue.length)
			resize(2 * queue.length);
		queue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
    	if (isEmpty())
    		throw new NoSuchElementException();

		int i = StdRandom.uniform(0, size);
		Item item = queue[i];
		queue[i] = queue[size-1];
		queue[size-1] = null;
		size--;
		
		if (size > 0 && size == queue.length/4)
			resize(queue.length/2);
		
		return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
    	if (isEmpty())
    		throw new NoSuchElementException();
    	
		return queue[StdRandom.uniform(0, size)];
    }

    @Override
    public Iterator<Item> iterator() {
    	return new RandomArrayIterator();
    }

	private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = queue[i];
        }
        queue = temp;
	}
	
	private class RandomArrayIterator implements Iterator<Item> {
		private final Item[] array;
		private int i;
		
		public RandomArrayIterator() {
            array = (Item[]) new Object[size];
            for (int x = 0; x < size; x++) {
                array[x] = queue[x];
            }
		}

		@Override
		public boolean hasNext() {
			return i < size;
		}

        public void remove() {
            throw new UnsupportedOperationException();
        }
		
		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return array[i++];
		}
	}	
	
	public static void main(String[] args) {
		//Empty
	}

}
