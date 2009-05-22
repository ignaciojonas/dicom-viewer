package data.io;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.dcm4che.imageio.plugins.DcmImageReadParam;

import data.ImagesData;
import data.VisualData;

import ui.HandImage;

public class OpenDicom extends Thread{

	File[] file;
	HandImage handImage;
	public OpenDicom(File[] file, HandImage handImage) {
		this.file=file;
		this.handImage=handImage;
	}
	public void run(){
		  VisualData.jProgressBar.setMaximum((int)file.length);
		    VisualData.jProgressBar.setValue(0);
		    VisualData.jProgressBar.setStringPainted(true);
		    
		for(int i=0; i<file.length;i++){
			ImagesData.addImagePath(file[i]);
			processImage(file[i]);
			
			VisualData.jProgressBar.setValue(i);  
	    	VisualData.jProgressBar.repaint();
	    	if(i==0){
	    		handImage.setPreferredSize(new Dimension(ImagesData.imagesB.get(0).getHeight()+1,ImagesData.imagesB.get(0).getWidth()+1));
				handImage.setImage();
				
	    	}
			//ImagesData.processImageProperties(file[i]);
		}
		VisualData.jProgressBar.setValue(file.length);
		VisualData.jProgressBar.repaint();
		
	}
	private void processImage( File file){
    	ImageInputStream iis;
    	try {
    		Iterator iter = ImageIO.getImageReadersByFormatName("DICOM");
    		ImageReader reader = (ImageReader)iter.next();
    		DcmImageReadParam param = (DcmImageReadParam) reader.getDefaultReadParam();
    		param.setPValToDDL(null);
    		iis = ImageIO.createImageInputStream(file);
    		BufferedImage bi;
    		try {
    			reader.setInput(iis, false);
    			bi = reader.read(0, param);

    			if (bi == null) {
    				System.out.println("\nError: " + file + " - couldn't read DICOM format!");
    				return;
    			}
    		} finally {
    			try { iis.close(); } catch (IOException ignore) {}
    		}
    		ImagesData.imagesB.add(bi);
    	} catch (IOException e) {
    		try {
    			BufferedImage bi;
				iis = ImageIO.createImageInputStream(file);
				Iterator iter = ImageIO.getImageReaders(iis);
	    		ImageReader reader = (ImageReader)iter.next();
	    		reader.setInput(iis, false);
	    		bi = reader.read(0, reader.getDefaultReadParam());
	    		ImagesData.imagesB.add(bi);
			} catch (IOException e1) {
				System.out.println("\nError: " + file + " - couldn't read any format!");
			}
    		
    		
    	}
	}
}
