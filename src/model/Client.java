package model;

import java.time.LocalDate;

public class Client extends User{
	
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

	public void payCreditCard(boolean withCash) throws Exception {
		if(creditCard.getValue()==0) {
			throw new Exception("Credit card value is 0");
		}else{
			if(withCash) {
				creditCard.pay();
			}else {
				if(account.getBalance() < creditCard.getValue()) {
					throw new Exception("Balance in account is not enough to pay credit card");
				}else {
					account.withDraw(creditCard.getValue());
					creditCard.pay();
				}
			}
		}
	}	
}
