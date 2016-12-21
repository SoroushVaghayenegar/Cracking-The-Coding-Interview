package hard;

public class HtWt implements Comparable{
	private int height;
	private int weight;
	
	
	public HtWt(int height, int weight){
		this.height = height;
		this.weight = weight;
	}


	public int getHeight() {
		return height;
	}


	public int getWeight() {
		return weight;
	}
	
	public boolean isBefore(HtWt another){
		return (height < another.getHeight() && weight < another.getWeight());
	}
	
	public String toString(){
		return "(" + this.height + ", " + this.weight + ")";
	}
	@Override
	public int compareTo(Object anotherHtWt) {
		int anotherHeight = ((HtWt) anotherHtWt).getHeight();
		return this.height - anotherHeight;
	}
}
