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

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import data.ImagesData;
import data.VisualData;
import data.io.DicomProperties;
import draw2D.AllCirclesOnScreen;
import draw2D.CircleOnScreen;
import draw2D.InitCircleOnScreen;
import draw2D.NormalOnScreen;
import draw2D.filtering.EnlargeCircle;
import draw3D.Line;
import draw3D.Mesh;
import draw3D.scenario.screen.Point3D;



public class HandImage extends JPanel{
 

 private Image imgBuff;
 private Graphics grafBuff;
 private Vector<Point> seeds = new Vector<Point>();
 private Vector<BufferedImage> imagesB = new Vector<BufferedImage>();
 
 
 private InitCircleOnScreen initCircle = new InitCircleOnScreen();
 private AllCirclesOnScreen allCircles = new AllCirclesOnScreen();
 
 
  private int index;
  private BufferedImage image = null;

  public void setCirclePoints(Vector<Point> circlePoints) {
		this.initCircle = new InitCircleOnScreen(circlePoints,Color.RED);
	}
    
    public InitCircleOnScreen getInitCircle() {
    	return initCircle;
    }
public AllCirclesOnScreen getAllCircles() {
	return allCircles;
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

	initCircle = new InitCircleOnScreen();
	this.setImage();
}

public void addPointToInitCircle(Point p){
	if (VisualData.viewCircle){
		this.initCircle.addPointToInitCircle(p);
		
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


public void drawInitCircle(){
	if (VisualData.viewCircle)
		initCircle.draw(this.getGraphics());
}
public void update (Graphics g){
	
	setInic();
	grafBuff.setColor(Color.BLACK);
	g.drawImage(image, 0, 0, this);
	drawSeeds(g);
	allCircles.draw(g, index);
	drawInitCircle();

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
	allCircles.draw(this.getGraphics(), index);
	drawInitCircle();
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
	for(int i=0;i<imagesB.size();i++){
		EnlargeCircle e = new EnlargeCircle(this,1,imagesB.get(i),this.initCircle);
		
		CircleOnScreen enl = e.enlargeCircle();
		enl.setColor(VisualData.enlargeColor);
				
		CircleOnScreen redu = e.getReduceCircle();
		redu.setColor(VisualData.reduceColor);
		
		NormalOnScreen norm = getNormalOnScreen(this.initCircle, enl);
		norm.setColor(VisualData.normal);
		
		
		this.initCircle.setColor(VisualData.initColor);
		
		
		allCircles.add(enl,redu,this.initCircle,norm);
		
	}
	this.setImage();
}

public void enlargeCircle(Color initColor,Color enlargeColor,Color reduceColor,Color normal){
	EnlargeCircle e = new EnlargeCircle(this,1,imagesB.get(index),this.initCircle);
	
	CircleOnScreen enl = e.enlargeCircle();
	enl.setColor(enlargeColor);
	
	
	CircleOnScreen redu = e.getReduceCircle();
	redu.setColor(reduceColor);
	
	
	
	Vector <Point> aux = new Vector<Point>();
	Vector <Point> iniAux = this.initCircle.getCirclePoints();
	Point p;
	for(int i = 0;i<this.initCircle.getCantPoints();i++){
		p= iniAux.get(i);
		aux.add(new Point(p.x,p.y));
	}
	
	
	
	InitCircleOnScreen init = new InitCircleOnScreen(aux,initColor);
	
	NormalOnScreen norm = getNormalOnScreen(init, enl);
	norm.setColor(normal);
	
	allCircles.add(enl,redu,init,norm,index );
	

	
}

public void previewCircle(int index,Color initColor,Color enlargeColor,Color reduceColor,Color normal){
	EnlargeCircle e = new EnlargeCircle(this,1,imagesB.get(index),this.initCircle);
	
	CircleOnScreen aux = e.enlargeCircle();
	aux.setColor(enlargeColor);
	
	CircleOnScreen aux2 = e.getReduceCircle();
	aux2.setColor(reduceColor);
	
	NormalOnScreen aux3 = getNormalOnScreen(this.initCircle, aux);
	aux3.setColor(normal);
	
	aux3.draw(this.getGraphics());
	aux.draw(this.getGraphics());
	aux2.draw(this.getGraphics());
	
	
	this.initCircle.setColor(initColor);
	this.initCircle.draw(this.getGraphics());
}

private NormalOnScreen getNormalOnScreen(InitCircleOnScreen init, CircleOnScreen enlarge) {
	 Vector<Line> normals = new Vector<Line>();
	 Vector<Point> in = init.getCirclePoints();
	 Vector<Point> en = enlarge.getCirclePoints();
	 for(int i=0;i<enlarge.getCantPoints();i++){
		 normals.add(new Line(in.get(i),en.get(i))); 
	 }
	 return new NormalOnScreen(normals);
}

public void generateMesh(){
	
	Mesh mesh = new Mesh();
	
	float div= 90.0f;
	float dist = 0.0f;
	int cantp=1;
	
	int size = allCircles.size();
	CircleOnScreen aux = allCircles.getEnlargeCircle(0);
	int circleSize = aux.getCantPoints();
	
	VisualData.jProgressBar.setMaximum(size);
	 VisualData.jProgressBar.setValue(0);
	 VisualData.jProgressBar.setStringPainted(true);
	 
	//Mesh.points.add(new Point3D(-1,-1,-1));//Se agrega para que la numeracion comience de 1
	for (int i = 0;i<size;i++){
		aux = allCircles.getEnlargeCircle(i);
		Vector<Point> v1 = aux.getCirclePoints();
		
		for (int j = 0;j<v1.size();j++){
			
			Point p = v1.get(j);
			Point3D p3d = new Point3D(p.x/div, p.y/div, dist);
			Mesh.points.add(p3d);
			Mesh.pointsSUR+= " "+cantp+" "+p3d.toSUR()+"\n";
			cantp++;
		}
		dist+=0.3f;
		VisualData.jProgressBar.setValue(i);  
    	VisualData.jProgressBar.repaint();
	}
	Mesh.cantPoints3D = cantp-1;
	Mesh.generateTriangles(circleSize,size);
	System.gc();
	VisualData.jProgressBar.setValue(size);
	VisualData.jProgressBar.repaint();
	
	JOptionPane.showMessageDialog(null,"Mesh generate succefuly","Confirm",JOptionPane.INFORMATION_MESSAGE);
}


}
