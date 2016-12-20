package hard;

public class BitInteger {
	
	private int value;
	public static int INTEGER_SIZE = 32;
	
	public BitInteger(int value){
		this.value = value;
	}
	
	public void setJthBitOne(int j){
		int mask = 1<<j;
		value |= mask;
	}
	
	public void setJthBitZero(int j){
		int mask = 1<<j;
		value &= ~mask;
	}
	
	public int fetchTheJthBit(int j){
		int num = value;
		int mask = 1<<j;
		num &= mask;
		return num >> j;
	}
	
	public int toInt(){
		return value;
	}
}
