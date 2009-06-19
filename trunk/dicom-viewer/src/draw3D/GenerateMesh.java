package draw3D;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JOptionPane;

import org.dcm4che.imageio.plugins.DcmImageReadParam;

import data.ImagesData;
import data.VisualData;
import draw2D.AllCirclesOnScreen;
import draw2D.CircleOnScreen;
import draw3D.scenario.screen.Point3D;

import ui.HandImage;

public class GenerateMesh extends Thread{

	 private AllCirclesOnScreen allCircles;
	 
	public GenerateMesh( AllCirclesOnScreen allCircles ) {
		this.allCircles =allCircles;
	}
	public void run(){
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
