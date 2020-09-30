package model;

import java.util.ArrayList;

import datastructures.*;

public class Bank {
	
	public static final char SORT_BY_NAME_CC = 'N';
	public static final char SORT_BY_DATE_NAME = 'D';
	public static final char SORT_BY_PAYMENTDATE = 'P';
	public static final char SORT_BY_BALANCE = 'B';
	
	private char orderCriterion;
	
	private User currentUser;
	private User searchedUser;
	
	private Queue<User> generalQueue;
	private Heap<Priority,User> priorityQueue;
	private HashTable<String, Client> clients;
	private HashTable<String, Client> deserters;
	private Stack<Operation> record;
	
	public Bank() {
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
	
	public void attendUser(boolean attendGeneral) throws Exception {
		User user;
		if(attendGeneral) {
			if(generalQueue.isEmpty()) {
				throw new Exception("General queue is empty");
			}else {
				user = generalQueue.dequeue();
			}
		}else {
			if(priorityQueue.isEmpty()) {
				throw new Exception("General queue is empty");
			}else {
				user = priorityQueue.extractMax();
			}
		}
		Client client = searchClient(user.getCC());
		user = client!=null? client: user;
	}
	
	public void registerClient() {
		
		Client helperAdd = new Client(currentUser.getName(), currentUser.getCC());
		clients.put(currentUser.getCC(), helperAdd);
		
	}
	
	public Client searchClient(String CC) {
		
		Client clientReturn = clients.get(CC);
		
		if(clientReturn == null) {
			return null;
		}else {
			return clientReturn;
		}
		
		
	}
	
	public void cancelAccountOfClient(String CC) {
		
		Client clientCancelAccount = clients.get(CC);
		clients.delete(CC);
		deserters.put(CC, clientCancelAccount);
		
	}
	
	private void sortByDateAndName() {
		
	}
	
	private void sortByNameAndCC() {
		
	}
	
	private void sortByBalance() {
		
	}
	
	private void sortByLastPaymentCreditC() {
		
	}
	
	public void sortDataBase(char criterion) {
		
	}
	
	public void undo() {
		
	}
	
	public void saveData() {
		
	}
	
	public void loadData() {
		
	}
}
