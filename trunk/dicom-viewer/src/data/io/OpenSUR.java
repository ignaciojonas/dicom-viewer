package data.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import draw3D.Mesh;
import draw3D.scenario.screen.Point3D;
import draw3D.scenario.screen.Triangle3D;



public class OpenSUR{
	
	private int dimension=-1;
	private  Vector<Triangle3D> triangles = new Vector<Triangle3D>();
	public  Vector<Point3D> points = new Vector<Point3D>();
	private int cantPoint;
	protected Vector<MyPolygon> polygons = new Vector<MyPolygon>();
	
	public OpenSUR(String path) {
		super();
		openFile(path);
		Mesh.setPoints(points);
		Mesh.setTriangles(triangles);
		Mesh.cantPoints3D=this.cantPoint;
		Mesh.generateTrianglesSteps();
		
		
	}
	

	private void openFile(String path){
		String line;
		boolean startPol = false;
		boolean startPoint = false;
		boolean startVolume = false;
		try {
			BufferedReader faux = new BufferedReader(new FileReader(path));
			line = faux.readLine();
			while (line != null){
				if (line.startsWith("*INCIDENCE"))
					startPol=true;
				if (line.startsWith("*COORDINATES")){
					startPol=false;
					startPoint=true;
				}
				if (line.startsWith("*FRONT_VOLUME")){
					startPol=false;
					startPoint=true;
					startVolume=true;
				}
				
				if(startPol)
					addPoligon(line);

				if(startPoint)
					addPoint(line);
				
				if(startVolume)
					addVolume(line);
				line = faux.readLine();
			}
			setAllPoligongs();
			faux.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	


	private void addVolume(String line) {
		
		
	}


	private void setAllPoligongs() {
		MyPolygon p;
		int p1,p2,p3;
		for(int i=0;i<polygons.size();i++){
			p = polygons.get(i);
			p1=p.getInt1()-1;
			p2=p.getInt2()-1;
			p3=p.getInt3()-1;
			triangles.add(new Triangle3D(points.get(p1),points.get(p2),points.get(p3)));
			
			
		}
		
	}

	private void addPoint(String line) {
		
		
		cantPoint++;
	
		String[] split = line.split(" ");
		float p1 = 0;
		float p2 = 0;
		float p3 = 0;
		int pos=0;
		if(split.length>2){
			for (int i = 0; i < split.length; i++) {
				if(!split[i].equals("")){
					if(pos==1){
						p1=Float.parseFloat(split[i]);
					}
					else{
						if(pos==2){
							p2=Float.parseFloat(split[i]);
						}
						else{
							if(pos==3){
								p3=Float.parseFloat(split[i]);
							}
						}
					}
					pos++;
				}
				
			}
			points.add(new Point3D(p1,p2,p3));

		}
		
	}


	private void addPoligon(String line) {
	
		String[] split = line.split(" ");
		int p1;
		int p2;
		int p3;
		if(split.length>1){
			p1=Integer.parseInt(split[0]);
			p2=Integer.parseInt(split[1]);
			p3=Integer.parseInt(split[2]);
			polygons.add(new MyPolygon(p1,p2,p3));
			
		}
		
	}
}
