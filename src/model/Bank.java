package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import customexception.FullStructureException;
import customexception.InsufficientBalanceException;
import datastructures.*;

public class Bank {
	
	public static final char SORT_BY_NAME_CC = 'N';
	public static final char SORT_BY_DATE_NAME = 'D';
	public static final char SORT_BY_PAYMENTDATE = 'P';
	public static final char SORT_BY_BALANCE = 'B';
	
	public static final String FILE_NAME_CLIENTS = "data/clients.srl";
	public static final String FILE_NAME_DESERTERS = "data/deserters.srl";
	
	private char orderCriterion;
	
	private User currentUser;
	private User searchedClient;
	
	private boolean currentUserActive;
	
	private Queue<User> generalQueue;
	private Heap<Priority,User> priorityQueue;
	private HashTable<String, Client> clients;
	private HashTable<String, Client> deserters;
	private Stack<Operation> record;
	private ArrayList<Tuple<? extends Comparable<?>, ?>> orderedClientsToShow;
	
	public Bank() {
		orderCriterion = Bank.SORT_BY_NAME_CC;
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
		User user=null;
		if(attendGeneral) {
			if(!generalQueue.isEmpty()) {
				user = generalQueue.dequeue();
			}
		}else {
			if(!priorityQueue.isEmpty()) {
				user = priorityQueue.extractMax();
			}
		}
		Client client = clients.get(user.getCC());
		user = client!=null? client: user;
	}
	
	public void registerClient() throws FullStructureException {
		Client client = new Client(currentUser.getName(), currentUser.getCC());
		clients.put(currentUser.getCC(), client);
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
		((Client)currentUser).payCreditCard(inCash);;
		Operation o = new Operation(Operation.PAY_CREDIT_CARD, currentUser.getCC(), inCash, p);
		record.push(o);
	}
	
	public void cancelAccountOfClient(String CC) throws FullStructureException {		
		Client deserter = clients.get(CC);
		deserters.put(CC, deserter);
		clients.delete(CC);
		Operation o = new Operation(Operation.CANCEL, CC);
		record.push(o);
	}
	
	private void sortByDateAndName() {
		
	}
	
	private void sortByNameAndCC() {
		
	}
	
	private void sortByBalance() {
		
	}
	
	private void sortByLastPaymentCreditC() {
		
	}
	
	public void sortDataBase() {
		if(orderCriterion==Bank.SORT_BY_BALANCE) {
			sortByBalance();
		}else if(orderCriterion==Bank.SORT_BY_DATE_NAME) {
			sortByDateAndName();
		}else if(orderCriterion==Bank.SORT_BY_PAYMENTDATE) {
			sortByLastPaymentCreditC();
		}else {
			sortByNameAndCC();
		}
	}
	
	public void undo() throws FullStructureException, InsufficientBalanceException {
		Operation o = record.pop();
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
	
	public void saveData() throws IOException {
		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(FILE_NAME_CLIENTS));
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(FILE_NAME_DESERTERS));
    	oos1.writeObject(clients);
    	oos2.writeObject(deserters);
    	oos1.close();
    	oos2.close();
	}
	
	@SuppressWarnings("unchecked")
	public void loadData() throws IOException, ClassNotFoundException {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME_CLIENTS));
			clients = (HashTable<String, Client>)(ois.readObject());
			ois.close();
			
			ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream(FILE_NAME_DESERTERS));
			clients = (HashTable<String, Client>)(ois2.readObject());
			ois2.close();
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

	public ArrayList<Tuple<? extends Comparable<?>, ?>> getOrderedClientsToShow() {
		return orderedClientsToShow;
	}
	
	public void refreshList() {
		orderedClientsToShow = clients.toList();
	}
}
