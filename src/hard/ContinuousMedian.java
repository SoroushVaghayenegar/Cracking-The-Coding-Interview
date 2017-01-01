package hard;

import java.util.PriorityQueue;

public class ContinuousMedian {
	private double median;
	private PriorityQueue<Integer> smaller;
	private PriorityQueue<Integer> larger;
	
	public ContinuousMedian(){
		smaller = new PriorityQueue<Integer>(new MyComparator());
		larger = new PriorityQueue<Integer>();
	}
	
	public void insertNumber(int num){
		//initializing (first number) since both heaps are empty
		if(smaller.size() == 0 && larger.size() == 0){
			median = num;
			smaller.add(num);
		}
		else{
			if(num > median){
				larger.add(num);
			}
			else{
				smaller.add(num);
			}
			
			adjustHeaps();
			updateMedian();
		}
	}
	
	private void updateMedian() {
		if(smaller.size() == larger.size() + 1)
			median = smaller.peek();
		else if(smaller.size() == larger.size())
			median = (double)(smaller.peek() + larger.peek()) / 2;
		else
			median = Integer.MAX_VALUE;
		
	}

	private void adjustHeaps() {
		if(correctDivision())
			return;
		while(!correctDivision()){
			if(smaller.size() < larger.size()){
				smaller.add(larger.poll());
			}
			else{
				larger.add(smaller.poll());
			}
		}
		
	}

	private boolean correctDivision() {
		return smaller.size() == larger.size() || smaller.size() == larger.size() + 1;
	}

	public double getMedian(){
		return median;
	}
}
