package draw2D.snakes;

import java.awt.Point;

public class Gradient {

	private PointF p;
	private double gmod;
	public Gradient(PointF p, double dmod) {
		super();
		this.p = p;
		this.gmod = dmod;
	}
	public PointF getP() {
		return p;
	}
	public void setP(PointF p) {
		this.p = p;
	}
	public double getGmod() {
		return gmod;
	}
	public void setGmod(double dmod) {
		this.gmod = dmod;
	}
	
	
}
