package datastructures;

public class NodeStack<T> {

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
