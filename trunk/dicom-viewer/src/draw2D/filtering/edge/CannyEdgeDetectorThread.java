package draw2D.filtering.edge;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Vector;

import ui.HandImage;

import data.ImagesData;
import data.VisualData;

public class CannyEdgeDetectorThread extends Thread{

	float lowT;
	float hightT;
	Vector<BufferedImage> imagesB;
	HandImage handImage;
	int gaussianKernelWidth;
	float gaussianKernelRadius;
	boolean contrastNormalized;
	
	public CannyEdgeDetectorThread(HandImage handImage,float lowT,float hightT,Vector<BufferedImage> imagesB, int gaussianKernelWidth, float gaussianKernelRadius, boolean contrastNormalized) {
		this.lowT = lowT;
		this.hightT = hightT;
		this.imagesB =imagesB;
		this.handImage=handImage;
		this.gaussianKernelRadius=gaussianKernelRadius;
		this.gaussianKernelWidth=gaussianKernelWidth;
		this.contrastNormalized=contrastNormalized;
		
		
	}
	public String getFilteredName(){
		return " Canny Edge lowT:"+lowT+" hightT:"+hightT+" GRadius:"+gaussianKernelRadius+" GWidth:"+gaussianKernelWidth+" Norm:"+contrastNormalized;
	}
	public void run(){

    VisualData.jProgressBar.setMaximum((int)imagesB.size());
    VisualData.jProgressBar.setValue(0);
    VisualData.jProgressBar.setStringPainted(true);
    
	
	for (int i = 0; i < imagesB.size(); i++) {
		BufferedImage biOrigin= imagesB.get(i);
		
		CannyEdgeDetector detector = new CannyEdgeDetector();
		//adjust its parameters as desired
		detector.setLowThreshold(lowT);
		detector.setHighThreshold(hightT);
		detector.setContrastNormalized(contrastNormalized);
		detector.setGaussianKernelRadius(gaussianKernelRadius);
		detector.setGaussianKernelWidth(gaussianKernelWidth);
		//apply it to an image
		detector.setSourceImage(biOrigin);
		detector.process();
		ImagesData.imagesBFiltered.add(detector.getEdgesImage());
				
		String name="IMG"+i+getFilteredName();
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
