package datastructures;

public class HashTable<K extends Comparable<K>,V> implements IHashTable<K,V>{
	
	private Tuple<? extends Comparable<?>,?>[] table;
	private int m;
	
	
	public HashTable(int m) {
		this.m = m;
		table = new Tuple<?,?>[m];
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int hash(K key, int explorator) {
		int hash = auxHash(key);
		Tuple<K,V> current = (Tuple<K, V>) table[hash + explorator];
		while((current!=null && current.getKey().compareTo(key)!=0) && explorator<m) {
			explorator++;
		}
		if(explorator<m) {
			return hash + explorator;
		}else {
			return -1;
		}
	}
	
	@Override
	public int auxHash(K key) {
		return keyToInteger(key)%m;
	}

	@Override
	public void put(K key, V value) {
		Tuple<K,V> tuple = new Tuple<K,V>(key, value);
		int hash = hash(tuple.getKey(), 0);
		if(hash!=-1) {
			table[hash]=tuple;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) {
		int hash=hash(key,0);
		if(hash!=-1 && table[hash]!=null) {
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
}
