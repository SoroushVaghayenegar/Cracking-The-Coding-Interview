package hard;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HardTests {

	Hard h = new Hard();

	@Test
	public void test_add_function() {
		//one zero
		assertEquals(10, h.add(0, 10));
		assertEquals(10, h.add(10, 0));
		
		//both positive
		assertEquals(20, h.add(10, 10));
		
		//both zero
		assertEquals(0, h.add(0, 0));
		
		//both negative
		assertEquals(-110, h.add(-100, -10));
		
		//alternate + -
		assertEquals(-2413, h.add(-2523, 110));
		assertEquals(190, h.add(200, -10));
	}
	
	@Test
	public void test_MissingNumber_function() {
		ArrayList<BitInteger> arrayOdd = new ArrayList<BitInteger>();
		ArrayList<BitInteger> arrayEven = new ArrayList<BitInteger>();
		for(int i=0 ; i<100 ; i++){
			if(i != 53){
				arrayEven.add(new BitInteger(i));
			}
		}
		
		for(int i=0 ; i<201 ; i++){
			if(i != 152){
				arrayOdd.add(new BitInteger(i));
			}
		}
		
		assertEquals(53, h.missingNumber(arrayEven));
		assertEquals(152, h.missingNumber(arrayOdd));
	}
	
	@Test
	public void test_getLongestSubarray_function() {
		char[] toTest = {'A','6','7','B','2','C','F','T','2','9','3','Y','Q'};
		char[] expected = {'6','7','B','2','C','F','T','2','9','3','Y', 'Q'};
		Assert.assertArrayEquals(expected, h.getLongestSubarray(toTest));
	}
	
	@Test
	public void test_countTwos_function() {
		assertEquals(1, h.countTwos(5));
		assertEquals(3, h.countTwos(20));
		assertEquals(20, h.countTwos(100));
		assertEquals(300, h.countTwos(1000));
	}
	
	
	
	
	@Test
	public void test_kthNumber_function() {
		assertEquals(1, h.kthNumber(1));
		assertEquals(5, h.kthNumber(3));
		assertEquals(21, h.kthNumber(7));
	}
	
	
	@Test
	public void test_getMajority_function() {
		int[] test = new int[20];
		for(int i=0 ; i<20 ; i++){
			test[i] = i;
		}
		assertEquals(-1, h.getMajority(test));
		
		for(int i=5 ; i<14 ; i++){
			test[i] = 4;
		}
		assertEquals(-1, h.getMajority(test));
		
		for(int i=5 ; i<16 ; i++){
			test[i] = 4;
		}
		assertEquals(4, h.getMajority(test));
		
		for(int i=1 ; i<9 ; i++){
			test[i] = 2;
		}
		assertEquals(-1, h.getMajority(test));
	}
	
	@Test
	public void test_doublyLinkedList_function() {
		int[] arr = {1,2,3,9,12,25,35,40,100,150};
		BiNode root = makeTree(arr, 0, arr.length-1);
		
		BiNode doublyLinkedList = h.fromTreeToDoublyLinkedList(root);
		
		int index = 0;
		while(doublyLinkedList != null){
			assertEquals(arr[index],doublyLinkedList.data);
			index++;
			doublyLinkedList = doublyLinkedList.node2;
		}
		assertEquals(arr.length,index);
	}

	

	private BiNode makeTree(int[] arr, int low, int high) {
		if(high < low)
			return null;
		int mid = (high + low)/2;
		BiNode root = new BiNode();
		root.data = arr[mid];
		root.node1 = makeTree(arr,low,mid-1);
		root.node2 = makeTree(arr,mid+1,high);
		return root;
	}
	
	
	@Test
	public void test_reSpace_function() {
		String doc = "jesslookedjustliketimherbrother";
		HashSet<String> dictionary = new HashSet<String>();
		dictionary.add("looked");
		dictionary.add("just");
		dictionary.add("like");
		dictionary.add("her");
		dictionary.add("brother");
		
		String expected = "jess looked just like tim her brother";
		assertEquals(expected ,h.reSpace(doc, dictionary, 0));
	}
	
	
	@Test
	public void test_smallestKNumbers_function() {
		int[] arr1 = {12,5,9,2,1,7,3,10,4,6,11,8};
		int[] expected1 = {5,2,1,7,3,4,6};
		Assert.assertArrayEquals(expected1, h.smallestKNumbers(arr1, 7));
	}
	
	@Test
	public void test_longestWord_function() {
		String[] words = {"cat",
				 		  "banana",
				 		  "dog",
				 		  "nana",
				 		  "walk",
				 		  "walker",
				 		  "dogwalker"};
		
		
		assertEquals("dogwalker", h.longestWord(words));
	}
	
	@Test
	public void test_getOptimalSet_function() {
		int[] appointments = {30, 15, 60, 75, 45, 15, 15, 45};
		assertEquals(180, h.getOptimalSet(appointments));
	}
}
