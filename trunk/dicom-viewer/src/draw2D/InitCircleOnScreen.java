package draw2D;

import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;
import java.util.Vector;

import data.ImagesData;

public class InitCircleOnScreen extends CircleOnScreen{

	private int x_min=Integer.MAX_VALUE;
	private int x_max=Integer.MIN_VALUE;
	private int y_min=Integer.MAX_VALUE;
	private int y_max=Integer.MIN_VALUE;
	
	public InitCircleOnScreen(Vector<Point> circlePoints, Color red) {
		super(circlePoints,red);
	}

	public InitCircleOnScreen() {
		super();
	}

	public void addPointToInitCircle(Point p) {
		if(circlePoints.size()>0){
			addCirclesPointsBetween(circlePoints.get(circlePoints.size()-1),p,ImagesData.POINTSBETWEEN);
		}
		circlePoints.add(p);
	}


	public void addCirclesPointsBetween(Point p1, Point p2,int Max){
		int contar=0;
		int fin=0;
		int inicio=0;
		int sumar=0;

		if(p1.y==p2.y){
			if(p1.x>p2.x){
				inicio=0;
				fin=p2.x-p1.x;
				sumar=fin/Max;
				if(sumar==0){
					sumar=-1;
				}
				double y=0;
				int x=0;
				Point p;
				int i=inicio;
				while(i>fin){
					
					x=p1.x+i;
					y=(((x-p1.x)*(p1.y-p2.y))/(p1.x-p2.x))+p1.y;
					p=new Point(x,(int)y);
					if(!circlePoints.contains(p))
						circlePoints.add(p);

					i+=sumar;
				}

			}
			else{
				inicio=1;
				fin=p2.x-p1.x;
				sumar=fin/Max;
				if(sumar==0){
					sumar=1;
				}
				double y=0;
				int x=0;
				Point p;
				int i=inicio;
				while(i<fin){
					
					x=p1.x+i;
					y=(((x-p1.x)*(p1.y-p2.y))/(p1.x-p2.x))+p1.y;
					p=new Point(x,(int)y);
					if(!circlePoints.contains(p))
						circlePoints.add(p);

					i+=sumar;
				}
			
			}
				}
		else{
			if(p1.y>p2.y){
				inicio=1;
				fin=-1*(p2.y-p1.y);
				sumar=fin/Max;
				
				if(sumar==0){
					sumar=1;
				}
				int y=0;
				double x=0;
				Point p;
				int i=inicio;
				while(i<fin){
					
					y=p1.y-i;
					x=(((y-p1.y)*(p1.x-p2.x))/(p1.y-p2.y))+p1.x;
					p=new Point((int) x,y);
					circlePoints.add(p);
					
					i+=sumar;
				}
				
				
			}
			else{
				inicio=1;
				fin=p2.y-p1.y;
				sumar=fin/Max;
			
				if(sumar==0){
					sumar=1;
				}
				int y=0;
				double x=0;
				Point p;
				int i=inicio;
				while(i<fin){
					
					y=p1.y+i;
					x=(((y-p1.y)*(p1.x-p2.x))/(p1.y-p2.y))+p1.x;
					p=new Point((int) x,y);
					circlePoints.add(p);
					
					i+=sumar;
				}
			}
			
		}
		
	}
	
	public boolean isSelected(int x, int y){
		if(x_min==Integer.MAX_VALUE){
			for (Iterator iterator = circlePoints.iterator(); iterator.hasNext();) {
				Point p = (Point) iterator.next();
				if(p.x<x_min){
					x_min=p.x;
				}
				else{
					if(p.x>x_max){
						x_max=p.x;
					}
				}
				if(p.y<y_min){
					y_min=p.y;
				}
				else{
					if(p.y>y_max){
						y_max=p.y;
					}
				}
			}
		}
		if((x>x_min)&&(x<x_max)&&(y>y_min)&&(y<y_max)){
			return true;
		}
		return false;
	}
	
	public void sumarXY(int x, int y){
		for (Iterator iterator = circlePoints.iterator(); iterator.hasNext();) {
			Point p = (Point) iterator.next();
			int nuevoX=p.x+x;
			int nuevoY=p.y+y;
			p.setLocation(nuevoX, nuevoY);
		}
	}
	public int getX_min() {
		return x_min;
	}

	public void setX_min(int x_min) {
		this.x_min = x_min;
	}

	public int getX_max() {
		return x_max;
	}

	public void setX_max(int x_max) {
		this.x_max = x_max;
	}

	public int getY_min() {
		return y_min;
	}

	public void setY_min(int y_min) {
		this.y_min = y_min;
	}

	public int getY_max() {
		return y_max;
	}

	public void setY_max(int y_max) {
		this.y_max = y_max;
	}
}
