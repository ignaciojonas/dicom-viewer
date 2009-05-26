package draw3D;

import java.util.Vector;

import draw3D.scenario.screen.Point3D;
import draw3D.scenario.screen.Triangle3D;

public class Mesh {

	private static Vector<Triangle3D> triangles = new Vector<Triangle3D>();
	
	public static void addTriangle(Triangle3D trian){
		triangles.add(trian);
	}
	public static Triangle3D getTriangle(int i){
		return triangles.get(i);
		
	}
	public static Vector<Triangle3D> getTriangles() {
		return triangles;
	}
	public static void addTriangles (Vector<Point3D> v1,Vector<Point3D> v2){
		
		int s1 = v1.size();
		
		Triangle3D t;
		int j=0;
		for(int i=0;i<s1-1;i++){
			j=i;
			t=new Triangle3D(v1.get(i),v1.get(i+1),v2.get(j));
			triangles.add(t);
			
			t=new Triangle3D(v1.get(i+1),v2.get(j+1),v2.get(j));
			triangles.add(t);
			
			if(i == (s1-2)){
				i++;
				j=i;
				t=new Triangle3D(v1.get(i),v1.get(0),v2.get(j));
				triangles.add(t);
				
				t=new Triangle3D(v1.get(0),v2.get(0),v2.get(j));
				triangles.add(t);
			}
		}
	}
	
}
