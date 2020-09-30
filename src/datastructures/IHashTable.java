package datastructures;
import customexception.*;

public interface IHashTable<K extends Comparable<K>,V> {
	
	public boolean isEmpty();
	public int getSize();
	public int hashToSearch(K key, int explorator);
	public int hashToInsert(K key, int explorator) throws FullStructureException;
	public int auxHash(K key);
	public void put(K key, V value) throws FullStructureException;
	public V get(K key);
	public int keyToInteger(K key);
	public boolean delete(K key);
}
