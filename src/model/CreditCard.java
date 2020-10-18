package model;

import java.io.Serializable;
import java.time.LocalDate;

public class CreditCard implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4698497029506554993L;
	private double value;
	private LocalDate lastPaymentDate;
	
	public CreditCard(double value) {
		super();
		this.value = value;
	}
	
	public CreditCard() {
		value = Math.random()*5000000;
	}
	
	public void setValue(double value) {
		this.value = value;
	}

	public void setLastPaymentDate(LocalDate lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
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
