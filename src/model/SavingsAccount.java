package model;

public class SavingsAccount {
	
	private double balance;
	
	public SavingsAccount() {}
	
	public void consing(double value) throws Exception {
		if(value<=0) {
			throw new Exception("Can not consing a value smaller than 0");
		}else {
			balance+=value;
		}
	}
	
	public void withDraw(double value) throws Exception {
		if(value>balance || value <=0) {
			throw new Exception("Invalid input to withDraw: Non positive number or insufficient balance in account");
		}else {
			balance -= value;
		}
	}

	public double getBalance() {
		return balance;
	}
}
