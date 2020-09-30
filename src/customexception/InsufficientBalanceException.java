package customexception;

@SuppressWarnings("serial")
public class InsufficientBalanceException extends Exception{
	private double balance;
	private double attempToSpend;
	
	public InsufficientBalanceException(double b, double a) {
		balance = b;
		setAttempToSpend(a);
	}

	public double getAttempToSpend() {
		return attempToSpend;
	}

	public void setAttempToSpend(double attempToSpend) {
		this.attempToSpend = attempToSpend;
	}
	
	public String getMessage() {
		return "Insufficent Balance to pay or withDraw. Balance: "+balance+". Attemp to Spend: "+attempToSpend;
	}
}
