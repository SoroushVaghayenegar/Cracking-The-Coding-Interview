package moderate;

import moderate.BoardSquare.SquareType;

public class Board {
	private BoardSquare[][] squares;
	public int row;
	public int column;
	
	public Board(int n){
		this.squares = new BoardSquare[n][n];
		this.row = n;
		this.column = n;
		buildBoard();
	}

	private void buildBoard() {
		for(int i=0 ; i<squares.length ; i=i+2){
			for(int j=0 ; j<squares[0].length ; j++){
				if(j % 2 == 0)
					squares[i][j] = new BoardSquare(SquareType.Black);
				else
					squares[i][j] = new BoardSquare(SquareType.White);
			}
		}
		
		for(int i=1 ; i<squares.length ; i=i+2){
			for(int j=0 ; j<squares[0].length ; j++){
				if(j % 2 == 0)
					squares[i][j] = new BoardSquare(SquareType.White);
				else
					squares[i][j] = new BoardSquare(SquareType.Black);
			}
		}
	}
	public BoardSquare getSquare(int row, int col){
		return squares[row][col];
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i=0 ; i<squares[0].length ; i++){
			sb.append("----");
		}
		sb.append("\n");
		for(BoardSquare[] row : squares){
			for(BoardSquare b : row){
				sb.append("|");
				sb.append(b.toString());
				
			}
			sb.append("|");
			sb.append("\n");
			for(int i=0 ; i<squares[0].length ; i++){
				sb.append("----");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
