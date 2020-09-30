package datastructures;

public class NodoCola<T> {

	private T element;
	private NodoCola<T> next;
	
	public NodoCola(T element) {
		this.element = element;
	}
	
	public T getElement() {
		return element;
	}

	public void setElement(T element) {
		this.element = element;
	}

	public NodoCola<T> getNext() {
		return next;
	}

	public void setNext(NodoCola<T> next) {
		this.next = next;
	}

	
}
