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
	
	private Point centro;
	public InitCircleOnScreen(Vector<Point> circlePoints, Color red) {
		super(circlePoints,red);
		calculateCenter();
	}
	 public void setCirclePoints(Vector<Point> circlePoints) {
			this.circlePoints = circlePoints;
			calculateCenter();
		}
	public Point getCentro() {
		return centro;
	}
	private Point getCenter() {
		
		int x = x_min +((x_max - x_min)/2);
		int y = y_min +((y_max - y_min)/2);
		
		return new Point(x,y);
	}

	public InitCircleOnScreen() {
		super();
	}

	public void addPointToInitCircle(Point p) {
		if(circlePoints.size()>0){
			addCirclesPointsBetween(circlePoints.get(circlePoints.size()-1),p,StaticInitCircle.POINTSBETWEEN);
		}
		circlePoints.add(p);
		calculateCenter();
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
			calculateCenter();
		}
		if((x>x_min)&&(x<x_max)&&(y>y_min)&&(y<y_max)){
			return true;
		}
		return false;
	}

	public void calculateCenter() {
	
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
		centro = getCenter();
		
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

	private boolean contatinPoint(Point o,Point p){
		if((o.x < (p.x+3))&&(o.x > (p.x-3)))
			if((o.y < (p.y+3))&&(o.y > (p.y-3)))
				return true;
		return false;
	}
	public int containts(Point point) {
		Point aux;
		for(int i = 0;i<circlePoints.size();i++){
			aux = circlePoints.get(i);
			if (contatinPoint(aux, point))
				return i;
		}
		return -1;
	}
	
	public Point getPoint(int index){
		return circlePoints.get(index);
	}

	public Point getLeftIndex(int indexMoveOP) {
		int size = circlePoints.size();
		
		int index=indexMoveOP-1;
		if((index>=0))
				return circlePoints.get(index);
			else if(index < 0)
				return circlePoints.get(size-1);
		
			
		return null;
		}
		
	public Point getRightIndex(int indexMoveOP) {
		int size = circlePoints.size();
		
		int index=indexMoveOP+1;
		if((index<size))
				return circlePoints.get(index);
			else 
				return circlePoints.get(0);
		
			
	
		}
	
	
	public Vector<Integer> getLeftIndex(int indexMoveOP, int total) {
		int size = circlePoints.size();
		Vector<Integer> ret = new Vector<Integer>();
		int index=0;
		int resta =1;
		for(int j=1;j<=total;j++){
			index = indexMoveOP-j;
			if((index>=0))
				ret.add(index);
			else if(index < 0){
				ret.add(size-resta);
				resta++;
			}
				
		}
		return ret;
	}

	public Vector<Integer> getRightIndex(int indexMoveOP, int total) {
		int size = circlePoints.size();
		Vector<Integer> ret = new Vector<Integer>();
		int index=0;
		int suma =0;
		for(int j=1;j<=total;j++){
			index = indexMoveOP+j;
			if((index<size))
				ret.add(index);
			else if(index >= size){
				ret.add(suma);
				suma++;
			}
				
		}
		return ret;
	}
}
