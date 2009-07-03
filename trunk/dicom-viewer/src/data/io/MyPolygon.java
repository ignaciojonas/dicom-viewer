package data.io;




import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import draw3D.scenario.screen.Point3D;



public class MyPolygon{

	private Point3D p1;
	private Point3D p2;
	private Point3D p3;
	int point1;
	int point2;
	int point3;

	public MyPolygon(int p1,int p2,int p3) {
		point1=p1;
		point2=p2;
		point3=p3;
	}

	public Point3D getP1() {
		return p1;
	}

	public Point3D getP2() {
		return p2;
	}
	
	public Point3D getP3() {
		return p3;
	}
	
	public MyPolygon(Point3D p1, Point3D p2, Point3D p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
	}
	

	public int getInt1() {
		return point1;
	}

	public int getInt2() {
		return point2;
	}

	public int getInt3() {
		return point3;
	}
	
	public void setP1(Point3D p1) {
		this.p1 = p1;
	}

	public void setP2(Point3D p2) {
		this.p2 = p2;
	}

	public void setP3(Point3D p3) {
		this.p3 = p3;
	}

}
