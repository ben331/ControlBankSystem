package model;

public class Operation {
	
	public static final char CONSING = 'G';
	public static final char WITHDRAW = 'W';
	public static final char PAY_CREDIT_CARD = 'P';
	public static final char CANCEL = 'C';
	
	private char type;
	private String CC;
	private double value;
	
	public Operation(char type, String cC, double value) {
		super();
		this.type = type;
		CC = cC;
		this.value = value;
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
}