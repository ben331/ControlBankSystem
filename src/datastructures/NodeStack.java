package datastructures;

import java.io.Serializable;

public class NodeStack<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8077844194947967092L;
	private NodeStack<T> down;
	private T element;

	public NodeStack(T element) {
		this.element = element;
	}
	
	public T getElement() {
		return element;
	}

	public void setElement(T element) {
		this.element = element;
	}
	
	public NodeStack<T> getElemDown() {
		return down;
	}

	public void setElemDown(NodeStack<T> elemDown) {
		this.down = elemDown;
	}

}
