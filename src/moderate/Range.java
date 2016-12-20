package moderate;

public class Range {
	private int lower;
	private int higher;
	
	public Range(int lower, int higher){
		this.lower = lower;
		this.higher = higher;
	}
	
	public String toString(){
		return "(" + lower + ", " + higher + ")";
	}
}
