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
		int[] memo = new int[appointments.length];
		return getOptimalSet(appointments, 0, memo);
	}


	private int getOptimalSet(int[] appointments, int i, int[] memo) {
		if(appointments == null || i >= appointments.length)
			return 0;
		if(memo[i] != 0){
			return memo[i];
		}
		else{
			int current = appointments[i];
			memo[i] = Math.max(current + getOptimalSet(appointments, i+2, memo), getOptimalSet(appointments, i+1, memo));
			return memo[i];
		}
	}
	
	//takes O(n) time and O(1) space
	public int getOptimalSetIterative(int[] appointments){
		int oneAway = 0;
		int twoAway = 0;
		for(int i = appointments.length - 1; i>=0 ; i--){
			int bestWith = appointments[i] + twoAway;
			int bestWithout = oneAway;
			int current = Math.max(bestWith, bestWithout);
			twoAway = oneAway;
			oneAway = current;
		}
		return oneAway;
	}
	
	
	//17.17
	public HashMap<String, ArrayList<Integer>> multiSearch(String b, String[] arr){
		HashMap<String, ArrayList<Integer>> lookup = new HashMap<String, ArrayList<Integer>>();
		Trie trie = createTrieFromString(b);
		
		for(String s : arr){
			//get terminating locations of each string
			ArrayList<Integer> locations = trie.search(s);
			
			subtractValue(locations, s.length());
			
			lookup.put(s, locations);
			
		}
		return lookup;
	}


	private void subtractValue(ArrayList<Integer> locations, int length) {
		if(locations == null) return;
		for(int i=0 ; i<locations.size() ; i++){
			locations.set(i, locations.get(i) - length);
		}
	}


	private Trie createTrieFromString(String b) {
		Trie trie = new Trie();
		for(int i=0 ; i<b.length() ; i++){
			String current = b.substring(i);
			trie.insertString(current, i);
		}
		return trie;
	}
	
	
	//17.18
	public int[] shortestSupersequence(int[] target, int[] array){
		int[][] nextElements = getNextElements(target, array);
		int[] distances = getDistances(nextElements);
		return getShortestDistance(distances);
	}


	private int[] getShortestDistance(int[] distances) {
		int[] range ={-1,-1};
		int shortest = Integer.MAX_VALUE;
		
		for(int i=0 ; i<distances.length ; i++){
			if(distances[i] == -1)
				break;
			
			int current = distances[i] - i;
			if(current < shortest){
				shortest = current;
				range[0] = i;
				range[1] = distances[i];
			}
		}
		return range;
	}


	private int[] getDistances(int[][] nextElements) {
		int[] distances = new int[nextElements[0].length];
		for(int i=0 ; i<nextElements[0].length ; i++){
			distances[i] = getDistance(nextElements, i);
		}
		return distances;
	}


	private int getDistance(int[][] nextElements, int index) {
		int max = -1;
		for(int i=0; i<nextElements.length ; i++){
			if(nextElements[i][index] == -1){
				return -1;
			}
			max = Math.max(max, nextElements[i][index]);
		}
		return max;
	}


	private int[][] getNextElements(int[] target, int[] array) {
		int[][] nextElements = new int[target.length][array.length];
		for(int i=0 ; i<target.length ; i++){
			nextElements[i] = getNextElement(target[i] , array);
		}
		return nextElements;
	}


	private int[] getNextElement(int val, int[] array) {
		int next = -1;
		int[] nextElement = new int[array.length];
		for(int i = array.length - 1 ; i>=0 ; i--){
			if(val == array[i])
				next = i;
			nextElement[i] = next;
		}
		
		return nextElement;
	}


	//17.19
	public int[] findMissingTwo(int[] nums){
		int maxValue = nums.length + 2;
		int sum_square = squareSumToN(maxValue, 2);
		int sum = maxValue * (maxValue + 1)/2;
		
		for(int i=0 ; i< nums.length ; i++){
			sum_square -= nums[i] * nums[i];
			sum -= nums[i];
		}
		
		return findVariables(sum,sum_square);
	}



	private int[] findVariables(int r1, int r2) {
		
		int a = 2;
		int b = -2 * r1;
		int c = r1 *r1 - r2;
		
		double part1 = -1 * b;
		double part2 = Math.sqrt((b*b) - (4 * a * c));
		double part3 = 2*a;
		
		int solutionX = (int) ((part1 + part2) / part3);
		int solutionY = r1 - solutionX;
		
		int[] nums = {solutionX,solutionY};
		return nums;
	}




	

	private int squareSumToN(int n, int power) {
		int sum = 0;
		for(int i=1 ; i<=n; i++){
			sum += (int) Math.pow(i, power);
		}
		
		return sum ;
	}
	
	
	// 17.20
	// Created in its own class
	
	
	//17.21
	public int getVolume(int[] array){
		return getVolume(array, 0, array.length-1);
	}

	private int getVolume(int[] array, int low, int high){
		if(high < low || low<0)
			return 0;
		int tallestIndex = getTallest(array, low, high);
		
		if(tallestIndex == -1)
			return 0;
		
		int leftTallestIndex = getTallest(array, low, tallestIndex - 1);
		int rightTallestIndex = getTallest(array, tallestIndex + 1, high);
		
		int volumeLeft = calculateVolume(array, leftTallestIndex, tallestIndex);
		int volumeRight = calculateVolume(array, tallestIndex, rightTallestIndex);
		
		int left = getVolume(array, low, leftTallestIndex);
		int right = getVolume(array, rightTallestIndex , high);
		
		return left + volumeLeft + volumeRight + right; 
	}
	
	private int calculateVolume(int[] array, int low, int high) {
		if(low == -1 || high == -1)
			return 0;
		
		int sum = 0;
		
		//find the shortest bar out of the two tallest to subtract each value from
		int max = array[low] < array[high] ? array[low] : array[high];
		for(int i = low+1 ; i<high ; i++){
			sum  += (max - array[i]);
		}
		return sum;
	}

	//returns tallest, or -1 if no bars (all are zero)
	private int getTallest(int[] array, int low, int high) {
		if(high < low)
			return -1;
		
		int index = -1;
		int tallest = 0;
		for(int i = low; i <= high ; i++){
			if(array[i] > tallest){
				tallest = array[i];
				index = i;
			}
		}
		return index;
	}


	private int[] getStartingAndEnding(int[] array) {
		int[] range = new int[2];
		for(int i=0 ; i<array.length ; i++){
			if(array[i] != 0){
				range[0] = i;
				break;
			}
		}
		
		for(int i = array.length ; i >= 0 ; i--){
			if(array[i] != 0){
				range[1] = i;
				break;
			}
		}
		return range;
	}
	
	
	public int getVolumeOptimized(int[] array){
		int[] leftMaxes = new int[array.length];
		
		int leftMax = array[0];
		for(int i=0 ; i<array.length ; i++){
			if(array[i] > leftMax)
				leftMax = array[i];
			
			leftMaxes[i] = leftMax;
		}
		
		int sum = 0;
		
		int rightMax = array[array.length - 1];
		for(int i = array.length - 1 ; i >= 0 ; i--){
			if(array[i] > rightMax)
				rightMax = array[i];
			
			int secondTallest = Math.min(rightMax, leftMaxes[i]);
			
			sum += secondTallest - array[i];
		}
		
		return sum;
	}
	
	
	//17.22
	public void wordTransformer(String word1, String word2, HashSet<String> dictionary){
		if(word1.length() != word2.length())
			return;
		if(word1.equals(word2)){
			System.out.print(word2);
			return;
		}
		
		System.out.print(word1 + " -> ");
		
		for(int i=0 ; i<word1.length() ; i++){
			if(word1.charAt(i) != word2.charAt(i)){
				String temp = word1;
				char[] cArray = temp.toCharArray();
				cArray[i] = word2.charAt(i);
				temp = String.copyValueOf(cArray);
				if(dictionary.contains(temp)){
					wordTransformer(temp, word2, dictionary);
					break;
				}
			}
		}
	}
	
	
	//17.23
	//1 is black
	//0 is white
	/*
	 * W're looking for  11111111
	 * 					 1xxxxxx1
	 * 					 1xxxxxx1
	 * 					 11111111
	 */
	public int[][] maxBlackquare(int[][] matrix){
		
		SquareCell[][] processedSquare = processSquare(matrix);
		//starting square size from biggest
		for(int side= matrix.length ; side>0  ; side--){
			for(int row=0 ; row < matrix.length - side +1 ; row++){
				for(int col=0 ; col < matrix.length - side +1 ; col++){
					if(hasBlackBorder(processedSquare, row, col, side)){
						return getSquare(matrix, row, col, side);
					}
				}
			}
		}
		return null;
	}


	private SquareCell[][] processSquare(int[][] matrix) {
		SquareCell[][] processedSquare = new SquareCell[matrix.length][matrix[0].length];
		
		for(int r = matrix.length-1 ; r>=0 ; r--){
			for(int c = matrix.length-1 ; c>=0 ; c--){
				int onesBelow = 0;
				int onesRight = 0;
				
				if(matrix[r][c] == 1){
					onesBelow++;
					onesRight++;	
				}
				
				if(c + 1 < matrix.length){
					SquareCell right = processedSquare[r][c+1];
					onesRight += right.getOnesRight();
				}
				
				if(r + 1 < matrix.length){
					SquareCell below = processedSquare[r+1][c];
					onesBelow += below.getOnesBelow();
				}
				
				processedSquare[r][c] = new SquareCell(onesRight, onesBelow);
			}
		}
		return processedSquare;
	}


	private int[][] getSquare(int[][] matrix, int row, int col, int side) {
		int[][] square = new int[side][side];
		for(int i=row ; i< row+side ; i++){
			for(int j=col ; j< col+side ; j++){
				square[i-row][j-col] = matrix[i][j];
			}
		}
		return square;
	}


	private boolean hasBlackBorder(SquareCell[][] processedSquare, int row, int col, int side) {
		SquareCell topLeft = processedSquare[row][col];
		SquareCell topRight = processedSquare[row][col + side -1];
		SquareCell bottomLeft = processedSquare[row + side - 1][col];
		
		if(topLeft.getOnesRight() < side || topLeft.getOnesBelow() < side ||
		   topRight.getOnesBelow() < side || bottomLeft.getOnesRight() < side)
			return false;
		return true;
	}
	
	
	//17.24
	//gets the largest submatrix with the largest sum
	//input: matrix of positive and negative numbers
	public int[][] maxSubmatrix(int[][] matrix){
		int[][] preProcessed = getAllSums(matrix);
		Submatrix coord = new Submatrix();
		//check each sub-matrix 
		for(int w=1 ; w<=matrix.length ; w++){
			for(int l=1 ; l<=matrix.length ; l++){
				//check sub-matrix at each row and column
				for(int r=0; r<=matrix.length - l ; r++){
					for(int c=0 ; c<=matrix.length - w ; c++){
						getMaxSubmatrix(preProcessed, w, l, r, c, coord);
					}
				}
			}
		}
		
		return getSubmatrix(matrix, coord);
	}


	private int[][] getAllSums(int[][] matrix) {
		int[][]preProcessed = new int [matrix.length][matrix[0].length];
		for(int r=0 ; r<matrix.length ; r++){
			for(int c=0; c<matrix.length ; c++){
				int top = getSum(preProcessed, r-1, c);
				int left = getSum(preProcessed, r, c-1);
				int top_left = getSum(preProcessed, r-1, c-1);
				
				preProcessed[r][c] = matrix[r][c] + top + left - top_left;
			}
		}
		return preProcessed;
	}


	private int getSum(int[][] preProcessed, int r, int c) {
		if(r<0 || c<0 || r >= preProcessed.length || c >= preProcessed.length)
			return 0;
		else
			return preProcessed[r][c];
	}


	private int[][] getSubmatrix(int[][] matrix, Submatrix coord) {
		int l = coord.getLength();
		int w = coord.getWidth();
		int r = coord.getRow();
		int c = coord.getCol();
		
		int[][] subMatrix = new int [l][w];
		
		for(int i=r ; i<r + l ; i++){
			for(int j=c ; j<c+w ; j++){
				subMatrix[i-r][j-c] = matrix[i][j];
			}
		}
		return subMatrix;
	}


	private void getMaxSubmatrix(int[][] preProcessed, int width, int length, int row, int col, Submatrix coord) {
		int topAndLeft = row > 0 && col >0 ? preProcessed[row-1][col-1] : 0;
		int left = col > 0 ? preProcessed[row+length-1][col-1] : 0;
		int top = row > 0 ? preProcessed[row-1][col+width-1] : 0;
		int full = preProcessed[row + length -1][col + width -1];
		int sum = full - left - top + topAndLeft;
		if(sum > coord.getSum()){
			coord.setSum(sum);
			coord.setCol(col);
			coord.setLength(length);
			coord.setRow(row);
			coord.setWidth(width);
		}
	}
	
	
	//17.25
	public String wordRectangle(ArrayList<String> words){
		Trie trie = makeATrie(words);
		HashMap<Integer, ArrayList<String>> group = groupByLength(words);
		int maxLength = getMaxLength(words);
		
		String best = null;
		for(int w=maxLength ; w>0 ; w--){
			for(int l=maxLength ; l>0 ; l--){
				best = createRectangle(trie, group, w, l); 
			}
		}
		return best;
	}


	private Trie makeATrie(ArrayList<String> words) {
		Trie trie = new Trie();
		for(String word : words){
			trie.insertString(word, 0);
		}
		return trie;
	}


	private String createRectangle(Trie trie, HashMap<Integer, ArrayList<String>> group, int width, int length) {
		ArrayList<String> wList = group.get(width);
		ArrayList<String> lList = group.get(length);
		if(lList != null && wList != null){
			return null;
		}
		else
			return null;
	}


	private int getMaxLength(ArrayList<String> words) {
		int maxLength = 0;
		for(String word : words){
			if(word.length() > maxLength)
				maxLength = word.length();
		}
		return maxLength;
	}


	private HashMap<Integer, ArrayList<String>> groupByLength(ArrayList<String> words) {
		HashMap<Integer, ArrayList<String>> lengthGroup = new HashMap<Integer, ArrayList<String>>();
		int maxLength = 0;
		for(String word : words){
			if(word.length() > maxLength)
				maxLength = word.length();
			if(lengthGroup.containsKey(word.length())){
				ArrayList<String> list = lengthGroup.get(word.length());
				list.add(word);
			}
			else{
				ArrayList<String> list = new ArrayList<String>();
				list.add(word);
				lengthGroup.put(word.length(), list);
			}
		}
		return lengthGroup;
	}

	
	//17.26
	public ArrayList<DocumentPair> sparseSimilarity(ArrayList<Document> documents){
		ArrayList<DocumentPair> pairs = new ArrayList<DocumentPair>();
		for(int i=0 ; i < documents.size() ; i++){
			for(int j = i+1 ; j < documents.size() ; j++){
				DocumentPair pair = getPair(documents.get(i), documents.get(j));
				if(pair.getSimilarity() > 0)
					pairs.add(pair);
			}
		}
		return pairs;
	}


	private DocumentPair getPair(Document d1, Document d2) {
		double similarity = getSimilarity(d1,d2);
		return new DocumentPair(d1.getId(), d2.getId(), similarity);
	}


	private double getSimilarity(Document d1, Document d2) {
		HashSet<Integer> set = new HashSet<Integer>();
		for(Integer num : d1.getList()){
			set.add(num);
		}
		
		double intersection = 0;
		for(Integer num : d2.getList()){
			if(set.contains(num))
				intersection++;
			else
				set.add(num);
		}
		return (double) intersection/set.size();
	}
}
