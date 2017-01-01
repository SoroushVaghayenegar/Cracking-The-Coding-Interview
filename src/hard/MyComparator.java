package hard;

import java.util.Comparator;

public class MyComparator implements Comparator<Integer> {

	@Override
	public int compare(Integer a, Integer b) {
		return b.compareTo(a);
	}

}
