package moderate;

import java.util.HashMap;

public class LRUCache<K,V> {
	private int capacity;
	private int size;
	private HashMap<K,Node> map;
	private Node head;
	private Node last;
	
	
	//Nodes are associated with keys\\
	
	
	public LRUCache(int capacity){
		this.capacity = capacity;
		this.size = 0;
		this.map = new HashMap<K,Node>();
		
	}
	
	public V get(K key){
		Node requested = map.get(key);
		V val = (V) requested.value;
		if(requested != head)
			moveNodeToHead(requested);
		return val;
	}
	
	private void moveNodeToHead(Node requested) {
		requested.pre.next = requested.next;
		requested.pre = null;
		requested.next = head;
	}

	public void set(K key, V value){
		if(head == null){
			head = new Node(key, value);
			last = head;
			map.put(key, head);
		}
		else{
			Node n = new Node(key, value);
			n.next = head;
			head = n;
		}
		size++;
		if(isOverCapacity())
			removeLastNode();
	}
	
	private void removeLastNode() {
		last.pre.next = null;
		map.remove(last.key);
		last = last.pre;	
	}

	private boolean isOverCapacity(){
		return size > capacity;
	}
}
