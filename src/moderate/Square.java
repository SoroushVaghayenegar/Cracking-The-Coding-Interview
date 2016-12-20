package moderate;

public class Square {
	
	public Point topLeft;
	public Point topRight;
	
	public Point bottomLeft;
	public Point bottomRight;
	
	public Square(Point topLeft, Point topRight, Point bottomLeft, Point bottomRight){
		this.topLeft = topLeft;
		this.topRight = topRight;
		this.bottomLeft = bottomLeft;
		this.bottomRight = bottomRight;
	}
}
