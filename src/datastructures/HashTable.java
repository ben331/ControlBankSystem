package datastructures;

import java.io.Serializable;
import java.util.ArrayList;

import customexception.FullStructureException;

public class HashTable<K extends Comparable<K>,V> implements IHashTable<K,V>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 57815965684572420L;
	private Tuple<? extends Comparable<?>,?>[] table;
	private int m;
	private int size;
	
	public HashTable(int m) {
		this.m = m;
		table = new Tuple<?,?>[m];
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int hashToSearch(K key, int explorator) {
		
		int hash = auxHash(key);
		Tuple<K,V> current = (Tuple<K, V>) table[hash];
		
		//Search a space with the key searched until find it or find a null space.
		while(current!=null  && ((current.getKey()==null || current.getKey().compareTo(key)!=0) && explorator<m-1)) {
			explorator++;
			current = (Tuple<K, V>) table[(hash + explorator)%m];
		}
		
		if((current!=null && explorator<m-1) && current.getKey()!=null) {
			return hash + explorator;
		}else {
			return -1;
		}
	}
	
	@SuppressWarnings("unchecked")
	public int hashToInsert(K key, int explorator) throws FullStructureException {
		
		int hash = auxHash(key);
		Tuple<K,V> current = (Tuple<K, V>) table[hash];
		
		//Search a null space or a deleted space (key == null)
		while((current!=null  && current.getKey()!=null) && explorator < m-1) {
			explorator++;
			current = (Tuple<K, V>) table[(hash + explorator)%m];
		}
		
		//If was founded
		if(explorator < m-1) {
			return hash + explorator;
		}else {
			throw new FullStructureException(size);
		}
	}
	
	@Override
	public int auxHash(K key) {
		return keyToInteger(key)%m;
	}

	@Override
	public void put(K key, V value) throws FullStructureException {
		Tuple<K,V> tuple = new Tuple<K,V>(key, value);
		int hash = hashToInsert(tuple.getKey(), 0);
		table[hash]=tuple;
		size++;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) {
		
		int hash=hashToSearch(key,0);
		
		if(hash!=-1) {
			return (V) table[hash].getValue();
		}else {
			return null;
		}
	}

	@Override
	public int keyToInteger(K key) {
		int integer=0;
		if(key instanceof Character) {
			integer = (Character)key - 97;
		}
		else if(key instanceof Integer) {
			integer=(Integer)key;
		}else if(key instanceof Double) {
			integer = (int)((Double)key * m);
		}else {
			String s= key.toString();
			for(int i=0; i<s.length();i++) {
				integer+= s.charAt(i);
			}
		}
		return integer;
	}

	@Override
	public boolean delete(K key) {
		int hash = hashToSearch(key,0);
		if(hash!=-1) {
			table[hash].setDelete(); //Set key null
			size--;
			return true;
		}
		else {
			return false;
		}
	}
	
	@SuppressWarnings( "unchecked" )
	public ArrayList<Tuple<? extends Comparable<?>,?>> toList(){
		ArrayList<Tuple<? extends Comparable<?>, ?>> list = new ArrayList<>();
		Tuple<K,V> current;

		for(int i = 0; i<m; i++) {
			current = (Tuple<K, V>) table[i];
			if(current !=null && current.getKey()!=null) {
				list.add(current);
			}
		}
		return list;
	}
}
