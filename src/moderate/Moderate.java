package moderate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import moderate.BoardSquare.SquareType;
import moderate.PlankOfWood.PlankType;

public class Moderate {
	
	//16.1
	//Swap numbers without temp variables
	public void numberSwapper(int[] arr){
		arr[0] = arr[0] + arr[1];
		arr[1] = arr[0] - arr[1];
		arr[0] = arr[0] - arr[1];
	}
	
	//16.2
	private HashMap<String, Integer> cache = new HashMap<String, Integer>();
	public void preProcessBook(String[] book){
		for(String s : book){
			s = s.toLowerCase();
			if(s.trim() != ""){
				if(cache.containsKey(s))
					cache.put(s, cache.get(s) +1);
				else
					cache.put(s, 1);
			}
		}
	}
	
	public int wordFrequency(String[] book, String word){
		if(book == null || word == null)
			return 0;
		word = word.toLowerCase();
		if(cache.containsKey(word))
			return cache.get(word);
		else
			return 0;
	}
	
	//16.3
	
	public Point intersection(Line line1, Line line2){
			if((line1.slope - line2.slope) == 0)
				return null;
			int x = (line2.b - line1.b)/(line1.slope - line2.slope);
			int y = getYfromX(x , line1);
			return new Point(x,y);
		
	}

	private int getYfromX(int x, Line line1) {
		return (line1.slope * x) + line1.b;
	}
	
	
	//16.5
	public int factorialZeros(int n){
		if(n < 0)
			return -1;
		int result = 0;
		while(n >= 5){
			n = n / 5;
			result +=n;
		}
		return result;
	}
	
	//16.6
	public int smallestDifference(int[] a, int[] b){
		Arrays.sort(a);
		Arrays.sort(b);
		
		
		int min = Integer.MAX_VALUE;
		
		int indexA = 0;
		int indexB = 0;
		while(indexA < a.length && indexB < b.length){
			int val = Math.abs(a[indexA] - b[indexB]);
			if(val < min){
				min = val;
			}
			
			if(a[indexA] < b[indexB])
				indexA++;
			else
				indexB++;
		
		}
		return min;
	}
	
	//16.7
	public int maxOfTwoNumbers(int a, int b){
		int[] arr = {a,b};
		int k = getSignBit(a-b);
		return arr[k];
	}

	private int getSignBit(int i) {
		return (i >> 31) & 1;
	}
	
	
	//16.8
	String[] smalls = {"Zero", "One", "Two", "Three", "Four", "Five", "Six",
			"Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thrteen", 
			"Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen",
			"Nineteen"};
	String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty",
			"Seventy", "Eighty", "Ninety"};
	
	String[] bigs = {"", "Thousand", "Million", "Billion"};
	String hundred = "Hundred";
	String negative = "Negtive";
	
	public String convert(int n){
		if(n == 0)
			return smalls[0];
		else if(n < 0){
			return negative + " " + convert(-1 * n);
		}
		
		LinkedList<String> parts = new LinkedList<String>();
		int chunkCount = 0;
		while (n > 0){
			if(n % 1000 != 0){
				String chunk = convertChunk(n % 1000) + " " + bigs[chunkCount];
				parts.addFirst(chunk);
			}
			n /= 1000;
			chunkCount++;
		}
		
		return listToString(parts);
	}
	
	private String convertChunk(int num){
		LinkedList<String> parts = new LinkedList<String>();
		
		if(num >= 100){
			parts.addLast(smalls[num/100]);
			parts.addLast(hundred);
			num %= 100;
		}
		
		if(num >= 10 && num <= 19){
			parts.addLast(smalls[num]);
		}
		else if(num >= 20){
			parts.addLast(tens[num/10]);
			num %= 10;
		}
		
		if(num >=1 && num <=9){
			parts.addLast(smalls[num]);
		}
		
		return listToString(parts);
	}

	private String listToString(LinkedList<String> parts) {
		StringBuilder sb = new StringBuilder();
		while(parts.size() > 1){
			sb.append(parts.pop());
			sb.append(" ");
		}
		sb.append(parts.pop());
		return sb.toString();
	}
	
	//16.9
	public int subtract(int a, int b){
		b = flipSign(b);
		return a + b;
	}

	private int flipSign(int b) {
		return ~b + 1;
	}
	
	public int multiply(int a, int b){
		boolean negate = false;
		if(a < 0 || b < 0)
			negate = true;
		
		if(a == 0 || b == 0)
			return 0;
		if(b > a)
			return multiply(b,a);
		
		
		a = Math.abs(a);
		b = Math.abs(b);
		
		int res = 0;
		while(b > 0){
			res += a;
			b--;
		}
		if(negate)
			return flipSign(res);
		else
			return res;
	}
	
	public int divide(int a, int b){
		if(a == 0)
			throw new ArithmeticException("Divide by Zero");
		
		boolean negate = false;
		if(b < 0)
			negate = true;
		
		a = Math.abs(a);
		b = Math.abs(b);
		int count = 0;
		while(a > 0){
			a -= b;
			count++;
		}
		if(negate)
			return flipSign(count);
		else
			return count;
	}
	
	
	// 16.10
	//SOLUTION 1
	public int livingPeople(Person[] people){
		int[] years = new int[101];
		for(Person p : people){
			for(int i=p.born; i<p.death ; i++){
				if(i>2001)
					break;
				int val = i - 1900;
				years[val]++;
			}
		}
		return max(years) + 1900;
	}

	private int max(int[] years) {
		int max = Integer.MIN_VALUE;
		for(int i=0 ; i<years.length ; i++){
			if(max < years[i])
				max = years[i];
		}
		return max;
	}
	
	//SOLUTION 2
	public int livingPeople(Person[] people, int min, int max){
		int[] years = getYears(people, min,max);
		int maxAlive=0;
		int maxAliveYear = 0;
		int currentlyAlive = 0;
		for(int i=0; i<years.length ; i++){
			currentlyAlive += years[i];
			if(currentlyAlive > maxAlive){
				maxAlive = currentlyAlive;
				maxAliveYear = i;
			}
		}
		return maxAliveYear + min;
	}

	private int[] getYears(Person[] people, int min, int max) {
		int[] result = new int[max-min + 2];
		for(Person p : people){
			int bornVal = p.born - min;
			int deathVal = p.death - min + 1;
			result[bornVal]++;
			result[deathVal]--;
		}
		
		return result;
	}
	
	
	// 16.11
	public HashSet<Integer> divingBoard(int k, int shorter, int longer){
		HashSet<Integer> lengths = new HashSet<Integer>();
		HashSet<String> visited = new HashSet<String>();
		getAllLengths(k, 0,lengths, shorter, longer, visited);
		return lengths;
	}

	private void getAllLengths(int k, int total, HashSet<Integer> lengths, int shorter, int longer,HashSet<String> visited ) {
		if(k == 0){
			lengths.add(total);
			return;
		}
		String key = k + " " + total;
		if(visited.contains(key)){
			return;
		}
		
		getAllLengths(k -1, total + shorter , lengths, shorter, longer, visited);
		getAllLengths(k -1, total + longer , lengths, shorter, longer, visited);
		visited.add(key);
		
	}
	
	
	public HashSet<Integer> optimalDivingBoard(int k, int shorter, int longer){
		HashSet<Integer> lengths = new HashSet<Integer>();
		
		for(int numOfShort=0 ; numOfShort<= k ; numOfShort++){
			int numOfLong = k - numOfShort;
			int length = numOfLong * longer + numOfShort * shorter;
			lengths.add(length);
		}
		return lengths;
	}
	
	// 16.13
	public Line bisectSqures(Square s1, Square s2){
		Line horizontal = areHorizontal(s1,s2);
		if(horizontal != null)
			return horizontal;
		
		Line vertical = areVertical(s1,s2);
		if(vertical != null)
			return vertical;
		
		Line diagnal = areDiagnal(s1,s2);
		
		return diagnal;
		
	}

	private Line areDiagnal(Square s1, Square s2) {
		//positive
		Line lineP1 = new Line(s1.bottomLeft, s1.topRight);
		Line lineP2 = new Line(s2.bottomLeft, s2.topRight);
		
		boolean positiveDiagnal = areLinesEqual(lineP1, lineP2);
		
		//negative
		Line lineN1 = new Line(s1.bottomRight, s1.topLeft);
		Line lineN2 = new Line(s2.bottomRight, s2.topLeft);
		
		boolean negativeDiagnal = areLinesEqual(lineN1, lineN2);
		
		if(positiveDiagnal)
			return lineP1;
		else if(negativeDiagnal)
			return lineN1;
		else
			return null;
	}

	public Line areHorizontal(Square s1, Square s2) {
		int first = s1.bottomLeft.y + (s1.topLeft.y - s1.bottomLeft.y);
		int second = s2.bottomLeft.y + (s2.topLeft.y - s2.bottomLeft.y);
		if(first == second){
			if(s1.bottomLeft.x > s2.bottomRight.x){
				return new Line(new Point(s2.bottomRight.x,first), 
								new Point(s1.bottomLeft.x,first));
			}
			else{
				return new Line(new Point(s1.bottomRight.x,first), 
								new Point(s2.bottomLeft.x,first));
			}
			
		}
		else
			return null;
	}

	public Line areVertical(Square s1, Square s2) {
		int first = s1.bottomLeft.x + (s1.bottomRight.x - s1.bottomLeft.x);
		int second = s2.bottomLeft.x + (s2.bottomRight.x - s2.bottomLeft.x);
		if(first == second){
			return new Line(first);
		}
		else
			return null;
		
	}
	
	private boolean areLinesEqual(Line l1, Line l2){
		return l1.slope == l2.slope && l1.b == l2.b;
	}
	
	
	//16.14
	public String bestLine(ArrayList<Point> points){
		HashMap<String, Integer> lines = new HashMap<String, Integer>();
		for(int i=0 ; i<points.size() ; i++){
			for(int j= i + 1 ; j<points.size() ; j++){
				String newLine = new Line(points.get(i), points.get(j)).toString();
				if(lines.containsKey(newLine)){
					lines.put(newLine, lines.get(newLine) + 2);
				}
				else
					lines.put(newLine, 2);
			}
		}
		
		Iterator it = lines.entrySet().iterator();
		int max = Integer.MIN_VALUE;
		String line = "";
		while(it.hasNext()){
			Map.Entry<String,Integer> val = (Entry<String, Integer>) it.next();
			if(val.getValue() > max){
				max = val.getValue();
				line = val.getKey();
			}
		}
		return line;
	}
	
	
	//16.5
	public int[] masterMind(Color[] solution, Color[] guess){
		//hits[0] : hit
		//hits[1] : pseudo-hit
		int[] hits = new int[2];
		for(int i=0 ; i<solution.length ; i++){
			if(solution[i] == guess[i]){
				hits[0]++;
				solution[i] = null;
				guess[i] = null;
			}
		}
		hits[1] = findPseudoHits(solution,guess);
		return hits;
	}

	private int findPseudoHits(Color[] solution, Color[] guess) {
		int pseudoHits = 0;
		HashMap<Color, Integer> numOfEachColor = new HashMap<Color, Integer>();
		
		for(int i=0 ; i<solution.length ; i++){
			Color current = solution[i];
			if(current != null){
				if(numOfEachColor.containsKey(current)){
					numOfEachColor.put(current, numOfEachColor.get(current) + 1);
				}
				else{
					numOfEachColor.put(current, 1);
				}
			}
		}
		
		for(int i=0 ; i<guess.length ; i++){
			Color current = guess[i];
			if(current != null){
				if(numOfEachColor.containsKey(current)){
					pseudoHits++;
					int val = numOfEachColor.get(current);
					if(val == 1)
						numOfEachColor.remove(current);
					else
						numOfEachColor.put(current, numOfEachColor.get(current) - 1);
					
				}
			}
		}
		return pseudoHits;
	}
	
	//16.16
	
	public Range subSort(int[] array){
		int[] left = getLeft(array);
		int[] right = getRight(array);
		
		int[] middle = Arrays.copyOfRange(array, left.length , array.length - right.length);
		
		boolean shiftLeft = true;
		boolean shiftRight = true;

		int leftIndex = left.length - 1;
		int rightIndex = 0;
		int lower = 0;
		int higher = 0;
		
		while(true){
			if(!shiftLeft && !shiftRight || leftIndex < 0 || rightIndex >= right.length)
				break;
			
			if(shiftLeft){
				if(middle[0] >= left[leftIndex]){
					lower = leftIndex;
					shiftLeft = false;
				}
				leftIndex--;
			}
			
			if(shiftRight){
				if(middle[middle.length-1] < right[rightIndex]){
					higher = left.length + middle.length + rightIndex - 1 ;
					shiftRight = false;
				}
				rightIndex++;
			}
		}
		
		return new Range(lower,higher);
	}

	private int[] getRight(int[] array) {
		int indexCutOff = 0;
		int pre = array[array.length - 1];
		for(int i = array.length - 2 ; i >= 0 ; i--){
			if(array[i] > pre){
				indexCutOff = i;
				break;
			}
			pre = array[i];
		}
		return Arrays.copyOfRange(array, indexCutOff + 1, array.length);
	}

	private int[] getLeft(int[] array) {
		int indexCutOff = 0;
		int pre = array[0];
		for(int i=1; i<array.length ; i++){
			if(array[i] < pre){
				indexCutOff = i;
				break;
			}
			pre = array[i];
		}
		return Arrays.copyOfRange(array, 0, indexCutOff);
	}

	
	//16.17
	public int sum(int[] array){
		int maxSum = 0;
		int sum = 0;
		for(int i=0; i<array.length ; i++){
			sum += array[i];
			if(maxSum < sum){
				maxSum = sum;
			}
			else if( sum < 0){
				sum = 0;
			}
		}
		return maxSum;
	}
	
	
	
	//16.18
	public boolean isMatch(String pattern, String value){
		if(pattern.length() == 0) return value.length() == 0;
		
		char mainChar = pattern.charAt(0);
		char altChar = mainChar == 'a' ? 'b' : 'a';
		int size = value.length();
		
		int countOfMain = count(pattern, mainChar);
		int countOfAlt = pattern.length() - countOfMain;
		int firstAlt = pattern.indexOf(altChar);
		int maxMainSize = size / countOfMain;
		
		for(int mainSize = 0 ; mainSize< maxMainSize ; mainSize++){
			int remainingLength = size - mainSize * countOfMain;
			String first = value.substring(0, mainSize);
			if(countOfAlt == 0 || remainingLength % countOfAlt == 0){
				int altIndex = firstAlt * mainSize;
				int altSize = countOfAlt == 0 ? 0 : remainingLength/countOfAlt;
				String second = countOfAlt == 0 ? "" :
								value.substring(altIndex, altSize + altIndex);
				String cand = buildFromPattern(pattern, first,second);
				if(cand.equals(value))
					return true;
			}
		}
		return false;
		
	}

	private int count(String pattern, char mainChar) {
		int count = 0;
		for(char c : pattern.toCharArray()){
			if(c == mainChar)
				count++;
		}
		return count;
	}

	private String buildFromPattern(String pattern, String main, String alt) {
		StringBuilder sb = new StringBuilder();
		char first = pattern.charAt(0);
		for(char c : pattern.toCharArray()){
			if(c == first){
				sb.append(main);
			}
			else{
				sb.append(alt);
			}
		}
		return sb.toString();
	}

	
	
	//16.19
	public ArrayList<Integer> pondSizes(int[][] matrix){
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		boolean[][] visited = new boolean[matrix.length][matrix[0].length];
		for(int row = 0 ; row < matrix.length ; row++){
			for(int col = 0 ; col < matrix[0].length ; col++){
				if(matrix[row][col] == 0){
					int size = getPondSize(matrix, row, col , visited);
					if(size > 0)
						sizes.add(size);
				}
			}
		}
		
			return sizes;
	}

	private int getPondSize(int[][] matrix, int row, int col, boolean[][] visited) {
		if(row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length
				|| visited[row][col] || matrix[row][col] != 0)
			return 0;
		
		visited[row][col] = true;
		int size = 1;
		for(int r= -1 ; r<= 1 ; r++){
			for(int c= -1 ; c<= 1 ; c++){
				size += getPondSize(matrix, row + r, col + c, visited);
			}
		}
		return size;
	}
	
	
	//16.20
	private char[][] key = {{},
					{},
					{'a','b','c'},
					{'d','e','f'},
					{'g','h','i'},
					{'j','k','l'},
					{'m','n','o'},
					{'p','q','r','s'},
					{'t','u','v'},
					{'w','x','y','z'}};
			
	public ArrayList<String> TPad(String digits, String[] validWords){
		ArrayList<String> words = new ArrayList<String>();
		makeWords(digits,validWords,"", words, 0);
		return words;
	}

	private void makeWords(String digits,String[] validWords, String soFar, ArrayList<String> words, int current) {
		if(soFar.length() >= digits.length()){
			if(wordisValid(soFar,validWords)){
				words.add(soFar);
			}
			return;
		}
		
		int currentDigit = Character.getNumericValue(digits.charAt(current));
		char[] keys = key[currentDigit];
		for(int curKey = 0 ; curKey < keys.length ; curKey++){
			makeWords(digits,validWords, soFar + keys[curKey], words, current + 1);
		}
		
	}

	private boolean wordisValid(String soFar, String[] validWords) {
		for(String s : validWords){
			if(s.equals(soFar))
				return true;
		}
		return false;
	}

	
	//16.21
	public Pair sumSwap(int[] arr1 , int[] arr2){
		int diff = getDifference(arr1,arr2);
		
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int i=0 ; i<arr1.length; i++){
			int val = diff + arr1[i];
			map.put(val, arr1[i]);
		}
		
		for(int j=0 ; j<arr2.length; j++){
			if(map.containsKey(arr2[j]))
				return new Pair(map.get(arr2[j]),arr2[j]);
		}
		return new Pair(-1,-1);
	}

	private int getDifference(int[] arr1, int[] arr2) {
		int sum1 = 0;
		int sum2 = 0;
		for(int i=0 ; i<arr1.length ; i++){
			sum1 += arr1[i];
		}
		
		for(int i=0 ; i<arr2.length ; i++){
			sum2 += arr2[i];
		}
		return (sum2 - sum1)/2;
	}
	
	//16.22
	public void printKMoves(int k){
		Board board = new Board(k*2);
		//initially at row 0, column 0.
		//initially facing right
		
		//next row and column to explore
		int row = k/2;
		int col = k/2;
		String currentDirection = "Right";
		while(k >= 0){
			k--;
			BoardSquare currentSquare = board.getSquare(row, col);
			if(currentSquare.squareType == SquareType.Black){
				switch(currentDirection){
					case "Up":{
						currentDirection = "Left";
						col--;
						break;
					}
					case "Down":{
						currentDirection = "Right";
						col++;
						break;
					}
					case "Left":{
						currentDirection = "Down";
						row++;
						break;
					}
					case "Right":{
						currentDirection = "Up";
						row--;
						break;
					}
					default:
						break;
				}
			}
			else{
				switch(currentDirection){
				case "Up":{
					currentDirection = "Right";
					col++;
					break;
				}
				case "Down":{
					currentDirection = "Left";
					col--;
					break;
				}
				case "Left":{
					currentDirection = "Up";
					row--;
					break;
				}
				case "Right":{
					currentDirection = "Down";
					row++;
					break;
				}
				default:
					break;
			}
			}
			currentSquare.flip();
		}
		System.out.println(board.toString());
	}
	
	
	//16.23
	private int rand5(){
		Random rand = new Random();
		return rand.nextInt(5);
	}
	
	public int rand7(){
		while(true){
			int num = 5 * rand5() + rand5();
			if(num < 21){
				return num % 7;
			}
		}
	}
	
	//16.24
	public ArrayList<Pair> pairsWithSum(int[] array, int sum){
		ArrayList<Pair> pairs = new ArrayList<Pair>();
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int i=0; i<array.length ; i++){
			int key = sum - array[i];
			if(map.getOrDefault(key, 0) > 0){
				pairs.add(new Pair(key, array[i]));
				map.put(key, map.get(key) - 1);
			}
			else{
				map.put(array[i], map.getOrDefault(key, 0) + 1);
			}
		}
		
		
		return pairs;
	}
	
	//16.26
	public double calculator(String s){
		while(true){
			if(s.contains("/")){
				s = doCalculation(s, '/');
			}
			else if(s.contains("*")){
				s = doCalculation(s, '*');
			
			} 
			else if(s.contains("+")){
				s = doCalculation(s, '+');
			}
			else if(s.contains("-")){
				s = doCalculation(s, '-');
			}
			else{
				break;
			}
			
		}
		
		return Double.parseDouble(s);
	}

	private String doCalculation(String s, char c) {
		int indexOfArithmetic = s.indexOf(c);
		int start = getStartIndex(s,indexOfArithmetic);
		int end = getEndIndex(s,indexOfArithmetic);
		double first = Double.parseDouble(s.substring(start, indexOfArithmetic));
		double second = Double.parseDouble(s.substring(indexOfArithmetic + 1, end));
		double middle = getMiddle(first, second, c);
		String left = s.substring(0,start);
		String right = s.substring(end);
		s = left+ middle + right;
		return s;
	}

	private double getMiddle(double first, double second, char c) {
		if(c == '*')
			return first * second;
		else if(c == '/')
			return first / second;
		else if(c == '+')
			return first + second;
		else if(c == '-')
			return first - second;
		return 0;
	}

	private int getEndIndex(String s, int index) {
		for(int i = index +1 ; i<s.length() ; i++){
			if(isArithmetic(s.charAt(i)))
				return i;
		}
		
		return s.length();
	}

	private int getStartIndex(String s, int index) {
		for(int i = index - 1 ; i>=0 ; i--){
			if(isArithmetic(s.charAt(i)))
					return i+1;
		}
		return 0;
	}

	private boolean isArithmetic(char charAt) {
		if(charAt == '*' || charAt == '/' || charAt == '-' || charAt == '+')
			return true;
		else
			return false;
	}
	
}
