package draw3D;

import java.awt.Point;

public class Line {

	private Point point1;
	private Point point2;
	
	public Line(Point point1,Point point2) {
		this.point1=point1;
		this.point2=point2;
	}
	
	public Point getPoint1() {
		return point1;
	}
	public Point getPoint2() {
		return point2;
	}
	
	public double getSize(){
		int x = point2.x - point1.x;
		int y = point2.y - point1.y;
		return Math.sqrt(x*x + y*y);
	}
}
