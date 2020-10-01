package comparatorClient;

import java.util.Comparator;

import model.Client;

public class TimeComparator implements Comparator<Client> {

	public TimeComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Client o1, Client o2) {
		return o1.getIncorporationDate().compareTo(o2.getIncorporationDate());
	}

}