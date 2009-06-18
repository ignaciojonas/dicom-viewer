package draw2D.filtering;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.swing.SwingUtilities;

import data.ImagesData;
import data.VisualData;

import ui.HandImage;
import ui.MainFrame;

public class MeanFilter extends Thread{
	
	int radio;
	Vector<BufferedImage> imagesB;
	HandImage handImage;
	public MeanFilter(HandImage handImage, int radio,Vector<BufferedImage> imagesBFiltered) {
		this.radio=radio;
		this.imagesB=imagesBFiltered;
		this.handImage=handImage;
	}
	

	private int getRGB(int x,int y,BufferedImage bi,int radio) {
		if (radio<=0){
			int rgb =bi.getRGB(x, y);
			Color c = new Color(rgb);
			return c.getRed();
		}
		
		int xmin=x-radio;
		int ymin=y-radio;
		int xmax=x+radio;
		int ymax=y+radio;
		
		if (x-radio < 0)
			xmin=0;
		if (y-radio < 0)
			ymin=0;
		
		if (x+radio > bi.getWidth())
			xmax= bi.getWidth();
		if (y+radio > bi.getHeight())
			ymax=bi.getHeight();
		
		int sum=0;
		int cant=0;
		for (int i = xmin; i < xmax; i++) {
			for (int j = ymin; j < ymax; j++) {
				int rgb =bi.getRGB(i, j);
				Color c = new Color(rgb);
				
				sum+=c.getRed();
				cant++;
			}
		}
	 	return sum/cant;
		
		
	}
	public void run(){

	    VisualData.jProgressBar.setMaximum((int)imagesB.size());
	    VisualData.jProgressBar.setValue(0);
	    VisualData.jProgressBar.setStringPainted(true);
	    
		BufferedImage biFiltered = null;
		Color temp=null;
		for (int i = 0; i < imagesB.size(); i++) {
			BufferedImage biOrigin= imagesB.get(i);
			
			int biType = biOrigin.getType();
			if(biType==0)
				biType= BufferedImage.TYPE_INT_RGB;
			biFiltered= new BufferedImage (biOrigin.getWidth(),biOrigin.getHeight(),biType);

			for (int x=0; x<biOrigin.getWidth();++x)
				for (int y=0; y<biOrigin.getHeight(); ++y){
						int red =getRGB(x, y, biOrigin, radio);
						temp=new Color(red,red,red);
						
						biFiltered.setRGB(x,y,temp.getRGB());
				}
			
			ImagesData.imagesBFiltered.add(biFiltered);
			
			String name="IMG"+i+" Mean radio:"+radio;
			ImagesData.imagesBFilteredName.add(name);
			
			
	    	VisualData.jProgressBar.setValue(i);  
	    	VisualData.jProgressBar.repaint();
	    	if(i==0){
	    		this.handImage.setImage();
	    		this.handImage.repaint();
	    	}
		}

		this.handImage.setImage();
		this.handImage.repaint();
		System.gc();
		VisualData.jProgressBar.setValue(imagesB.size());
		VisualData.jProgressBar.repaint();
	
		
	}


}

