package datastructures;


public class Stack<T> implements IStack<T> {

	private NodeStack<T>  top;

	public Stack () {}
	
	public boolean isEmpty() {
		return top==null;
	}
	public void push(T element) {
		
		NodeStack<T> nodeAdd= new NodeStack<>(element);
		
		if(top == null) {
			top = nodeAdd;
		}
		else {
			nodeAdd.setElemDown(top);
			top = nodeAdd;
		}
	}
	
	public T peek() {
		return top.getElement();
	}
	
	public T pop() {

		T temp = top.getElement();
		top = top.getElemDown();
		return temp;
	}
	
	public NodeStack<T> getLast() {
		return top;
	}
}
