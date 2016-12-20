package moderate;

public class Line {
	public Point point1;
	public Point point2;
	public int slope;
	public int b;
	public boolean x_intercept = false;
	
	public Line(Point p1, Point p2){
		this.point1 = p1;
		this.point2 = p2;
		if((p1.x - p2.x) != 0){
			this.slope = calculateSlope();
			this.b = calculateB();
		}
		else
			this.x_intercept = true;
	}
	
	public Line(int x){
		this.b = 0;
		this.slope = 1;
	}

	private int calculateB() {
		return (slope * -(point1.x)) + point1.y;
	}

	private int calculateSlope() {
		
		return (point2.y - point1.y)/(point2.x - point1.x);
	}
	
	public boolean isPointInLine(Point p){
		return p.y == (p.x * this.slope + this.b);
	}
	
	public String toString(){
		if(x_intercept)
			return "x = " + point1.x;
		return "y = " + slope + "x " + b;
	}
}
