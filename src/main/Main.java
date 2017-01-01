package main;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import hard.Hard;


public class Main {

	public static void main(String[] args) {
		Hard h = new Hard();
		HashSet<String> dictionary = new HashSet<String>();
		dictionary.add("DAMP");
		dictionary.add("LAMP");
		dictionary.add("LIMP");
		dictionary.add("LIME");
		dictionary.add("LIKE");
		
		h.wordTransformer("DAMP", "LIKE", dictionary);
	}

}
