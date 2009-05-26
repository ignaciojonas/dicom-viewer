package filtering;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.SwingUtilities;
import javax.swing.text.Segment;

import data.ImagesData;
import data.VisualData;
import draw3D.Line;
import draw3D.Normal;

import sun.security.provider.certpath.Vertex;
import ui.HandImage;
import ui.MainFrame;

public class EnlargeCircle {
	
	int radio;
	BufferedImage image;
	Vector<Point> points;
	HandImage handImage;
	Vector<Point> newCircle = new Vector<Point>();
	Vector<Line> normales = new Vector<Line>();

	public Vector<Point> getNewCircle() {
		return newCircle;
	}
	public Vector<Line> getNormales() {
		return normales;
	}
	public EnlargeCircle(HandImage handImage, int radio,BufferedImage image,Vector<Point> points) {
		this.radio=radio;
		this.image=image;
		this.handImage=handImage;
		this.points=points;
	}
	private double getDistance(Point point1, Point point2){
		int x = point2.x - point1.x;
		int y = point2.y - point1.y;
		return Math.sqrt(x*x + y*y);
	}
	
	//nuevo
	private void enlargePoint(Point p1,Point p2,Point p3){
		System.out.println("Comienzo: "+p2.x+":"+p2.y);
		if((p2.x==374)&&(p2.y==242)){
			System.out.println("Caso Particular");
		}
		if((p2.x==374)&&(p2.y==241)){
			System.out.println("Caso Particular");
		}
		Point set = new Point (p2.x,p2.y);
		Point antSet;//Anterior Avance
		int color = image.getRGB(p2.x,p2.y);
		int newColor = color;
		Normal normal= getVectorNormal(p1,p2,p3);
		boolean stop=false;
		Vector<Point> blackPoints = new Vector<Point>();
		Point ant = null;
		int i=0;
		if(newCircle.size()>0){
			ant = newCircle.get(newCircle.size()-1);
		}
		while ((color==newColor)&&(!stop)){
			i++;
			if(i==10000){ //POR SI ME QUEDO EN LOOP
				System.out.println("Me quedo en loop en Enlarge");
			}
			antSet=(Point) set.clone();//Guardo el anterior
			set=normal.getNormalAdd(p2,i);//Calculo el nuevo
			try{
				newColor = image.getRGB(set.x,set.y);
			}catch (Exception e){
				stop=true;
			}
			
			if(color!=newColor){
				blackPoints.add(new Point (set.x,set.y));
				color=newColor;
				while ((color==newColor)&&(!stop)){//Avanzo por el negro
					i++;
					antSet=(Point) set.clone();//Guardo el anterior
					set=normal.getNormalAdd(p2,i);//Calculo el nuevo
					if(antSet.equals(set)){//si el punto anterior es igual al actual
						//stop=true;
					}
					try{
						newColor = image.getRGB(set.x,set.y);
					}catch (Exception e){
						stop=true;				
					}
				}
				color=newColor;		
			}
			if(antSet.equals(set)){//si el punto anterior es igual al actual
			//	stop=true;
			}
			
		}

		set = selectPoint(ant,blackPoints,p2,normal);
		newCircle.add(set);
		normales.add(new Line(p2,set));
		System.out.println("Termine: "+p2.x+":"+p2.y);
	}

	private Point selectPoint(Point ant, Vector<Point> blackPoints,Point p2,Normal normal) {
		if((p2.x==325)&&(p2.y==99)){
			System.out.println("Caso Particular");
		}
		if(ant==null)
			return blackPoints.get(0);
		
		Point ret = normal.getNormalAdd(p2,1); //Inicializo con algo por las dudas.
		
		double dist;
		double distMin=Double.MAX_VALUE;
		//Recorro los puntos negros en busca del que cumpla con la minima distancia
		//entre el punto anterior y yo
		for (Iterator iterator = blackPoints.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			dist = getDistance(ant, point);
			if (dist<distMin){
				distMin=dist;
				ret = point;
			}
		}
		Line lin=normales.get(normales.size()-1);
		double dis=lin.getSize()-Math.abs(getDistance(p2, ret));
		//ACA si la distancia es mucha, entonces le pongo el anterior en la direccion de la normal
		if(Math.abs(dis)>20){
			int i=1;
			Line l=normales.get(normales.size()-1);
			ret=(Point) p2.clone();
			double sizeL=l.getSize();
			double sizeN=getDistance(p2, ret);
			while(sizeN<=sizeL){
				ret = normal.getNormalAdd(p2, i);
				sizeN=getDistance(p2, ret);
				i++;
			}	
			
		}
		//ACA si no existe ningun negro, entonces le pongo el anterior  en la direccion de la normal
		if((blackPoints.size()==0)){
			int i=1;
			Line l=normales.get(normales.size()-1);
			ret=p2;
			double sizeL=l.getSize();
			double sizeN=getDistance(p2, ret);
			while(sizeN<=sizeL){
				ret = normal.getNormalAdd(p2, i);
				sizeN=getDistance(p2, ret);
				i++;
			}	
		}
		//QUE NO CREZCA MAS QUE 30
		if(Math.abs(getDistance(p2, ret))>50){
			int i=1;
			ret=(Point) p2.clone();
			double sizeN=getDistance(p2, ret);
			while(sizeN<=30){
				ret = normal.getNormalAdd(p2, i);
				sizeN=getDistance(p2, ret);
				i++;
			}
		}
		//ACA si la distancia es mucha, entonces le pongo el anterior en la direccion de la normal
		if(Math.abs(dis)>20){
				
			
		}
			
		return ret;
		
	}


	public Vector<Point> run(){

		
		Point normal=null;
		Point set=null;
			int size = points.size();
			if (size>3)
				for (int i = 0; i < size; i++) {
					
					if ((size-i)>2){
						enlargePoint(points.get(i+2),points.get(i+1),points.get(i));
						
					}else{
						if ((size-i)==2){
							enlargePoint(points.get(0),points.get(i+1),points.get(i));
							
						}else{
							if((size-i)==1){
								enlargePoint(points.get(1),points.get(0),points.get(i));
								
							}
						}
					}
					
					
				}

			return newCircle;
			

		}
	
	public Point getNormal(Point p1,Point p2){
		int vx = p2.x - p1.x;
		int vy = p2.y - p1.y;
		
		return new Point(-vy,vx);
	}
	public Normal getVectorNormal(Point p1,Point p2,Point p3){

		Point aux1= getNormal(p1, p2);
		  
		Point aux2= getNormal(p2, p3);
		Point ret =new Point((aux1.x+aux2.x)/2,(aux1.y+aux2.y)/2);
		
		double modulo = Math.sqrt((ret.x*ret.x + ret.y*ret.y));
		double xx =ret.x/modulo;
		double yy=ret.y/modulo;
//		if (xx>=0.5)
//			xx=1;
//		if (yy>=0.5)
//			yy=1;
//		if (xx<=-0.5)
//			xx=-1;
//		if (yy<=-0.5)
//			yy=-1;
		return new Normal(xx,yy);
		
		
	}
	
	
}

