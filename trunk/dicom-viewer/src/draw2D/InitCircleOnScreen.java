package draw2D;

import java.awt.Color;
import java.awt.Point;
import java.util.Vector;

public class InitCircleOnScreen extends CircleOnScreen{

	public InitCircleOnScreen(Vector<Point> circlePoints, Color red) {
		super(circlePoints,red);
	}

	public InitCircleOnScreen() {
		super();
	}

	public void addPointToInitCircle(Point p) {
		if(circlePoints.size()>0){
			addCirclesPointsBetween(circlePoints.get(circlePoints.size()-1),p,10);
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

}
