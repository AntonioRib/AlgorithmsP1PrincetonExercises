package Week2RandomizedQueue;

public interface RandomizedQueueAPI<Item> extends Iterable<Item> {
	
	// is the randomized queue empty?
    public boolean isEmpty();

    // return the number of items on the randomized queue
    public int size();

    // add the item
    public void enqueue(Item item);

    // remove and return a random item
    public Item dequeue();

    // return a random item (but do not remove it)
    public Item sample();
}
