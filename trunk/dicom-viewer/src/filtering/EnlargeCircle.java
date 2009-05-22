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

import sun.security.provider.certpath.Vertex;
import ui.HandImage;
import ui.MainFrame;

public class EnlargeCircle extends Thread{
	
	int radio;
	BufferedImage image;
	Vector<Point> points;
	HandImage handImage;
	Vector<Point> newCircle = new Vector<Point>();
	Vector<Line> normales = new Vector<Line>();

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
	private void enlargePoint2(Point p1,Point p2,Point p3){
		Point set = new Point (p2.x,p2.y);
		int color = image.getRGB(p2.x,p2.y);
		int newColor = color;
		Point normal= getVectorNormal(p1,p2,p3);
		if((normal.x!=0)||(normal.y!=0)){
			boolean toLong=false;
			Point ant = null;
			Line antL = null;
			if(normales.size()>0){
				ant = newCircle.get(newCircle.size()-1);
				antL= normales.get(normales.size()-1);
			}
			int count=0;
			
			while ((color==newColor)&&(!toLong)){	
				set.y+=normal.y;
				set.x+=normal.x;
				count++;
				normal = getVectorNormal(p1,set,p3);
				if ((count>40)&&(ant!=null)&&(getDistance(p2, set)>20)){
					toLong=true;
					set = getAdd(p2,antL,normal);
				}
			
				try{
					newColor = image.getRGB(set.x,set.y);
				}catch (Exception e){
					newColor = color-1;
				}
				if(color!=newColor){
					
					if((antL!=null)&&((antL.getSize()- getDistance(p2, set))>10)&&((antL.getSize()- getDistance(p2, set))<60)){
						color=newColor;
						
						while ((color==newColor)){
							set.y+=normal.y;
							set.x+=normal.x;
							count++;
							normal = getVectorNormal(p1,set,p3);
							try{
								newColor = image.getRGB(set.x,set.y);
							}catch (Exception e){
								newColor = color-1;
							}
						}
						color=newColor;		
					}
				}
			}
		}

		newCircle.add(set);
		normales.add(new Line(p2,set));
		
	}
	
	
	
	//nuevo
	private void enlargePoint(Point p1,Point p2,Point p3){
		Point set = new Point (p2.x,p2.y);
		int color = image.getRGB(p2.x,p2.y);
		int newColor = color;
		Point normal= getVectorNormal(p1,p2,p3);
		boolean stop=false;
		Vector<Point> blackPoints = new Vector<Point>();
		Point ant = null;
		Line antL = null;
		if((normal.x!=0)||(normal.y!=0)){
			if(normales.size()>0){
				ant = newCircle.get(newCircle.size()-1);
				antL= normales.get(normales.size()-1);
			}
			
			while ((color==newColor)&&(!stop)){	
				set.y+=normal.y;
				set.x+=normal.x;
				normal = getVectorNormal(p1,set,p3);
				try{
					newColor = image.getRGB(set.x,set.y);
				}catch (Exception e){
					stop=true;
				}
				if(color!=newColor){
					blackPoints.add(new Point (set.x,set.y));
				
					color=newColor;
					
					while ((color==newColor)&&(!stop)){
						set.y+=normal.y;
						set.x+=normal.x;
						normal = getVectorNormal(p1,set,p3);
						try{
							newColor = image.getRGB(set.x,set.y);
						}catch (Exception e){
							stop=true;				
						}
					}
					color=newColor;		
				}
				
			}
		}
		set = selectPoint(ant,antL,blackPoints,p2,normal);
		newCircle.add(set);
		normales.add(new Line(p2,set));
		
	}

	private Point selectPoint(Point ant, Line antL, Vector<Point> blackPoints,Point p2,Point normal) {
		if(ant==null)
			return blackPoints.get(0);
		
		Point ret = getAdd(p2, antL, normal);
		
		double dist;
		double distMin=Double.MAX_VALUE;
		for (Iterator iterator = blackPoints.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			dist = getDistance(ant, point);
			if (dist<distMin){
				distMin=dist;
				ret = point;
			}
		}
//		if((p2.x==262)&&(p2.y==100)){
//			double a= Math.abs(getDistance(p2, ret)-antL.getSize());
//			System.out.println("Superdistancia: "+a);
//			if((Math.abs(getDistance(p2, ret)-antL.getSize())>20)){//||Math.abs(getDistance(p2, ret)-antL.getSize())<5){
//			System.out.println("Le sumoooo");
//			}
//		}
		if((Math.abs(getDistance(p2, ret)- antL.getSize())>18)){//||Math.abs(getDistance(p2, ret)-antL.getSize())<5){
			ret = getAdd(p2, antL, normal);
			
		}
			
		return ret;
		
	}
	private Point getAdd(Point set, Line antL, Point normal) {
		Point ret = new Point();
		ret.x=set.x;
		ret.y=set.y;
		int l = (int) antL.getSize();
		while (l>0){
			ret.x+=normal.x;
			ret.y+=normal.y;
			l--;
		}
		return ret;
	}
	private Point enlarge(Point p1,Point p2,Point p3){
		Point normal=null;
		Point set=null;
		normal= getVectorNormal(p1,p2,p3);
		set=new Point(p2.x,p2.y);
		set.y+=normal.y;
		set.x+=normal.x;
		return set;
		
	}
	public void run(){

		
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
			
			
			if (handImage!=null){
				handImage.setNormales(normales);
				handImage.setCirclePointsEnlarge(newCircle);
				handImage.setImage();
				}
		}
	
	public Point getNormal(Point p1,Point p2){
		int vx = p2.x - p1.x;
		int vy = p2.y - p1.y;
		
		return new Point(-vy,vx);
	}
	public Point getVectorNormal(Point p1,Point p2,Point p3){
		
		Point aux1= getNormal(p1, p2);
		
		Point aux2= getNormal(p2, p3);
		Point ret =new Point((aux1.x+aux2.x)/2,(aux1.y+aux2.y)/2);
		
		double modulo = Math.sqrt((ret.x*ret.x + ret.y*ret.y));
		double xx =ret.x/modulo;
		double yy=ret.y/modulo;
		if (xx>=0.5)
			xx=1;
		if (yy>=0.5)
			yy=1;
		if (xx<=-0.5)
			xx=-1;
		if (yy<=-0.5)
			yy=-1;
		return new Point((int)xx,(int)yy);
		
		
	}
	
	
}

