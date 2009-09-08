package draw2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import data.VisualData;

public class CircleOnScreen {
	 public Vector<Point> circlePoints = new Vector<Point>();
	 private Color color = Color.RED;
	 
	 public CircleOnScreen(Vector<Point> circlePoints,Color color) {
		this.circlePoints=circlePoints;
		this.color=color;
	}
	 
	 public Point getPoint(int index){
		 return circlePoints.get(index);
	 }
	 public CircleOnScreen() {}
	 
	 public void setColor(Color color) {
		this.color = color;
	}
	 public void setCirclePoints(Vector<Point> circlePoints) {
		this.circlePoints = circlePoints;
	}

	public void draw(Graphics g){
		
				int x=0;
				int y=0;
				int x2=0;
				int y2=0;
				for (int i = 0; i < circlePoints.size(); i++) {
					
					g.setColor(color);
					Point p = circlePoints.get(i);
					x=(int) p.getX();
					y=(int) p.getY();
					if(i==(circlePoints.size()-1)){
						Point p2 = circlePoints.get(0);
						x2=(int) p2.getX();
						y2=(int) p2.getY();
					}else{
						if(circlePoints.size()>1){
							Point p2 = circlePoints.get(i+1);
							x2=(int) p2.getX();
							y2=(int) p2.getY();
						}
					}
					
					g.fillOval(x-3,y-3, 7, 7);
					g.drawLine(x, y, x2, y2);
				}
			
	 }
	 public Vector<Point> getCirclePoints() {
		return circlePoints;
	}
	
	 public int getCantPoints(){
		 return circlePoints.size();
	 }

	public Color getColor() {
		return color;
	}
	 
}
