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
import draw3D.GenerateMesh;
import draw3D.Line;
import draw3D.Mesh;
import draw3D.scenario.screen.Point3D;



public class HandImage extends JPanel{
 

 private Image imgBuff;
 private Graphics grafBuff;
 private Vector<Point> seeds = new Vector<Point>();
 private Vector<BufferedImage> imagesB = new Vector<BufferedImage>();
 private Vector<BufferedImage> allImagesB = new Vector<BufferedImage>();
 
 private InitCircleOnScreen initCircle = new InitCircleOnScreen();
 private AllCirclesOnScreen allCircles = new AllCirclesOnScreen();

 private int index;
 private BufferedImage image = null;
 
 private boolean workPackage=false;
 private int cantPackage =1;
 private int numPackage =1;
 public void startWorkPackage(int p){
	 workPackage=true;
	 cantPackage = Mesh.endSequence - Mesh.startSequence;
	 numPackage = p;
 }
 
 public void stopWorkPackage(){
	 workPackage=false;
 }

 public BufferedImage getFromAllImagesB(int index) {
		return allImagesB.get(index);
		
	}
 public int getSizeAllImagesB() {
		return allImagesB.size();
		
	}

 public void setImagesB(Vector<BufferedImage> imagesB) {
	this.imagesB = imagesB;
	
	
}
 public Vector<BufferedImage> getImagesB() {
	return imagesB;
}
 public void setCirclePoints(Vector<Point> circlePoints) {
	 this.initCircle = new InitCircleOnScreen(circlePoints,VisualData.initColor);

 }

 public InitCircleOnScreen getInitCircle() {
	 return initCircle;
 }
 public AllCirclesOnScreen getAllCircles() {
	 return allCircles;
 }


 
 public HandImage(Vector<BufferedImage> imagesB) {
	 this.imagesB=imagesB;
	 this.allImagesB=imagesB;
	 
		
		
		
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

private Point center = new Point(256,256);
private Point refer = new Point (256,2);
public void setRefer(Point refer) {
	this.refer = refer;
}
private void drawLateral(Graphics g){
	if (VisualData.viewLateral){
		
		rectaSimple2(refer.x, refer.y, center.x*2-refer.x, center.y*2-refer.y,g);
		
	}
}

public Point getRefer() {
	return refer;
}
public Point getCenter() {
	return center;
}
public void setCenter(Point center) {
	this.center = center;
}
public boolean contatinPoint(Point o,Point p){
	if((o.x < (p.x+3))&&(o.x > (p.x-3)))
		if((o.y < (p.y+3))&&(o.y > (p.y-3)))
			return true;
	return false;
}
public boolean containtPoint(Point p){
	int x1 = center.x*2-refer.x;
	int y1 = center.y*2-refer.y;
	
	if(contatinPoint(center, p))
		return true;
	else
		if(contatinPoint(refer, p))
			return true;
		else
			if(contatinPoint(new Point(x1,y1), p))
				return true;
	return false;
}
public void rectaSimple2(int x0, int y0, int x1, int y1, Graphics g)
{   g.setColor(Color.PINK);
    int dx = x1 - x0;
    int dy = y1 - y0;
    
    g.fillRect(x0-3, y0-3, 7, 7);
 
    g.fillRect(x1-3, y1-3, 7, 7);
    
    g.drawOval(center.x-3, center.y-3, 7, 7);
    
    g.drawLine( x0, y0, x0, y0);
    if (Math.abs(dx) > Math.abs(dy)) {          // pendiente < 1
        float m = (float) dy / (float) dx;
        float b = y0 - m*x0;
        if(dx<0)
            dx =  -1;
        else
            dx =  1;
        while (x0 != x1) {
            x0 += dx;
            y0 = Math.round(m*x0 + b);
            g.drawLine( x0, y0, x0, y0);
        }
    } else
    if (dy != 0) {                              // slope >= 1
        float m = (float) dx / (float) dy;      // compute slope
        float b = x0 - m*y0;
        if(dy<0)
            dy =  -1;
        else
            dy =  1;
        while (y0 != y1) {
            y0 += dy;
            x0 = Math.round(m*y0 + b);
            g.drawLine( x0, y0, x0, y0);
        }
    }
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
	drawLateral(g);
	int temp=index;
	if(workPackage){
		temp = (Mesh.startSequence + numPackage+(temp*cantPackage));
	}
	allCircles.draw(g, temp);
	drawInitCircle();
	drawLateral(g);

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
	drawInitCircle();
	int temp=index;
	if(workPackage){
		temp = (Mesh.startSequence + numPackage+(temp*cantPackage));
	}
	
	allCircles.draw(this.getGraphics(), temp);//para mostrar con paquetes recalcular este index
	drawLateral(this.getGraphics());

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
public void enlargeCircleForAllWithReduce(){
	CircleOnScreen init;
	for(int i=0;i<imagesB.size();i++){
		System.out.println(i);
		if(i==0)
			init = this.initCircle;
		else
			init = allCircles.getRecudeCircle(i-1);
		EnlargeCircle e = new EnlargeCircle(this,1,imagesB.get(i),init);
		
		CircleOnScreen enl = e.enlargeCircle();
		enl.setColor(VisualData.enlargeColor);
				
		CircleOnScreen redu = e.getReduceCircle();
		redu.setColor(VisualData.reduceColor);
		
		NormalOnScreen norm = getNormalOnScreen(this.initCircle, enl);
		norm.setColor(VisualData.normal);
		
		
		this.initCircle.setColor(VisualData.initColor);
		
		
		allCircles.add(enl,redu,this.initCircle,norm,i);
		
	}
	this.setImage();
}

private NormalOnScreen getNormalOnScreenSnake(CircleOnScreen init, CircleOnScreen enlarge) {
	 Vector<Line> normals = new Vector<Line>();
	 Vector<Point> in = init.getCirclePoints();
	 Vector<Point> en = enlarge.getCirclePoints();

	 for(int i=0;i<enlarge.getCantPoints();i++){
		 normals.add(new Line(in.get(i),en.get(i))); 
	 }
	 return new NormalOnScreen(normals);
}

public void addToAllCircle(CircleOnScreen enl,CircleOnScreen redu){
	
	NormalOnScreen norm = getNormalOnScreenSnake(this.initCircle, enl);
	norm.setColor(VisualData.normal);
	
	int temp=index;
	if(workPackage){
		temp = (Mesh.startSequence + numPackage+(temp*cantPackage));
	}
	
	allCircles.add(enl,redu,this.initCircle,norm,temp);
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
		
		allCircles.add(enl,redu,this.initCircle,norm,i);
		
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
	
	int temp=index;
	if(workPackage){
		temp = (Mesh.startSequence + numPackage+(temp*cantPackage));
	}
	
	allCircles.add(enl,redu,init,norm,temp );
	

	
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

private NormalOnScreen getNormalOnScreen(CircleOnScreen init, CircleOnScreen enlarge) {
	 Vector<Line> normals = new Vector<Line>();
	 Vector<Point> in = init.getCirclePoints();
	 Vector<Point> en = enlarge.getCirclePoints();
	 Point poin = in.get(0);
	 in.remove(0);
	 in.add(in.size()-1,poin);
	 
	 for(int i=0;i<enlarge.getCantPoints();i++){
		 normals.add(new Line(in.get(i),en.get(i))); 
	 }
	 return new NormalOnScreen(normals);
}

public void generateMesh(){
	GenerateMesh gm = new GenerateMesh(allCircles);
	gm.start();

}

public void setCircleAllPontsEnlarge(
		Vector<CircleOnScreen> circleAllPointsEnlarge) {
	allCircles.setCircleAllPointsEnlarge(circleAllPointsEnlarge);
	
}

public void setInitCircleColor(Color background) {
	VisualData.initColor=background;
	this.initCircle.setColor(VisualData.initColor);
}

public void setCursor(int crosshairCursor) {
	this.setCursor(crosshairCursor);
	
}
public void reloadImageB() {
	this.imagesB=this.allImagesB;
	this.setImage();
	
}




}
