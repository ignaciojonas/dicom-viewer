package draw3D;

import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;

import draw3D.scenario.screen.Point3D;
import draw3D.scenario.screen.Triangle3D;

public class Mesh {

	private static Vector<Triangle3D> triangles = new Vector<Triangle3D>();
	public static Vector<Point3D> points = new Vector<Point3D>();
	public static int cantPoints3D =0;
	public static String pointsSUR="";
	public static String trianglesSUR="";
	public static float distanceCircles = 0.3f;
	
	public static Vector<Vector> trianglesStep = new Vector<Vector>();
	public static int startSequence = 5;
	public static int endSequence = 10;
	public static boolean sequences = false;
	public static int circleSize = 0;
	public static int size = 0; //Cantidad de circulos en total 
	 
	public static void generateTrianglesSteps(){
		sequences = true;
		int j=0;
		int i=0;
		int triang = 0;
		Triangle3D t;
		int endCircle;
		
		Vector auxStep = new Vector<Triangle3D>();
		int longStep = endSequence - startSequence;
		int auxLong = 0;
		
		for (int nroCirc =0;nroCirc<size-1;nroCirc++){
			endCircle = (nroCirc*circleSize)+circleSize;
			for (i = nroCirc*circleSize; i < endCircle - 1; i++) {
				j = i +circleSize;
		
				auxStep.add(triangles.get(triang));
				triang++;

				auxStep.add(triangles.get(triang));
				triang++;
	
				if(i == (endCircle -2)){
					i++;
					j++;
			
					auxStep.add(triangles.get(triang));
					triang++;
				
					auxStep.add(triangles.get(triang));
					triang++;

				}
			}	
			auxLong ++;
			if(nroCirc == startSequence){
				trianglesStep.add(auxStep);
				
				auxStep = new Vector<Triangle3D>();
				auxLong =0;
			}else
				if((auxLong == longStep)|| (nroCirc==size-2)){
					trianglesStep.add(auxStep);
					
					auxStep = new Vector<Triangle3D>();
					auxLong =0;
				}
			
		}
		
	}

	public static void setTriangles(Vector<Triangle3D> triangles) {
		Mesh.triangles = triangles;
	}
	public static void setPoints(Vector<Point3D> points) {
		Mesh.points = points;
	}
	
	public static void addTriangle(Triangle3D trian){
		triangles.add(trian);
	}
	public static Triangle3D getTriangle(int i){
		return triangles.get(i);
		
	}
	public static Vector<Triangle3D> getTriangles() {
		return triangles;
	}
	
	public static Vector<Vector> getTrianglesStep() {
		return trianglesStep;
	}
	
	private static void addTriangleSUR(int p1,int p2,int p3){
		Mesh.trianglesSUR+=(p1+1)+" "+(p2+1)+" "+(p3+1)+"\n";
	}
	
	public static void generateTriangles(){
		sequences = true;
		int j=0;
		int i=0;
		Triangle3D t;
		int endCircle;
		
		Vector auxStep = new Vector<Triangle3D>();
		int longStep = endSequence - startSequence;
		int auxLong = 0;
		
		for (int nroCirc =0;nroCirc<size-1;nroCirc++){
			endCircle = (nroCirc*circleSize)+circleSize;
			for (i = nroCirc*circleSize; i < endCircle - 1; i++) {
				j = i +circleSize;
				
				Point3D p1 = points.get(i);
				Point3D p2 = points.get(i+1);
				Point3D p3 = points.get(j);
				t=new Triangle3D(p1,p2,p3);
				triangles.add(t);
				auxStep.add(t);
				addTriangleSUR(i, i+1, j);
				
				p1 = points.get(i+1);
				p2 = points.get(j+1);
				p3 = points.get(j);
				t=new Triangle3D(p1,p2,p3);
				triangles.add(t);
				auxStep.add(t);
				addTriangleSUR(i+1,j+1,j);
				
				
				if(i == (endCircle -2)){
					i++;
					j++;
					
					p1 = points.get(i);
					p2 = points.get(nroCirc*circleSize);
					p3 = points.get(j);
					t=new Triangle3D(p1,p2,p3);
					triangles.add(t);
					auxStep.add(t);
					addTriangleSUR(i,(nroCirc*circleSize),j);
					

					p1 = points.get(nroCirc*circleSize);
					p2 = points.get(nroCirc*circleSize+circleSize);
					p3 = points.get(j);
					t=new Triangle3D(p1,p2,p3);
					triangles.add(t);
					auxStep.add(t);
					addTriangleSUR(nroCirc*circleSize, (nroCirc*circleSize+circleSize), j);
					
					
				}
			}	
			auxLong ++;
			if(nroCirc == startSequence){
				trianglesStep.add(auxStep);
				
				auxStep = new Vector<Triangle3D>();
				auxLong =0;
			}else
				if((auxLong == longStep)|| (nroCirc==size-2)){
					trianglesStep.add(auxStep);
					
					auxStep = new Vector<Triangle3D>();
					auxLong =0;
				}
			
		}
		
	}
	
	
}
