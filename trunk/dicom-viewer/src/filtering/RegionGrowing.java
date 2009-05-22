package filtering;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.SwingUtilities;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import data.ImagesData;
import data.VisualData;
import filtering.rg.Criterio;

import ui.HandImage;
import ui.MainFrame;

public class RegionGrowing extends Thread{
	
	
	Vector<BufferedImage> imagesB;
	HandImage handImage;
	HandImage handImageFiltered;
	Vector<Criterio> criterios;
	public RegionGrowing(HandImage handImage,HandImage handImageFiltered, Vector<BufferedImage> imagesBFiltered,Vector<Criterio> criterios) {
	
		this.imagesB=imagesBFiltered;
		this.handImage=handImage;
		this.handImageFiltered=handImageFiltered;
		this.criterios=criterios;
	}

	public void run(){

	    VisualData.jProgressBar.setMaximum((int)imagesB.size());
	    VisualData.jProgressBar.setValue(0);
	    VisualData.jProgressBar.setStringPainted(true);
	    
	    byte[][] visits;
	    
	    Point aux;
	   
	    
	    
	    
	    //borrar ##############################################
	   // handImage.addSeed(new Point(126,105));
	    
	    for (Iterator iterator = criterios.iterator(); iterator.hasNext();) {
			Criterio criterio = (Criterio) iterator.next();
			int radio = criterio.getRadio();
			System.out.println();
			System.out.println("#### CRITERIO: "+criterio.getType()+" ####");
		    
		    for (int i = 0; i < imagesB.size(); i++) {//recorre todas las imagenes
		    	
		    	Vector<Point> auxNeigh = null;
		 	    double desvio=0;
		 	    double intensidadCaracteristica=0;
		 	    double intensidadCaracteristicaTotal=0;
		 	    Vector<Point> listL = new Vector<Point>(); //handImage.getSeedsVector();//Insertar los voxels semilla en la lista auxiliar L 
			    Vector<Point> listR = new Vector<Point>();
			    Vector<Point> listRG = new Vector<Point>();
			    
		    	BufferedImage bi = imagesB.get(i);
		    	int height = bi.getHeight();
		    	int width = bi.getWidth();
		    	int s=0;
		    	Point seed=null;
		    	for (s=0; s < handImage.getSeedsSize(); s++) {
		    		seed=handImage.getSeedPoint(s);
		    		listL.add(seed);
		   	    	auxNeigh = criterio.getNeight(seed,radio,bi);
		   	    	
		   	    	intensidadCaracteristica=intensidadCaracteristica(auxNeigh, bi);
		   	    	desvio+=desvio(auxNeigh, bi, intensidadCaracteristica);
		   	    	intensidadCaracteristicaTotal+=intensidadCaracteristica;
		   	    	
		   	    	int temp=s+1;
		   	    	System.out.println("intensidadCaracteristica "+ temp+": "+intensidadCaracteristica);
		   		}
		    	desvio=desvio/s;
		    	System.out.println("desvio: "+ desvio+" s: "+s);
		    	intensidadCaracteristicaTotal=intensidadCaracteristicaTotal/s;
		    	System.out.println("intensidadCaracteristicaTotal: "+ intensidadCaracteristicaTotal);
		    	
		    	visits = new byte[width+1][height+1];
		 	    
		 	    Color temp=new Color(0,0,0);
		 	    int type = bi.getType();
		 	    BufferedImage biFiltered;
		 	    if (type==0)
		 	    	biFiltered = new BufferedImage (width,height,BufferedImage.TYPE_INT_RGB);
		 	    else
		 	    	biFiltered = new BufferedImage (width,height,BufferedImage.TYPE_INT_RGB);
		 	    
		    	for(int h=0;h<=height;h++)
		    	      for(int w=0;w<=width;w++){
		    	    	  visits[w][h] = 0;
		    	    	  if((w!=width)&&(h!=height))
		    	    		  biFiltered.setRGB(w,h,temp.getRGB());
		    	        }
		    		    	
			    while (listL.size()>0){//while L no esté vacía
			    
			    	aux = listL.get(0);//eliminar el primer voxel vr de L 
			    	listL.remove(0);
			    	//listR.add(aux);//rotular vr como incluido en la región Ri 
			    	biFiltered.setRGB(aux.x, aux.y, 255);
			    	
			    	visits[(int) aux.getX()][(int) aux.getY()]= 1;
			    
			     	auxNeigh = getNeight(aux,radio,bi,visits);//obtener los vecinos de aux
			     	
			       	for (int j = 0; j < auxNeigh.size(); j++) {   //  for cada vecino v de vr que no haya sido visitado
			    		Point ne = auxNeigh.get(j);
		
			    		if (criterio.evaluar(ne,bi,desvio,intensidadCaracteristicaTotal)){// if ( v satisface el criterio de aceptación para Ri )
			    			if (visits[(int) ne.getX()][(int) ne.getY()]==0){
			    				visits[(int) ne.getX()][(int) ne.getY()]= 1;
			    				listL.add(ne);//insertar v en L 
			    			}
			    		}else{
			    			//TODO     //rotular v como voxel frontera de Ri
			    		    listRG.add(ne);//insertar v en RG-list
			    		}
						
					}
			    }	
		   
			    //CREAR LA MAGEN
			    
			   
			    ImagesData.imagesBFiltered.add(biFiltered);
			    String name="IMG"+i+" "+criterio.getStringName();
				ImagesData.imagesBFilteredName.add(name);
			    VisualData.jProgressBar.setValue(i);  
		    	VisualData.jProgressBar.repaint();
		    	if(i==0){
		    		this.handImageFiltered.setImage();
		    		//this.handImageFiltered.repaint();
		    	}
		    
		    }
	    }    
	    
	    this.handImageFiltered.setImage();
		//this.handImageFiltered.repaint();
		System.gc();
		VisualData.jProgressBar.setValue(imagesB.size());
		VisualData.jProgressBar.repaint();
  
	}


	
	private double intensidadCaracteristica(Vector<Point> s, BufferedImage bi){
		int auxRGB;
		double auxRGColor, sumaIntensidades=0;
		double n=0;
		for (Iterator iterator = s.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			auxRGB = bi.getRGB(point.x, point.y);
			auxRGColor = new Color(auxRGB).getRed();
			sumaIntensidades+=auxRGColor;
			n++;
		}

		return (sumaIntensidades/n);

	}
		
	private double desvio(Vector<Point> s, BufferedImage bi,double intensidadCaracteristica){
		double aux=0;
		int auxRGB;
		double  auxRGColor, sumaIntensidades=0;
		double n=0;
		for (Iterator iterator = s.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			auxRGB = bi.getRGB(point.x, point.y);
			auxRGColor = new Color(auxRGB).getRed();
			aux=auxRGColor-intensidadCaracteristica;
			sumaIntensidades+=aux*aux;
			n++;
		}
		
		return (Math.sqrt(sumaIntensidades/n));

	}
	
	
	
	
	private Vector<Point> getNeight(Point aux, int radio, BufferedImage bi, byte[][] visits) {
		int x= aux.x;
		int y= aux.y;
		
		int xmin=x-radio;
		int ymin=y-radio;
		int xmax=x+radio;
		int ymax=y+radio;
		
		if (x-radio < 0)
			xmin=0;
		if (y-radio < 0)
			ymin=0;
		
		if (x+radio >= bi.getWidth()){
			
			xmax= bi.getWidth()-1;
			
		}
		if (y+radio >= bi.getHeight()){
			ymax=bi.getHeight()-1;
			
		}
		
	
		Vector ret=new Vector();
		for (int i = xmin; i <= xmax; i++) {
			for (int j = ymin; j <= ymax; j++) {
				
				if(visits[i][j] == 0)
					ret.add(new Point(i,j));
				
				//listVisits.add(new Point(i,j));
				
			}
		}

		return ret;
	}

	private boolean criterioOK(Point point, Point aux, BufferedImage bi) {

		int originRGB =bi.getRGB(aux.x, aux.y);
		int auxRGB = bi.getRGB(point.x, point.y);
		
		if(originRGB==auxRGB)
			return true;
			
		return false;
	}

	private boolean criterioOK2(Point point, Point aux, BufferedImage bi) {

		int originRGB = bi.getRGB(aux.x, aux.y);
		int auxRGB = bi.getRGB(point.x, point.y);
		
		int originColor = new Color(originRGB).getRed();
		int auxRGColor = new Color(auxRGB).getRed();
		
		
		if((auxRGB <= originRGB+50)&& (auxRGB > originRGB-50))
			return true;
			
		return false;
	}
}

