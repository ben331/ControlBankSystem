package comparatorClient;

import java.util.Comparator;

import model.Client;

public class CCComparator implements Comparator<Client> {

	public CCComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Client o1, Client o2) {
		return o1.getCC().compareTo(o2.getCC());
	}

}
