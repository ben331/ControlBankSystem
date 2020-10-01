package comparatorClient;

import java.util.Comparator;

import model.Client;

public class MoneyComparator implements Comparator<Client> {

	public MoneyComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Client o1, Client o2) {
		if(o1.getAccount().getBalance() < o2.getAccount().getBalance()) {
			return -1;
		}else if(o1.getAccount().getBalance() > o2.getAccount().getBalance()){
			return 1;
		}else {
			return 0;
		}
	}

}