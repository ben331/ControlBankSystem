package model;

import java.io.Serializable;
import java.time.LocalDate;

import customexception.InsufficientBalanceException;

public class Client extends User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4818633155885007350L;
	private LocalDate incorporationDate;
	private CreditCard creditCard;
	private SavingsAccount account;

	public Client(String name, String CC) {
		super(name, CC);
		incorporationDate = LocalDate.now();
		account = new SavingsAccount();
		creditCard = new CreditCard();
	}
	
	public LocalDate getIncorporationDate() {
		return incorporationDate;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public SavingsAccount getAccount() {
		return account;
	}
	
	public double getBalance() {
		return account.getBalance();
	}

	public void payCreditCard(boolean inCash) throws InsufficientBalanceException {
		if(creditCard.getValue()==0) {
			throw new NumberFormatException("Credit card value is 0");
		}else{
			if(inCash) {
				creditCard.pay();
			}else {
				if(account.getBalance() < creditCard.getValue()) {
					throw new InsufficientBalanceException(account.getBalance(), creditCard.getValue());
				}else {
					account.withDraw(creditCard.getValue());
					creditCard.pay();
				}
			}
		}
	}	
}
