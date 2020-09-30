package datastructures;

public interface IQueue<T> {
	public boolean isEmpty();
	public void enqueue(T element);
	public T dequeue();
	public int getSize();
}
