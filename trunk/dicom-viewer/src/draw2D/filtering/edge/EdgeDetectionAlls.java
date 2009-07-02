package draw2D.filtering.edge;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

import javax.swing.ImageIcon;

import sun.awt.windows.ThemeReader;
import ui.HandImage;

import data.ImagesData;
import data.VisualData;

public class EdgeDetectionAlls extends Thread{
	//for edge algorithm
	public static final int SIMPLE = 0;
	public static final int ROBERTSCROSS = 1;
	public static final int PREWITT = 2;
	public static final int SOBEL3X3 = 3;
	
	
	HandImage handImage;
	public EdgeDetectionAlls(HandImage handImage,int typeofgaussianblur,int typeofoperator,boolean nonmaxsupenabled,boolean hresholdingenabled,int thresholdval, boolean thresholdingenabled) {
		
		this.typeofgaussianblur = typeofgaussianblur;
		this.typeofoperator = typeofoperator;
		this.nonmaxsupenabled = nonmaxsupenabled;
		this.thresholdval = thresholdval;
		this.thresholdingenabled = thresholdingenabled;
		this.handImage =handImage;
		
		
	}
	
	private String getFilteredName() {
		
		return " Gaussian Blur:"+typeofoperator+" Type:"+typeofgaussianblur+" Supression:"+nonmaxsupenabled+" Thresholdin:"+thresholdingenabled+" Threshold:"+thresholdval;
	}
	
	int typeofgaussianblur = 0;
	int typeofoperator = SIMPLE;
	//for non-maximumsupression
	boolean nonmaxsupenabled = false;
	//for thresholding
	boolean thresholdingenabled = false;
	int thresholdval = 50;
	
		
	Image originalImage = null;
	Image processedImage = null;

	


	
	
	public void run(){
		
		VisualData.jProgressBar.setMaximum((int)ImagesData.imagesB.size());
	    VisualData.jProgressBar.setValue(0);
	    VisualData.jProgressBar.setStringPainted(true);
	    
		GaussianBlurOp apgausblur; 
		for (int i = 0; i < ImagesData.imagesB.size(); i++) {
			
			this.originalImage =ImagesData.imagesB.get(i);
			
		
				if(typeofgaussianblur==0)
						
					processedImage = originalImage;
					else{
						apgausblur = new GaussianBlurOp(originalImage,typeofgaussianblur);
						processedImage = apgausblur.getImage();
					}
					
				switch(typeofoperator){
				case SIMPLE: {
					Simple tempimage = new Simple(processedImage);
					processedImage = tempimage.getImage();
					//NonMaxSupression
					if(nonmaxsupenabled){
						NonMaxSupression tempimage2 = new NonMaxSupression(processedImage);
						processedImage = tempimage2.getImage();
					}
					
					break;
				}
				case ROBERTSCROSS: {
					RobertsCrossOp tempimage = new RobertsCrossOp(processedImage);
					processedImage = tempimage.getImage();
					//NonMaxSupression
					if(nonmaxsupenabled){
						NonMaxSupression tempimage2 = new NonMaxSupression(processedImage);
						processedImage = tempimage2.getImage();
					}
									
					break;
				}
				case PREWITT: {
					PrewittOp tempimage = new PrewittOp(processedImage);			
					processedImage = tempimage.getImage();
					//NonMaxSupression
					if(nonmaxsupenabled){
						NonMaxSupression tempimage2 = new NonMaxSupression(processedImage);
						processedImage = tempimage2.getImage();
					}
					
					break;
				}
				case SOBEL3X3: {
					Sobel3x3Op tempimage = new Sobel3x3Op(processedImage);
					processedImage = tempimage.getImage();
					//NonMaxSupression
					if(nonmaxsupenabled){
						NonMaxSupression tempimage2 = new NonMaxSupression(processedImage);
						processedImage = tempimage2.getImage();
					}				
								
					break;				
				}
				
				
			}
			if(thresholdingenabled){
				Threshold newThreshold = new Threshold(processedImage,thresholdval);
				processedImage = newThreshold.getImage();
			}
			
			ImagesData.imagesBFiltered.add(getProcessedImage());
			
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
		VisualData.jProgressBar.setValue(ImagesData.imagesB.size());
		VisualData.jProgressBar.repaint();
		
		
}
	
	

	public BufferedImage getProcessedImage() {
		return  toBufferedImage(processedImage);
	}

	
	
	public  BufferedImage toBufferedImage(Image image) {
	       if (image instanceof BufferedImage) {return (BufferedImage)image;}
	   
	       // This code ensures that all the pixels in the image are loaded
	       image = new ImageIcon(image).getImage();
	   
	       // Determine if the image has transparent pixels
	       boolean hasAlpha = hasAlpha(image);
	   
	       // Create a buffered image with a format that's compatible with the screen
	       BufferedImage bimage = null;
	       GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	       try {
	           // Determine the type of transparency of the new buffered image
	           int transparency = Transparency.OPAQUE;
	           if (hasAlpha == true) {transparency = Transparency.BITMASK;}
	   
	           // Create the buffered image
	           GraphicsDevice gs = ge.getDefaultScreenDevice();
	           GraphicsConfiguration gc = gs.getDefaultConfiguration();
	           bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
	       } 
	       catch (HeadlessException e) {} //No screen
	   
	       if (bimage == null) {
	           // Create a buffered image using the default color model
	           int type = BufferedImage.TYPE_INT_RGB;
	           if (hasAlpha == true) {type = BufferedImage.TYPE_INT_ARGB;}
	           bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
	       }
	   
	       // Copy image to buffered image
	       Graphics g = bimage.createGraphics();
	   
	       // Paint the image onto the buffered image
	       g.drawImage(image, 0, 0, null);
	       g.dispose();
	   
	       return bimage;
	   }

	     public static boolean hasAlpha(Image image) {
	    	 return false;
//	            // If buffered image, the color model is readily available
//	            if (image instanceof BufferedImage) {return ((BufferedImage)image).getColorModel().hasAlpha();}
//	        
//	            // Use a pixel grabber to retrieve the image's color model;
//	            // grabbing a single pixel is usually sufficient
//	            PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
//	            try {pg.grabPixels();} catch (InterruptedException e) {}
//	        
//	            // Get the image's color model
//	            return pg.getColorModel().hasAlpha();
	}

}
