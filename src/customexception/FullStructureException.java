package customexception;

@SuppressWarnings("serial")
public class FullStructureException extends Exception{
	
	private int maxSize;
	
	public FullStructureException(int maxSize) {
		this.maxSize = maxSize;
	}
	
	@Override
	public String getMessage() {
		return "Can not add more elements. Structure is full. Current size: "+maxSize;
	}
}
