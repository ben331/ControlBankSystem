package model;

import customexception.InsufficientBalanceException;

public class SavingsAccount {
	
	private double balance;
	
	public SavingsAccount() {}
	
	public void consing(double value){
		if(value<=0) {
			throw new NumberFormatException("Can not consing a value smaller than 0");
		}else {
			balance+=value;
		}
	}
	
	public void withDraw(double value) throws InsufficientBalanceException {
		if(value <=0) {
			throw new NumberFormatException("Invalid input to withDraw: Non positive number");
		}else if(value>balance){
			throw new InsufficientBalanceException(balance, value);
		}else {
			balance -= value;
		}
	}

	public double getBalance() {
		return balance;
	}
}
