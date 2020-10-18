package datastructures;

import java.util.ArrayList;

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
			element = first.getElement();
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
	
	public ArrayList<T> toArrayList(){
		ArrayList<T> list = new ArrayList<>();
		NodeQueue<T> current= first;
		for(int i=0; i<size; i++) {
			list.add(current.getElement());
			current = current.getBack();
		}
		return list;
	}
}
