package datastructures;

public interface IHashTable<K extends Comparable<K>,V> {
	
	public int hash(K key, int explorator);
	public int auxHash(K key);
	public void put(K key, V value);
	public V get(K key);
	public int keyToInteger(K key);
}
