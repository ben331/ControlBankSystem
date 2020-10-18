package model;

import java.io.Serializable;

import customexception.InsufficientBalanceException;

public class SavingsAccount implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1133289246378335271L;
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
