package datastructures;

public class Queue<T> implements IQueue<T>{
	
	private int size;

	private NodeQueue<T> first, last;

	public Queue() {}

	public boolean isEmpty() {
		return first==null;
	}
	
	public void enqueue(T element) {
		
		NodeQueue<T> nodeAdd = new NodeQueue<T>(element);
		
		if(isEmpty()) {
			first = last = nodeAdd;
		}else {		
			last.setBack(nodeAdd);
			last = nodeAdd;
		}
		size++;
	}
	
	public T dequeue(){
		T element = null;
		
		if(!isEmpty()) {
			element = last.getElement();
			if(last == first) {
				last = first = null;
			}
			else {
				first = first.getBack();
			}
			size--;
		}
		return element;
	}
	
	public NodeQueue<T> getFirst() {
		return first;
	}

	public NodeQueue<T> getLast() {
		return last;
	}

	public int getSize() {
		return size;
	}
}
