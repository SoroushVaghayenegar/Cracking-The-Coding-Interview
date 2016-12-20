package hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Hard {
	
	//17.1
	public int add(int a, int b){
		if(b ==0) return a;
		int sum = a^b;
		int carry = (a&b) <<1;
		return add(sum,carry);
	}
	
	
	//17.2
	public void shuffle(int[] cards){
		for(int i=0 ; i<cards.length ; i++){
			int k = randomNumber(0,cards.length);
			int temp = cards[k];
			cards[k] = cards[i];
			cards[i] = temp;
		}
	}
	
	public int[] shuffleRecursive(int[] cards , int n){
		shuffleRecursive(cards, n-1);
		
		int k = randomNumber(0,n);
		int temp = cards[k];
		cards[k] = cards[n];
		cards[n] = temp;
		
		return cards;
	}

	private int randomNumber(int low, int high) {
		return low + ((int)Math.random() * (high - low + 1));
	}
	
	
	//17.3
	public int[] randomSet(int[] n, int m){
		int[] subset = new int[m];
		for(int i=0 ; i<m ; i++){
			subset[i] = n[i];
		}
		Random rand = new Random();
		
		for(int i=m ; i<n.length ; i++){
			int k = rand.nextInt(i + 1);
			if(k < m){
				subset[k] = n[i];
			}
		}
		
		return subset;
	}
	
	
	//17.4
	
	//A is 0 ... n
	public int missingNumber(ArrayList<BitInteger> array){
		BitInteger b = new BitInteger(0);
		return missingNumber(array, b, 0);
	}


	private int missingNumber(ArrayList<BitInteger> array, BitInteger b, int j) {
		if(j >= BitInteger.INTEGER_SIZE)
			return b.toInt();
		
		ArrayList<BitInteger> zeros = new ArrayList<BitInteger>();
		ArrayList<BitInteger> ones = new ArrayList<BitInteger>();
		
		for(BitInteger bitInt : array){
			if(bitInt.fetchTheJthBit(j) == 0)
				zeros.add(bitInt);
			else
				ones.add(bitInt);
		}
		
		
		if(zeros.size() <= ones.size()){
			b.setJthBitZero(j);
			return missingNumber(zeros,b,j+1);
		}
		else{
			b.setJthBitOne(j);
			return missingNumber(ones,b,j+1);
		}	
	}
	
	//17.5
}
