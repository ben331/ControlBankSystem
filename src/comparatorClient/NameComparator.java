package comparatorClient;

import java.util.Comparator;

import model.Client;

public class NameComparator implements Comparator<Client> {

	public NameComparator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Client o1, Client o2) {
		return o1.getName().compareToIgnoreCase(o2.getName());
	}

}
