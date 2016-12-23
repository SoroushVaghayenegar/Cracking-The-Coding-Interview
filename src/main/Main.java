package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import hard.Hard;
import hard.HtWt;
import moderate.Board;
import moderate.Color;
import moderate.Line;
import moderate.Moderate;
import moderate.Pair;
import moderate.PlankOfWood;
import moderate.Point;
import moderate.PlankOfWood.PlankType;

public class Main {

	public static void main(String[] args) {
		Hard h = new Hard();
		int[] arr1 = new int[10000];
		Random rand = new Random();
		for(int i=0 ; i<10000 ; i++){
			arr1[i] = rand.nextInt(100000);
		}
		
		System.out.println(Arrays.toString(h.smallestKNumbers(arr1, 50)));
	}

}
