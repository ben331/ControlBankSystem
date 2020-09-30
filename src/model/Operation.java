package model;

import java.time.LocalDate;

public class Operation {
	
	public static final char CONSING = 'G';
	public static final char WITHDRAW = 'W';
	public static final char PAY_CREDIT_CARD = 'P';
	public static final char CANCEL = 'C';
	
	private char type;
	private String CC;
	private double value;
	private boolean cash;
	private LocalDate paymentCreditCard;
	
	public Operation(char type, String CC, boolean cash, LocalDate p) {
		super();
		this.type = type;
		this.CC = CC;
		this.cash = cash;
		paymentCreditCard = p;
	}
	
	public Operation(char type, String CC, double value) {
		super();
		this.type = type;
		this.CC = CC;
		this.value = value;
	}
	
	public Operation(char type, String CC) {
		super();
		this.type = type;
		this.CC = CC;
	}
	
	public char getType() {
		return type;
	}
	public String getCC() {
		return CC;
	}
	public double getValue() {
		return value;
	}

	public boolean byCash() {
		return cash;
	}

	public LocalDate getPaymentCreditCard() {
		return paymentCreditCard;
	}
}