package hard;

import java.util.ArrayList;
import java.util.Iterator;

public class Document{

	private ArrayList<Integer> list;
	private int id;
	
	public Document(int id){
		this.id = id;
		this.list = new ArrayList<Integer>();
	}
	
	public void add(int num){
		this.list.add(num);
	}
	
	public void insertAt(int num, int index){
		this.list.set(index, num);
	}
	
	public void remove(int index){
		this.list.remove(index);
	}
	
	public int getId() {
		return id;
	}

	public ArrayList<Integer> getList() {
		return list;
	}

	
	
}
