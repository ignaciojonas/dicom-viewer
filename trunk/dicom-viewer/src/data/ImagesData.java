package data;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.dcm4che.imageio.plugins.DcmImageReadParam;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.ElementDictionary;
import org.dcm4che2.io.DicomInputStream;

import ui.HandImage;

import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.SpatialCoordinateAndImageReference;
import com.pixelmed.display.SourceImage;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import data.io.DicomProperties;
import data.io.OpenDicom;
import data.io.OpenDicomDir;

import filtering.MeanFilter;

public class ImagesData {
	public static Vector<BufferedImage> imagesB = new Vector<BufferedImage>();
	public static Vector<BufferedImage> imagesBFiltered = new Vector<BufferedImage>();
	public static Vector<String> imagesBFilteredName = new Vector<String>();
	public static Vector<DicomProperties> imagesProperties = new Vector<DicomProperties>();
	public static Vector<File> imagesPaths = new Vector<File>();
	
	
	public static void addImagePath(File file){
		imagesPaths.add(file);
	}
	
	
	public static void processImageProperties(){
		
		imagesProperties.clear();
		for(int i=0;i<imagesPaths.size();i++){
			File file = imagesPaths.get(i);
			DicomObject dcmObj;
			DicomInputStream din = null;
			try {

				din = new DicomInputStream(file);

				dcmObj = din.readDicomObject();
				DicomProperties dicomProper=new DicomProperties();

				//recorro todos los datos que tiene dentro el DICOM
				for (Iterator iterator = dcmObj.iterator(); iterator.hasNext();) {
					DicomElement de = (DicomElement) iterator.next();
					//obtengo el string con la info
					String a =de.toString();
					//Creo un diccionario que me sabe decir, dado un tag (int), como se llama ese atributo
					ElementDictionary ed = ElementDictionary.getDictionary();
					//Obtengo el nombre del atributo
					//System.out.print(ed.nameOf(de.tag())+"  -  ");

					//corto la info que necesito del string

					//	System.out.println(a.substring(a.indexOf("[")+1, a.indexOf("]")));

					if(!ed.nameOf(de.tag()).equals("Pixel Data"))
						dicomProper.addPropertie(ed.nameOf(de.tag()), a.substring(a.indexOf("[")+1, a.indexOf("]")));

				}
				imagesProperties.add(dicomProper);

			}
			catch (IOException e) {
				e.printStackTrace();
				return;
			}
			finally {
				try {
					din.close();
				}
				catch (IOException ignore) {
				}
			}
		}
	
	}
	

	

	public static void cloneImagesBToImagesFiltered() {
		for (int i = 0; i < imagesB.size(); i++) {
			imagesBFiltered.add(i,clon(imagesB.get(i)));
		}
		
	}

	public static BufferedImage clon(BufferedImage imagen){
				
		BufferedImage copia=new BufferedImage (imagen.getWidth(),imagen.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
		copia.setData(imagen.getData());
		return copia;
		}
	
	private static BufferedImage cloneBufferedImage(BufferedImage temp) {
		BufferedImage newBI = new BufferedImage(temp.getHeight(),temp.getWidth(), BufferedImage.TYPE_INT_RGB);
		newBI.setData(temp.getData());
		
//		BufferedImage clone = image.getSubimage(
//				0, 0, image.getWidth(), image.getHeight()
//				);
		return newBI;
	}

	public static void openDicom(File[] file, HandImage handImage) {
		OpenDicom o = new OpenDicom(file,handImage);
		o.start();
		
	}
	public static void openDicomDir(String paths, HandImage handImage) {
		OpenDicomDir o = new OpenDicomDir(paths,handImage);
		o.start();
		
	}


	public static void deleteFilteredImages() {
		//TODO 
		imagesBFiltered.removeAllElements();
		imagesBFilteredName.removeAllElements();
		
	}
}
