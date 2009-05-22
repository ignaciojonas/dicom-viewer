package data.io;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.media.jai.JAI;

import org.dcm4che.imageio.plugins.DcmImageReadParam;

import data.ImagesData;
import data.VisualData;

import ui.HandImage;

public class SaveFilteredImages extends Thread{

	Vector<BufferedImage> images;
	String path;
	int tab;
	public SaveFilteredImages(String path, int tab,Vector<BufferedImage> images) {
		this.images=images;
		this.path=path;
		this.tab=tab;
	}
	public void run(){
		System.out.println("images size "+images.size());
		System.out.println("imagesName size "+ImagesData.imagesBFilteredName.size());
		if(images.size()>0){
			VisualData.jProgressBar.setMaximum((int)images.size());
			VisualData.jProgressBar.setValue(0);
			VisualData.jProgressBar.setStringPainted(true);
			int progress=0;
			File folder=new File(path);
			folder.mkdir();
			int aux=0;
			for (int i=0;i<images.size(); i++) {
				BufferedImage bi = images.get(i);
				aux=i+1;
				String name;
				if (tab==0)
					name = "IMG_"+aux+".jpg";
				else
					name = ImagesData.imagesBFilteredName.get(i)+".bmp";
					name = name.replace(":", "_");
				JAI.create("filestore",bi, folder.getAbsolutePath()+"/"+name, "BMP");
				
				VisualData.jProgressBar.setValue(progress);  
		    	VisualData.jProgressBar.repaint();
				progress++;
			}   
			
			VisualData.jProgressBar.setValue(images.size());
			VisualData.jProgressBar.repaint();
		}
	}

}
