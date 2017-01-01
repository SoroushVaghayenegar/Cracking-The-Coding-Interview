package hard;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class ContinuousMedianTest {
	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test_with_even_amount_of_numbers() {
		ContinuousMedian cm = new ContinuousMedian();
		int[] arr = new int[1000];
		Random rand = new Random();
		for(int i=0 ; i<arr.length ; i++){
			int num = rand.nextInt(2001);
			arr[i] = num;
			cm.insertNumber(num);
		}
		Arrays.sort(arr);
		double expd = (double)(arr[arr.length/2] + arr[(arr.length/2)-1])/2;
		assertEquals(expd,cm.getMedian(), 0.00);
	}
	
	@Test
	public void test_with_odd_amount_of_numbers() {
		ContinuousMedian cm = new ContinuousMedian();
		int[] arr = new int[9999];
		Random rand = new Random();
		for(int i=0 ; i<arr.length ; i++){
			int num = rand.nextInt(2001);
			arr[i] = num;
			cm.insertNumber(num);
		}
		Arrays.sort(arr);
		double expd = (double)arr[arr.length/2];
		assertEquals(expd,cm.getMedian(), 0.00);
	}

}
