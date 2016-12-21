package hard;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	

}
