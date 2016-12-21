package hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

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
	public char[] getLongestSubarray(char[] array){
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		int delta = 0;
		
		int start = 0;
		int end = 0;
		
		int longest = 0;
		
		for(int i=0 ; i<array.length ; i++){
			if(Character.isLetter(array[i]))
				delta++;
			else
				delta--;
			
			
			if(map.containsKey(delta)){
				int match = map.get(delta);
				int distance = i - match;
				if(distance > longest){
					longest = distance;
					start = match + 1;
					end = i + 1;
				}
			}
			else{
				map.put(delta, i);
			}
		}
		
		return Arrays.copyOfRange(array, start, end);
	}
	
	
	//17.6
	public int countTwos(int n){
		int count = 0;
		int len = String.valueOf(n).length();
		for(int digit=0 ; digit < len ; digit++){
			count += getTwosCountAtDigit(n,digit);
		}
		return count;
	}


	private int getTwosCountAtDigit(int number, int d) {
		int powerOfTen = (int) Math.pow(10, d);
		int nextPowerOfTen = powerOfTen * 10;
		int right = number % powerOfTen;
				
		int roundDown = number - number % nextPowerOfTen;
		int roundUp = roundDown + nextPowerOfTen;
		
		int digit = (number/powerOfTen) % 10;
		if(digit < 2){
			return roundDown/10;
		}
		else if(digit == 2){
			return (roundDown/10) + right + 1; 
		}
		else{
			return roundUp/10;
		}
	}
	
	
	//17.7
	public HashMap<String,Integer> trueFrequency(HashMap<String,Integer> names, 
												String[][] synonyms){
		
		HashMap<String, NameSet> groups = constructGroups(names);
		
		mergeClasses(groups, synonyms);
		
		return convertToMap(groups);
	}


	private HashMap<String, Integer> convertToMap(HashMap<String, NameSet> groups) {
		HashMap<String, Integer> list = new HashMap<String, Integer>();
		for(NameSet n : groups.values()){
			list.put(n.getRootName(), n.getFrequency());
		}
		return list;
	}


	private void mergeClasses(HashMap<String, NameSet> groups, String[][] synonyms) {
		for(String[] entry : synonyms){
			String name1 = entry[0];
			String name2 = entry[1];
			NameSet set1 = groups.get(name1);
			NameSet set2 = groups.get(name2);
			if(set1 != set2){
				NameSet smaller = set1.size() > set2.size()? set2 : set1;
				NameSet bigger = smaller == set1 ? set2 : set1;
				
				Set<String> othernames = smaller.getNames();
				int frequency = smaller.getFrequency();
				bigger.copyNamesWithFrequency(othernames, frequency);
				
				for(String s : othernames){
					groups.put(s, bigger);
				}
			}
		}
		
	}


	private HashMap<String, NameSet> constructGroups(HashMap<String, Integer> names) {
		HashMap<String, NameSet> groups = new HashMap<String, NameSet>();
		for(Entry<String, Integer> entry : names.entrySet()){
			String name = entry.getKey();
			int frequency = entry.getValue();
			NameSet group = new NameSet(name,frequency);
			groups.put(name, group);
		}
		return groups;
	}
	
	
	//17.8
	public ArrayList<HtWt> circusTower(ArrayList<HtWt> people){
		Collections.sort(people);
		
		ArrayList<ArrayList<HtWt>> solutions = new ArrayList<ArrayList<HtWt>>();
		ArrayList<HtWt> bestSequence = null;
		
		for(int i=0 ; i<people.size() ; i++){
			ArrayList<HtWt> longestAtIndex = bestSequenceAtIndex(people, solutions, i);
			solutions.add(i, longestAtIndex);
			bestSequence = max(longestAtIndex, bestSequence);
		}
		
		return bestSequence;
	}


	private ArrayList<HtWt> bestSequenceAtIndex(ArrayList<HtWt> people, ArrayList<ArrayList<HtWt>> solutions, int index) {
		HtWt value = people.get(index);
		
		ArrayList<HtWt> bestSequence = new ArrayList<HtWt>();
		
		for(int i=0 ; i<index ; i++){
			ArrayList<HtWt> solution = solutions.get(i);
			if(canAppend(solution, value)){
				bestSequence = max(solution, bestSequence);
			}
		}
		
		ArrayList<HtWt> best = (ArrayList<HtWt>) bestSequence.clone();
		best.add(value);
		return best;
	}


	private ArrayList<HtWt> max(ArrayList<HtWt> solution, ArrayList<HtWt> bestSequence) {
		if(solution == null)
			return bestSequence;
		if(bestSequence == null)
			return solution;
		
		return solution.size() > bestSequence.size() ? solution : bestSequence;
	}


	private boolean canAppend(ArrayList<HtWt> solution, HtWt value) {
		if(solution == null) return false;
		if(solution.size() == 0) return true;
		
		return solution.get(solution.size()-1).isBefore(value);
	}
}
