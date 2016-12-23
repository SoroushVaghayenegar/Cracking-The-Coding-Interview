package hard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
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
	
	
	
	//17.9
	public int kthNumber(int k){
		if(k < 0)
			return 0;
		int kth = k;
		ArrayList<Integer> nums = new ArrayList<Integer>();
		HashSet<Integer> checker = new HashSet<Integer>();
		
		nums.add(1);
		int index = 0;
		while(k > 0){
			int val = nums.get(index);
			int v3 = 3 * val;
			if(!checker.contains(v3)){
				nums.add(v3);
				k--;
			}
			
			int v5 = 5 * val;
			if(!checker.contains(v5)){
				nums.add(v5);
				k--;
			}
			
			int v7 = 7 * val;
			if(!checker.contains(v7)){
				nums.add(v7);
				k--;
			}
			index++;
		}
		
		return nums.get(kth - 1);
	}

	
	//17.10
	public int getMajority(int[] nums){
		int candidate  = getCandidate(nums);
		
		return validate(nums,candidate);
	}


	private int validate(int[] nums, int candidate) {
		int count = 0;
		for(int x : nums){
			if(x == candidate)
				count++;
			if(count > (nums.length/2))
				return candidate;
		}
		return -1;
	}


	private int getCandidate(int[] array) {
		int majority = 0;
		int count = 0;
		for(int n : array){
			if(count == 0)
				majority = n;
			
			if(n == majority)
				count++;
			else
				count--;
		}
		return majority;
	}
	
	
	//17.11
	public int wordDistance(HashMap<String, ArrayList<Integer>> wordLocations, String word1, String word2){
		ArrayList<Integer> locations1 = wordLocations.get(word1);
		ArrayList<Integer> locations2 = wordLocations.get(word2);
		
		return findClosestDistance(locations1, locations2);
	}


	private int findClosestDistance(ArrayList<Integer> locations1, ArrayList<Integer> locations2) {
		int maxDistance = -1;
		int index1 = 0;
		int index2 = 0;
		
		
		while(index1 < locations1.size() && index2 < locations2.size() ){
			int val1 = locations1.get(index1);
			int val2 = locations1.get(index2);
			int diff = Math.abs(val1 - val2); 
			
			if(val1 < val2){
				if(diff > maxDistance){
					maxDistance = diff - 1;
				}
				index1++;
			}
			else{
				if(diff > maxDistance){
					maxDistance = diff - 1;
				}
				index2++;
			}
		}
		return maxDistance;
	}


	private HashMap<String, ArrayList<Integer>> preProcess(String[][] file) {
		HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
		int count = 0;
		for(String[] row : file){
			for(String current : row){
				if(map.containsKey(current)){
					ArrayList<Integer> locations = map.get(current);
					locations.add(count);
				}
				else{
					ArrayList<Integer> locations = new ArrayList<Integer>();
					locations.add(count);
					map.put(current, locations);
				}
				
				count++;
			}
		}
		
		return map;
	}
	
	
	//17.12
	public BiNode fromTreeToDoublyLinkedList(BiNode root){
		if(root == null)
			return null;
		
		BiNode left = fromTreeToDoublyLinkedList(root.node1);
		BiNode right = fromTreeToDoublyLinkedList(root.node2);
		
		if(left != null){
			concat(getTail(left), root);
		}
		
		if(right != null){
			concat(root, right);
		}
		
		return left != null ? left : root;
	}




	private void concat(BiNode left, BiNode right) {
		left.node2 = right;
		right.node1 = left;
		
	}


	private BiNode getTail(BiNode root) {
		if(root == null)
			return null;
		
		while(root.node2 != null){
			root = root.node2;
		}
		return root;
	}
	
	
	//17.13
	public String reSpace(String doc, HashSet<String> dictionary, int index){
		if(index >= doc.length() || doc.length() == 0)
			return "";
		
		StringBuilder sb = new StringBuilder();
		for(int i=index ; i<doc.length() ; i++){
			sb.append(doc.charAt(i));
			if(dictionary.contains(sb.toString())){
				String left = doc.substring(0, index);
				String word = sb.toString();
				String right = doc.substring(i+1, doc.length());
				
				return concatStrings(left,word,reSpace(right, dictionary, 0)).trim();
			}
		}
		
		return reSpace(doc, dictionary, index + 1);
	}


	private String concatStrings(String left, String word, String right) {
		if(left.isEmpty())
			return word + " " + right;
		else
			return left + " " + word + " " + right;
	}
	
	
	
	//17.4
	public int[] smallestKNumbers(int[] arr, int k){
		Random rand = new Random();
		int index = rand.nextInt(arr.length);
		
		ArrayList<Integer> smaller = new ArrayList<Integer>();
		ArrayList<Integer> bigger = new ArrayList<Integer>();
		
		for(int i=0 ; i< arr.length ; i++){
			if(arr[i]< arr[index] && i!= index)
				smaller.add(arr[i]);
			else
				bigger.add(arr[i]);
		}
		
		if(smaller.size() == k)
			return makeArray(smaller);
		else if (smaller.size() > k)
			return smallestKNumbers(makeArray(smaller) , k);
		else
			return smallestKNumbers(arr , k); 
	}


	private int[] makeArray(ArrayList<Integer> smaller) {
		int[] array = new int[smaller.size()];
		for(int i=0 ; i<smaller.size() ; i++){
			array[i] = smaller.get(i);
		}
		return array;
	}
	
	
	//17.15
	//Get the longest word that is made of other words
	public String longestWord(String[] words){
		if(words.length == 0 || words == null)
			return null;
		
		//put all original words in a map
		HashMap<String,Boolean> dictionary = toDictionary(words);
		
		//sort words 
		Arrays.sort(words);
		
		//start from end (largest word)
		for(int i = words.length - 1 ; i >= 0 ; i--){
			String currentWord = words[i];
			
			//check if it is made from other words, if yes return it
			if(isMadeOfOtherWords(dictionary,currentWord, true)){
				dictionary.put(currentWord, false);
				return currentWord;
			}
		}
		
		return "";
	}

	//check if it is made from other words
	private boolean isMadeOfOtherWords(HashMap<String,Boolean> dictionary, String currentWord, boolean isOriginal) {
		//if words is in dictionary and it is not original then we have found a word
		// made from other words
		if(dictionary.containsKey(currentWord) && !isOriginal)
			return true;
		
		for(int i=0 ; i<currentWord.length() ; i++){
			String left = currentWord.substring(0, i+1);
			String right = currentWord.substring(i+1);
			if(dictionary.containsKey(left) && dictionary.get(left) == true &&
					isMadeOfOtherWords(dictionary, right, false)){
				return true;
			}
		}
		
		return false;
	}

	//put words in a dictionary, with true input as they are all original words
	private HashMap<String,Boolean> toDictionary(String[] words) {
		HashMap<String,Boolean> dictionary = new HashMap<String,Boolean>();
		for(int i=0 ; i<words.length ; i++){
			dictionary.put(words[i], true);
		}
		return dictionary;
	}
	
	//17.16
	//returns number of minutes that maximizes masseuse's appointments
	//base on the input appointment set
	public int getOptimalSet(int[] appointments){
		return 0;
	}
}
