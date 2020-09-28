package model;

import java.time.LocalDate;

public class CreditCard {
	private double value;
	private LocalDate lastPaymentDate;
	
	public CreditCard(double value) {
		super();
		this.value = value;
	}
	
	public CreditCard() {
		value = Math.random()*5000000;
	}
	
	public void pay() {
		value=0;
		lastPaymentDate=LocalDate.now();
	}

	public double getValue() {
		return value;
	}

	public LocalDate getLastPaymentDate() {
		return lastPaymentDate;
	}
}
