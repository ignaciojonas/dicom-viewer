package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import data.ImagesData;
import data.VisualData;
import data.io.DicomProperties;
import draw3D.Line;
import draw3D.Mesh;
import draw3D.scenario.screen.Point3D;
import filtering.EnlargeCircle;



public class HandImage extends JPanel{
 

 private Image imgBuff;
 private Graphics grafBuff;
 private Vector<Point> seeds = new Vector<Point>();
 private Vector<Point> circlePoints = new Vector<Point>();
 private Vector<Point> circlePointsEnlarge = new Vector<Point>();
 private Vector<Point> circlePointsReduce = new Vector<Point>();
 private Vector<BufferedImage> imagesB = new Vector<BufferedImage>();
 private Vector<Line> normales = new Vector<Line>();
 private Vector<Vector> circleAllPointsEnlarge = new Vector<Vector>();
 private Vector<Vector> circleAllPointsReduce = new Vector<Vector>();
 
 
 private int index;
  private BufferedImage image = null;

  public void setCirclePoints(Vector<Point> circlePoints) {
		this.circlePoints = circlePoints;
	}
    public Vector<Point> getCirclePoints() {
		return circlePoints;
	}
 
    public void setCirclePointsEnlarge(Vector<Point> circlePointsEnlarge) {
		this.circlePointsEnlarge = circlePointsEnlarge;
	}
    public void setCirclePointsReduce(Vector<Point> circlePointsReduce) {
		this.circlePointsReduce = circlePointsReduce;
	}
    public void setNormales(Vector<Line> normales) {
		this.normales = normales;
	}
 public HandImage(Vector<BufferedImage> imagesB) {
	 this.imagesB=imagesB;
	 index=0;
	 setImage();
}
 public void deleteFilteredImages() {
		 
		imagesB.removeAllElements();
}
 public void clearSeeds(){
	 seeds = new Vector<Point>();
 }
public void addSeed(Point p){
	if (VisualData.viewSeeds){
		seeds.add(p);
		Graphics g= this.getGraphics();
		paint(g);
	}
}

public void deleteCircle(){

	circlePoints = new Vector<Point>();
	circlePointsEnlarge = new Vector<Point>();
	circlePointsReduce = new Vector<Point>();
	normales=new Vector<Line>();
	this.setImage();
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


public void addCirclePoint(Point p){
	if (VisualData.viewCircle){
		
		if(circlePoints.size()>0){
			addCirclesPointsBetween(circlePoints.get(circlePoints.size()-1),p,10);
		}
		circlePoints.add(p);
		setInic();
		Graphics g= this.getGraphics();
		paint(g);
	
	}
}


public int getIndex() {
	return index;
}
 public int getSeedsSize(){
	return seeds.size();
 }
public Point getSeedPoint(int i){
	
	
	return seeds.get(i);
	
}


private void setInic(){
	 Dimension d = size();

	   if (grafBuff==   null) { 
		   imgBuff = createImage(d.width, d.height);
		   grafBuff = imgBuff.getGraphics();
	   }
	   grafBuff.setColor(getBackground());
	   grafBuff.fillRect(0, 0, d.width, d.height);
	   grafBuff.setColor(Color.WHITE);

	   grafBuff.drawRect(0, 0, d.width-1, d.height-1);
 }

private void drawSeeds(Graphics g){
	if (VisualData.viewSeeds){
		for (int i = 0; i < seeds.size(); i++) {
			g.setColor(Color.YELLOW);
			Point p = seeds.get(i);
			
			g.fillOval(new Double(p.getX()).intValue()-3,new Double(p.getY()).intValue()-3, 7, 7);
		}
	}
}
private void drawEnlargeCircle(Graphics g){
	if (VisualData.viewCircle){
		Line l;
		for (int i=0;i<normales.size();i++){
			g.setColor(Color.GREEN);
			l = normales.get(i);
			Point p = l.getPoint1();
			Point p2=l.getPoint2();
			g.drawLine(p.x, p.y, p2.x, p2.y);
			
			
		}
		
		int x=0;
		int y=0;
		int x2=0;
		int y2=0;
		for (int i = 0; i < circlePointsEnlarge.size(); i++) {
			
			g.setColor(Color.GREEN);
			Point p = circlePointsEnlarge.get(i);
			x=(int) p.getX();
			y=(int) p.getY();
			if(i==(circlePointsEnlarge.size()-1)){
				Point p2 = circlePointsEnlarge.get(0);
				x2=(int) p2.getX();
				y2=(int) p2.getY();
			}else{
				if(circlePointsEnlarge.size()>1){
					Point p2 = circlePointsEnlarge.get(i+1);
					x2=(int) p2.getX();
					y2=(int) p2.getY();
				}
			}
			
			g.fillOval(x-3,y-3, 7, 7);
			g.drawLine(x, y, x2, y2);
		}
	}
}

private void drawReduceCircle(Graphics g){
	if (VisualData.viewCircle){
		
		int x=0;
		int y=0;
		int x2=0;
		int y2=0;
		for (int i = 0; i < circlePointsReduce.size(); i++) {
			
			g.setColor(Color.CYAN);
			Point p = circlePointsReduce.get(i);
			x=(int) p.getX();
			y=(int) p.getY();
			if(i==(circlePointsReduce.size()-1)){
				Point p2 = circlePointsReduce.get(0);
				x2=(int) p2.getX();
				y2=(int) p2.getY();
			}else{
				if(circlePointsReduce.size()>1){
					Point p2 = circlePointsReduce.get(i+1);
					x2=(int) p2.getX();
					y2=(int) p2.getY();
				}
			}
			
			g.fillOval(x-3,y-3, 7, 7);
			g.drawLine(x, y, x2, y2);
		}
	}
}

private void drawEnlargeCircle(Graphics g,int index){
	if ((VisualData.viewCircleAll)&&(circleAllPointsEnlarge.size()>0)){
		
		
		int x=0;
		int y=0;
		int x2=0;
		int y2=0;
		
		Vector<Point> circ = circleAllPointsEnlarge.get(index);
		for (int i = 0; i < circ.size(); i++) {
			
			g.setColor(Color.GREEN);
			Point p = circ.get(i);
			x=(int) p.getX();
			y=(int) p.getY();
			if(i==(circ.size()-1)){
				Point p2 = circ.get(0);
				x2=(int) p2.getX();
				y2=(int) p2.getY();
			}else{
				if(circ.size()>1){
					Point p2 = circ.get(i+1);
					x2=(int) p2.getX();
					y2=(int) p2.getY();
				}
			}
			
			g.fillOval(x-3,y-3, 7, 7);
			g.drawLine(x, y, x2, y2);
		}
	}
}
private void drawCircle(Graphics g){
	if (VisualData.viewCircle){
		int x=0;
		int y=0;
		int x2=0;
		int y2=0;
		for (int i = 0; i < circlePoints.size(); i++) {
			g.setColor(Color.RED);
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
}
public void update (Graphics g){
	
	setInic();
	grafBuff.setColor(Color.BLACK);
	g.drawImage(image, 0, 0, this);
	drawSeeds(g);
	drawEnlargeCircle(g);
	drawEnlargeCircle(g,index);
	drawCircle(g);
	drawReduceCircle(g);
}


public void paint (Graphics g){
    update(g);
    
}

public void setImage() {
	int aux=index+1;
	VisualData.loadTable(aux);
	VisualData.setjLabelSBImages("Image "+aux +"/"+imagesB.size());
	try{
		if(aux>1)
		VisualData.setjLabelNameImage(ImagesData.imagesBFilteredName.get(index));}
	catch (Exception e){

	}
	if(imagesB.size()>0){
		this.image = imagesB.elementAt(index);
		Graphics g= this.getGraphics();
		g.drawImage(image, 0, 0, null);
		}
	drawSeeds(this.getGraphics());
	
	drawEnlargeCircle(this.getGraphics());
	drawEnlargeCircle(this.getGraphics(),index);
	drawCircle(this.getGraphics());
	drawReduceCircle(this.getGraphics());
}

public void nextImage(){
	index++;
	if(index>imagesB.size()-1)
		index=0;			
	setImage();
	
}
public void lastImage(){
	index=imagesB.size()-1;
	setImage();
	
}
public void firstImage(){
	index=0;
	setImage();
}
public void backImage() {
	index--;
	if(index<0)
		index=imagesB.size()-1;			
	setImage();
	
}
public BufferedImage getImage() {
	return image;
}

public Vector<Point> getSeedsVector() {
	return seeds;
}
public void deleteSeeds() {
	seeds=new Vector<Point>();
	this.setImage();
	
}
public void enlargeCircleForAll(){
//	EnlargeCircle en = new EnlargeCircle(this,1,imagesB.get(0),this.getCirclePoints());
//	Vector init=en.run();
	for(int i=0;i<imagesB.size();i++){
		EnlargeCircle e = new EnlargeCircle(this,1,imagesB.get(i),this.getCirclePoints());
		circleAllPointsEnlarge.add(e.run());
	}
}

public void generateMesh(){
	Mesh mesh = new Mesh();
	
	float div= 90.0f;
	float dist = 0.0f;
	int cantp=1;
	
	int size = circleAllPointsEnlarge.size();
	int circleSize = circleAllPointsEnlarge.get(0).size();
	
	//Mesh.points.add(new Point3D(-1,-1,-1));//Se agrega para que la numeracion comience de 1
	for (int i = 0;i<size;i++){
		Vector<Point> v1 = circleAllPointsEnlarge.get(i);
		
		for (int j = 0;j<v1.size();j++){
			
			Point p = v1.get(j);
			Point3D p3d = new Point3D(p.x/div, p.y/div, dist);
			Mesh.points.add(p3d);
			Mesh.pointsSUR+= " "+cantp+" "+p3d.toSUR()+"\n";
			cantp++;
		}
		dist+=0.3f;
	}
	Mesh.cantPoints3D = cantp-1;
	Mesh.generateTriangles(circleSize,size);
		
}


}
