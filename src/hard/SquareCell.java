package hard;

public class SquareCell {
	public int onesRight = 0;
	public int onesBelow = 0;
	
	public SquareCell(int onesRight, int onesBelow){
		this.onesRight = onesRight;
		this.onesBelow = onesBelow;
	}
	
	public int getOnesRight() {
		return onesRight;
	}
	public void setOnesRight(int onesRight) {
		this.onesRight = onesRight;
	}
	public int getOnesBelow() {
		return onesBelow;
	}
	public void setOnesBelow(int onesBelow) {
		this.onesBelow = onesBelow;
	}
}
