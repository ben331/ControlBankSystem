package datastructures;

public class NodoStack<T> {

	NodoStack<T> elemDown;
	NodoStack<T> elemCurrent;
	T element;

	public NodoStack(T element) {
		this.element = element;
	}
	
	public T getElement() {
		return element;
	}

	public void setElement(T element) {
		this.element = element;
	}
	
	public NodoStack<T> getElemDown() {
		return elemDown;
	}

	public void setElemDown(NodoStack<T> elemDown) {
		this.elemDown = elemDown;
	}

}
