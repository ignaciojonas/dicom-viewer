package data.io;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Vector;

import ui.HandImage;


import data.ImagesData;
import draw2D.AllCirclesOnScreen;
import draw2D.CircleOnScreen;
import draw2D.InitCircleOnScreen;
import draw3D.Mesh;
import draw3D.Setup3D;
import draw3D.scenario.screen.Point3D;
import draw3D.scenario.screen.Triangle3D;

public class Project {
	private Vector<CircleOnScreen> circleAllPointsEnlarge = new Vector<CircleOnScreen>();
	private Vector<File> imagesPaths;
	private String pathMesh;
	private String path;
	



public Project(Vector<CircleOnScreen> circleAllPointsEnlarge,
			Vector<File> imagesPaths, String pathMesh) {
		super();
		this.circleAllPointsEnlarge = circleAllPointsEnlarge;
		this.imagesPaths = imagesPaths;
		this.pathMesh = pathMesh;
	}


public void restoreProject(HandImage handImage){
	
	File[] files= new File[imagesPaths.size()];
	int i=0;
	for (Iterator iterator = imagesPaths.iterator(); iterator.hasNext();) {
		File f = (File) iterator.next();
		files[i]=f;
		i++;
	}
	ImagesData.openDicom(files, handImage);
	OpenSUR os = new OpenSUR(pathMesh);
	handImage.setCircleAllPontsEnlarge(circleAllPointsEnlarge);
}

public void saveProject(String path){
	this.path=path;
	if(!path.endsWith(".rj")){
		this.path+=".rj";
	}
	FileWriter fw;
	BufferedWriter bw;
	PrintWriter pw = null;
	try {
		fw = new FileWriter(this.path);
		bw = new BufferedWriter (fw);
		pw = new PrintWriter (bw);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}//se crea el archivo file.txt
	pw.println("Images Path:");
	for (Iterator iterator = imagesPaths.iterator(); iterator.hasNext();) {
		File image = (File) iterator.next();
		pw.println(image.getAbsolutePath());	
	}
	pw.println("Mesh Path:");
	pw.println(pathMesh);
	pw.println("StartSequence:");
	pw.println(Mesh.startSequence);
	pw.println("EndSequence:");
	pw.println(Mesh.endSequence);
	pw.println("Sequences:");
	pw.println(Mesh.sequences);
	
	pw.println("CircleSize:");
	pw.println(Mesh.circleSize);
	pw.println("Size:");
	pw.println(Mesh.size);
	pw.println("Enlarge Circles:");
	for (Iterator iterator = circleAllPointsEnlarge.iterator(); iterator.hasNext();) {
		CircleOnScreen circle = (CircleOnScreen) iterator.next();
		pw.println("Color");
		pw.println(circle.getColor().getRGB());
		pw.println("Points");
		Vector<Point> points= circle.getCirclePoints();
		for (Iterator it = points.iterator(); it.hasNext();) {
			Point point = (Point) it.next();
			pw.println(point.x+":"+point.y);	
		}
	}
	pw.close();

	
}

public Project(String path) {
	circleAllPointsEnlarge = new Vector<CircleOnScreen>();
	imagesPaths= new Vector<File>();
	this.path=path;
	FileReader fr;
	BufferedReader br = null;
	try {
		fr = new FileReader(path);
		br = new BufferedReader (fr); 
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}//se crea el archivo file.txt
	String linea=null;
	String point[];
	Point p;
	int x,y=0;
	try {
		while((linea=br.readLine())!=null){
			if(linea.equals("Images Path:")){
				while(!(linea=br.readLine()).equals("Mesh Path:")){
					File f = new File(linea);
					imagesPaths.add(f);
				}
				this.pathMesh=br.readLine();
				Setup3D.pathMesh =pathMesh; 
				if((linea=br.readLine()).equals("StartSequence:")){
					Mesh.startSequence=Integer.parseInt(br.readLine());
				}
				if((linea=br.readLine()).equals("EndSequence:")){
					Mesh.endSequence=Integer.parseInt(br.readLine());
				}
				if((linea=br.readLine()).equals("Sequences:")){
					Mesh.sequences=Boolean.parseBoolean(br.readLine());
				}
				if((linea=br.readLine()).equals("CircleSize:")){
					Mesh.circleSize=Integer.parseInt(br.readLine());
				}
				if((linea=br.readLine()).equals("Size:")){
					Mesh.size=Integer.parseInt(br.readLine());
				}
		//		br.readLine();
				if((linea=br.readLine()).equals("Enlarge Circles:")){
					linea=br.readLine();
					while(linea!=null){
						if(linea.equals("Color")){
							int Color=Integer.parseInt(br.readLine());
							Vector<Point> ret=new Vector<Point>();
							if((linea=br.readLine()).equals("Points")){
								while((linea=br.readLine())!=null){
									if(linea.equals("Color"))
										break;
									point=linea.split(":");
									x=Integer.parseInt(point[0]);
									y=Integer.parseInt(point[1]);
									p=new Point(x,y);
									ret.add(p);
								}
								CircleOnScreen circle=new CircleOnScreen(ret,new java.awt.Color(Color));
								this.circleAllPointsEnlarge.add(circle);
							}
						}
						
					}
				}
			}

		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}


}
