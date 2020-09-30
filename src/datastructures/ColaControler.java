package datastructures;

public class ColaControler<T> {

	private NodoCola<T> first, last;

	public ColaControler() {
		
	}

	public boolean empty() {
		
		if(first.getElement() == null) {
			return true;
		}else {
			return false;
		}
	}
	
	public void insert(T element) {
		
		NodoCola<T> nodoAdd = new NodoCola<T>(element);
		
		if(empty()) {
			first = nodoAdd;
			last = nodoAdd;
		}else {
			
			first.setNext(nodoAdd);
			first = nodoAdd;
			
		}
		
	}
	
	public NodoCola<T> extract(){
		
		if(!empty()) {
			NodoCola<T> information= last;
			if(last == first) {
				last = null;
				first = null;
			}
			else {
				last = last.getNext();
			}
			
			return information;
		}else {
			return null;
		}
		
	}
	
	public NodoCola<T> getFirst() {
		return first;
	}

	public NodoCola<T> getLast() {
		return last;
	}
	
	
}
