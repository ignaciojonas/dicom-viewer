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



public class RepaintPanel extends JPanel{
 

 private Image imgBuff;
 private Graphics grafBuff;

 private BufferedImage image = null;
 
 public RepaintPanel(BufferedImage image) {
	 this.image=image;

}

public RepaintPanel() {
	// TODO Auto-generated constructor stub
	
}

public void setPoints(int x0, int y0, int x1, int y1, Graphics g)
{
    int dx = x1 - x0;
    int dy = y1 - y0;

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
private double getDistance(Point point1, Point point2){
	int x = point2.x - point1.x;
	int y = point2.y - point1.y;
	return Math.sqrt(x*x + y*y);
}

public Vector<Integer> getGraph(int index){
	
	int h= image.getHeight();
	Vector<Integer> ret= new Vector<Integer>();
	for(int i=0;i<h;i++){
		ret.add(new Integer(image.getRGB(index, i)));
	}
	return ret;
}
public void setImage(Vector<BufferedImage> imagesB, Point point,Point center){
	int size = imagesB.size();
	
	int x0 = point.x;
	int y0 = point.y;
	int x1 = center.x*2-point.x;
	int y1 = center.y*2-point.y;
	
	int dx = x1 - x0;
    int dy = y1 - y0;
    
    int width = (int) getDistance(point,new Point( center.x*2-point.x,center.y*2-point.y));
	BufferedImage ret=new BufferedImage (size+1,width,BufferedImage.TYPE_BYTE_GRAY);
	if(size>0){
	
		BufferedImage aux;
		int x=1;
		for(int y=0;y<size;y++){
			x=0;
			aux = imagesB.get(y);
			
			x0 = point.x;
			y0 = point.y;
			x1 = center.x*2-point.x;
			y1 = center.y*2-point.y;
			
			dx = x1 - x0;
		    dy = y1 - y0;
		
		   
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
		            try{
		            ret.setRGB(y, x, aux.getRGB(x0,y0));
		            }catch(Exception e){}
		            x++;
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
		            try{
			            ret.setRGB(y, x, aux.getRGB(x0,y0));
			            }catch(Exception e){}
		            x++;
		        }
	    }
	}
	
	
	}
	
	this.image=ret;
}

private void setInic(){
	 Dimension d = size();

	 if (d.width==0){
		 d = new Dimension(1,1);
	 }
		 
	   if (grafBuff== null) { 
		   imgBuff = createImage(d.width, d.height);
		   grafBuff = imgBuff.getGraphics();
	   }
	   grafBuff.setColor(getBackground());
	   grafBuff.fillRect(0, 0, d.width, d.height);
	   grafBuff.setColor(Color.WHITE);
	   grafBuff.drawRect(0, 0, d.width-1, d.height-1);
 }


public void update (Graphics g){
	
	setInic();
	grafBuff.setColor(Color.BLACK);
	g.drawImage(image, 0, 0, this);

}


public void paint (Graphics g){
    update(g);
    
}

public void setImage() {

		Graphics g= this.getGraphics();
		g.drawImage(image, 0, 0, null);

}





public BufferedImage getImage() {
	return image;
}


public void setCursor(int crosshairCursor) {
	this.setCursor(crosshairCursor);
	
}



}
