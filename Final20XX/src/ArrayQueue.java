import java.util.ArrayList;

public class ArrayQueue<T> {
	private ArrayList<T> queue;

	public ArrayQueue()
	{
		queue = new ArrayList<T>();
	}
	
	public void clear()
	{
		queue = new ArrayList<T>();
	}
	
	/**
	 * Enqueues a value at the end of the queue.
	 * @param i
	 * Value to enqueue.
	 */
	public void enqueue(T i) {
		//Enqueues at the end of the arraylist
		queue.add(i);

	}

	/**
	 * Takes a value from the start of the queue and removes it.
	 * @return Value at start of queue
	 */
	public T dequeue() {
		//Dequeues from start
		T value = queue.remove(0);
		return value;
	}

	/**
	 * Takes a value from start of the queue. Does not remove.
	 * @return Value at start of queue
	 */
	public T peek() {
		T value = queue.get(0);
		return value;
	}

	public int size() {
		return queue.size();
	}

	
	public boolean isEmpty() {
		if (queue.size() == 0)
		{
			return true;
		} //else
		return false;
	}

	public boolean isFull() {
		return false;
	}
	
	/**
	 * Checks how many occurrences there are of a given value.
	 * @param value
	 * The value/move to check how often it occurs in the queue
	 * @return Number of occurrences of the value.
	 */
	public int getOccurrences(T value)
	{
		int count = 0;
		for (int i = 0; i < queue.size(); i++)
		{
			T queueValue = queue.get(i);
			if (queueValue.getClass().equals(value.getClass())) //matches
				count++;
		}
		return count;
	}
	
	/**
	 * Checks for the last occurrence of a given value in the queue.
	 * @param value
	 * Value to compare for.
	 * @return The last/occurrence of value closest to end of the queue. Returns -1 if not in queue.
	 */
	public int getLastOccurrence(T value)
	{
		int count = -1;
		for (int i = 0; i < queue.size(); i++) //will go until the farthest back value in the queue
		{
			T queueValue = queue.get(i);
			if (queueValue.getClass().equals(value.getClass())) //matches
				count = i+1;
		}
		return count;
	}

}


