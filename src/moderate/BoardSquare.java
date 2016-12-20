package moderate;

public class BoardSquare {
	public enum SquareType{
		White, Black;
	}
	
	public SquareType squareType;
	
	public BoardSquare(SquareType st){
		this.squareType = st;
	}
	
	public String toString(){
		return squareType == SquareType.Black ? " B " : "   ";
	}
	
	public void flip(){
		squareType = squareType == SquareType.Black ? SquareType.White:
													  SquareType.Black;
	}
}
