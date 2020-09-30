package datastructures;

public interface IStack<T> {
	
	public boolean isEmpty();
	public void push(T element);
	public T peek();
	public T pop();
}
