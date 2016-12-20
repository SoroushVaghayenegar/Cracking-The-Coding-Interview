package moderate;

public class Node<K,V> {
	public Node pre , next;
	public V value;
	public K key;
	
	public Node(K key, V value){
		this.key = key;
		this.value = value;
	}
}
