package datastructures;

public class NodeQueue<T> {

	private T element;
	private NodeQueue<T> back;
	
	public NodeQueue(T element) {
		this.element = element;
	}
	
	public T getElement() {
		return element;
	}

	public void setElement(T element) {
		this.element = element;
	}

	public NodeQueue<T> getBack() {
		return back;
	}

	public void setBack(NodeQueue<T> back) {
		this.back = back;
	}

	
}
