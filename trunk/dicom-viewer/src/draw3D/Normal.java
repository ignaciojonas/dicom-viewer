package draw3D;

import java.awt.Point;

public class Normal {
private double x;
private double y;

public Normal(double x, double y) {
	super();
	this.x = x;
	this.y = y;
}

public Point getNormalAdd(Point p, int avance){ 
	double xx=p.x+(x*avance);
	double yy=p.y+(y*avance);
	return new Point((int)Math.round(xx),(int)Math.round(yy));
}

public Point getNormalSubtract(Point p, int avance){ 
	double xx=p.x-(x*avance);
	double yy=p.y-(y*avance);
	int xxx=(int) Math.round(xx);
	int yyy=(int) Math.round(yy);
	return new Point((int)Math.round(xx),(int)Math.round(yy));
}
}
