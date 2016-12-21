package main;

import java.util.ArrayList;
import java.util.Arrays;

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
		ArrayList<HtWt> people = new ArrayList<HtWt>();
		people.add(new HtWt(65,100));
		people.add(new HtWt(70,150));
		people.add(new HtWt(56,90));
		people.add(new HtWt(75,190));
		people.add(new HtWt(60,95));
		people.add(new HtWt(68,110));
		
		System.out.println(Arrays.toString(h.circusTower(people).toArray()));
	}

}
