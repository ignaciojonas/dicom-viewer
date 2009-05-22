package data.io;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.dcm4che.imageio.plugins.DcmImageReadParam;

import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.display.SourceImage;

import data.ImagesData;
import data.VisualData;

import ui.HandImage;

public class OpenDicomDir extends Thread{

	String file;
	HandImage handImage;
	public OpenDicomDir(String paths, HandImage handImage) {
		this.file=paths;
		this.handImage=handImage;
	}
	public void run(){
		VisualData.jProgressBar.setMaximum(100);
	    VisualData.jProgressBar.setValue(1);
	    VisualData.jProgressBar.setStringPainted(true);

	SourceImage[] sImgs = new SourceImage[1];
		
		int widthMax=0;
		int heightMax=0;
		String title=null;
		int w=1;
		int imgCount=0;

			String fileName=file;

			try {
				com.pixelmed.dicom.DicomInputStream di = null;
				try {
					di=new com.pixelmed.dicom.DicomInputStream(new FileInputStream(fileName));
				}
				catch (FileNotFoundException e) {
					di=new com.pixelmed.dicom.DicomInputStream(new FileInputStream(fileName.toLowerCase()));
				}

				AttributeList list = new AttributeList();
				list.read(di);
				di.close();
				if (list.isImage()) {
					
					SourceImage sImg=new SourceImage(list);
					BufferedImage img=sImg.getBufferedImage();
					if (sImg.getWidth() > widthMax) widthMax=sImg.getWidth();
					if (sImg.getHeight() > heightMax) heightMax=sImg.getHeight();
					if (title == null) title=sImg.getTitle();
					sImgs[imgCount++]=sImg;
					
					VisualData.jProgressBar.setValue(50);  
			    	VisualData.jProgressBar.repaint();
			    	w++;
			    	
			    	if(w==0){
			    		handImage.setPreferredSize(new Dimension(ImagesData.imagesB.get(0).getHeight()+1,ImagesData.imagesB.get(0).getWidth()+1));
						//handImage.setImage();
						//handImage.repaint();
			    	}
				}
				
				else {
					throw new DicomException("Unsupported SOP instance type");
				}
			} catch (Exception e) {
				System.err.println(e);
				e.printStackTrace(System.err);
			}
		//}
		int total = 0;
		if (imgCount > 0) {
			for (int count=0; count < imgCount; ++count) {
				SourceImage sImg=sImgs[count];
				
				total=sImg.getNumberOfBufferedImages();
				
				
				for (int j = 0; j < total ; j++) {
					ImagesData.imagesB.add(sImg.getBufferedImage(j));
					
					}
				}
			
			}

		handImage.setImage();
		//handImage.repaint();
		VisualData.jProgressBar.setValue(total);
		VisualData.jProgressBar.repaint();
		}

	}
