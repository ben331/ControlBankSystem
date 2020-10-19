package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import comparatorClient.*;
import customexception.FullStructureException;
import customexception.InsufficientBalanceException;
import datastructures.*;

public class Bank {
	
	public static final char SORT_BY_NAME = 'N';
	public static final char SORT_BY_DATE = 'D';
	public static final char SORT_BY_CC = 'C';
	public static final char SORT_BY_BALANCE = 'B';
	
	public static final String FILE_NAME_CLIENTS = "data/clients.srl";
	public static final String FILE_NAME_DESERTERS = "data/deserters.srl";
	private static final String FILE_RECORD = "data/record.srl";
	
	private char orderCriterion;
	
	private User currentUser;
	private User searchedClient;
	
	public User getCurrentUser() {
		return currentUser;
	}

	private boolean currentUserActive;
	
	private Queue<User> generalQueue;
	private Heap<Priority,User> priorityQueue;
	private HashTable<String, Client> clients;
	private HashTable<String, Client> deserters;
	private Stack<Operation> record;
	private ArrayList<Client> orderedClientsToShow;
	
	public Bank() {
		orderCriterion = Bank.SORT_BY_NAME;
		generalQueue = new Queue<>();
		priorityQueue = new Heap<>(20);
		clients = new HashTable<>(1009);
		deserters = new HashTable<>(499);
		record = new Stack<>();
	}
	
	public void registerIncome(String name, String CC, Priority priority) {
		User user = new User(name, CC, priority);
		if(priority==null) {
			generalQueue.enqueue(user);
		}else {
			priorityQueue.insert(priority, user);
		}
	}
	
	public void attendUser(boolean attendGeneral) {
		if(attendGeneral) {
			if(!generalQueue.isEmpty()) {
				currentUser = generalQueue.dequeue();
			}
		}else {
			if(!priorityQueue.isEmpty()) {
				currentUser= priorityQueue.extractMax();
			}
		}
		if(currentUser!=null) {
			Client client = clients.get(currentUser.getCC());
			currentUser = client!=null? client: currentUser;
		}
	}
	
	public void registerClient() throws FullStructureException {
		Client client = new Client(currentUser.getName(), currentUser.getCC());
		clients.put(currentUser.getCC(), client);
		currentUser=null;
	}
	
	public void searchClient(String CC) {
		searchedClient = clients.get(CC);
	}
	
	public void consingn( double value) {
		((Client)currentUser).getAccount().consing(value);
		Operation o = new Operation(Operation.CONSING, currentUser.getCC(), value);
		record.push(o);
	}
	
	public void withDraw(double value) throws InsufficientBalanceException {
		((Client)currentUser).getAccount().withDraw(value);
		Operation o = new Operation(Operation.WITHDRAW, currentUser.getCC(), value);
		record.push(o);
	}
	
	public void payCreditCard(boolean inCash) throws InsufficientBalanceException {
		LocalDate p = ((Client)currentUser).getCreditCard().getLastPaymentDate();
		Operation o = new Operation(Operation.PAY_CREDIT_CARD, currentUser.getCC(), inCash, p, ((Client) currentUser).getCreditCard().getValue());
		record.push(o);
		((Client)currentUser).payCreditCard(inCash);
	}
	
	public void cancelAccountOfClient() throws FullStructureException {		
		Client deserter =(Client) currentUser;
		deserters.put(deserter.getCC(), deserter);
		clients.delete(deserter.getCC());
		Operation o = new Operation(Operation.CANCEL, deserter.getCC());
		record.push(o);
		currentUser=null;
	}
	
private void sortByName() {
		
		refreshList();
		if(orderedClientsToShow != null) {
			mergeSort(orderedClientsToShow);
		}
		
	}

	public ArrayList<Client> mergeSort(ArrayList<Client> list){
		ArrayList<Client> left = new ArrayList<Client>();
		ArrayList<Client> right = new ArrayList<Client>();
		
		int medium;
		
		if(list.size() == 1) {
			return list;
		}else {
			medium = list.size()/2;
			for(int i = 0 ; i < medium; i++) {
				left.add(list.get(i));
			}
			
			for(int i = medium; i<list.size(); i++) {
				right.add(list.get(i));
			}
			left = mergeSort(left);
			right = mergeSort(right);
			
			stir(left, right, list);
		}
		
		return list;
	}
	
	private void stir(ArrayList<Client> left, ArrayList<Client> right, ArrayList<Client> list) {
		
		int leftIndex=0;
		int rightIndex = 0;
		int listIndex = 0;
		
		while(leftIndex < left.size() && rightIndex < right.size()) {
			if(new NameComparator().compare(left.get(leftIndex), right.get(rightIndex)) < 0) {
				list.set(listIndex, left.get(leftIndex));
				leftIndex++;
			}else {
				list.set(listIndex, right.get(rightIndex));
				rightIndex++;
			}
			listIndex++;
		}
		
		ArrayList<Client> temp;
		int tempIndex = 0;
		if(tempIndex >= left.size()) {
			temp = right;
			tempIndex = rightIndex;
		}else {
			temp = left;
			tempIndex = leftIndex;
		}
		
		for (int i = tempIndex; i < temp.size(); i++) {
			list.set(listIndex, temp.get(i));
			listIndex++;
		}
		
	}
	
	private void sortByCC() {
		refreshList();
		Collections.sort(orderedClientsToShow, new CCComparator());
	}
	
	private void sortByBalance() {
		refreshList();
		Collections.sort(orderedClientsToShow, new MoneyComparator());
	}
	
	private void sortByDate() {
		refreshList();
		Collections.sort(orderedClientsToShow, new TimeComparator());
	}
	
	public void sortDataBase() {
		if(orderCriterion==Bank.SORT_BY_BALANCE) {
			sortByBalance();
		}else if(orderCriterion==Bank.SORT_BY_DATE) {
			sortByDate();
		}else if(orderCriterion==Bank.SORT_BY_CC) {
			sortByCC();
		}else {
			sortByName();
		}
	}
	
	public void undo() throws FullStructureException, InsufficientBalanceException {
		Operation o = record.pop();
		if(o!=null) {
			if(o.getType()==Operation.CANCEL) {
				Client client = deserters.get(o.getCC());
				clients.put(o.getCC(), client);
				deserters.delete(o.getCC());
			}else if(o.getType()==Operation.CONSING) {
				Client client = clients.get(o.getCC());
				client.getAccount().withDraw(o.getValue());
			}else if(o.getType()==Operation.PAY_CREDIT_CARD) {
				Client client = clients.get(o.getCC());
				client.getCreditCard().setValue(o.getValue());
				client.getCreditCard().setLastPaymentDate(o.getPaymentCreditCard());
				if(!o.byCash()) {
					client.getAccount().consing(o.getValue());
				}
			}else {
				Client client = clients.get(o.getCC());
				client.getAccount().consing(o.getValue());
			}
		}
	}
	
	public void saveData() throws IOException {
		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(FILE_NAME_CLIENTS));
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(FILE_NAME_DESERTERS));
		ObjectOutputStream oos3 = new ObjectOutputStream(new FileOutputStream(FILE_RECORD));
    	oos1.writeObject(clients);
    	oos2.writeObject(deserters);
    	oos3.writeObject(record);
    	oos1.close();
    	oos2.close();
    	oos3.close();
	}
	
	@SuppressWarnings("unchecked")
	public void loadData() throws IOException, ClassNotFoundException {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME_CLIENTS));
			clients = (HashTable<String, Client>)(ois.readObject());
			ois.close();
			
			ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream(FILE_NAME_DESERTERS));
			deserters = (HashTable<String, Client>)(ois2.readObject());
			ois2.close();
			
			ObjectInputStream ois3 = new ObjectInputStream(new FileInputStream(FILE_RECORD));
			record = (Stack<Operation>)(ois3.readObject());
			ois3.close();
		} catch (FileNotFoundException e) {}
	}

	public boolean isCurrentUserActive() {
		return currentUserActive;
	}

	public void setCurrentUserActive(boolean currentUserActive) {
		this.currentUserActive = currentUserActive;
	}

	public char getOrderCriterion() {
		return orderCriterion;
	}

	public void setOrderCriterion(char orderCriterion) {
		this.orderCriterion = orderCriterion;
	}

	public User getSearchedClient() {
		return searchedClient;
	}

	public ArrayList<Client> getOrderedClientsToShow() {
		return orderedClientsToShow;
	}
	
	public ArrayList<User> getGeneral() {
		return generalQueue.toArrayList();
	}
	
	public ArrayList<User> getPriority() {
		return priorityQueue.toArrayList();
	}
	
	public void refreshList() {
		orderedClientsToShow = (ArrayList<Client>) clients.toList();
	}
}
