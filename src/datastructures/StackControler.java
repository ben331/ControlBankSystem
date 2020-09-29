/**
 * 
 */
package datastructures;


public class StackControler<T> {

	NodoStack<T>  last;
	
	public StackControler () {
		last = new NodoStack<T>(null);
	}
	
	public boolean empty() {
		
		if(last.getElement() == null) {	
			return true;
		}else {
			return false;
		}
		
	}
	public void push(T element) {
		
		if(last.getElement() == null) {

			last.setElement(element);
			last.setElemDown(null);
			
		}
		else {
			
			NodoStack<T> elemAdd = new NodoStack<T>(element);
			last.setElemDown(last);
			last = elemAdd;
		}
		
	}
	public T peek() {
		
		return last.getElement();
		
	}
	public T pop() {

		T temp = last.getElement();
		last= last.getElemDown();
		return temp;
		
	}
	
}
