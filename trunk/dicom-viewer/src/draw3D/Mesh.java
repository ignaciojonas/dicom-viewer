package draw3D;

import java.util.Hashtable;
import java.util.Vector;

import draw3D.scenario.screen.Point3D;
import draw3D.scenario.screen.Triangle3D;

public class Mesh {

	private static Vector<Triangle3D> triangles = new Vector<Triangle3D>();
	public static Vector<Point3D> pp = new Vector<Point3D>();
	public static String pointsSUR="";
	public static String trianglesSUR="";
	public static int po=1;
	
	
	
	public static void addTriangle(Triangle3D trian){
		triangles.add(trian);
	}
	public static Triangle3D getTriangle(int i){
		return triangles.get(i);
		
	}
	public static Vector<Triangle3D> getTriangles() {
		return triangles;
	}
	
	public static void completeSUR(Point3D p1,Point3D p2,Point3D p3){
//		int p1int =0;
//		int p2int =0;
//		int p3int =0;
//		
//		int r1 = pp.indexOf(p1);
//		if(r1 == -1){
//		
//			pp.add(po, p1);
//			p1int = po;
//			pointsSUR+=" "+po+" "+p1.toSUR()+"\n";
//			po++;
//		}else{
//			p1int=r1;
//		}
//		r1 =pp.indexOf(p2);
//		if(r1 == -1){
//			
//			pp.add(po,  p2);
//			p2int = po;
//			pointsSUR+=" "+po+" "+p2.toSUR()+"\n";
//			po++;
//		}else{
//			p2int=r1;
//		}
//		r1 = pp.indexOf(p3);
//		if(r1 == -1){
//			
//			pp.add(po,  p3);
//			p3int = po;
//			pointsSUR+=" "+po+" "+p3.toSUR()+"\n";
//			po++;
//		}else{
//			p3int=r1;
//		}
//		trianglesSUR+=p1int+" "+p2int+" "+p3int+"\n";
	}
	public static void addTriangles (Vector<Point3D> v1,Vector<Point3D> v2){
	

		
		int s1 = v1.size();
		
		Triangle3D t;
		int j=0;
		for(int i=0;i<s1-1;i++){
			j=i;
			Point3D p1 = v1.get(i);
			Point3D p2 = v1.get(i+1);
			Point3D p3 = v2.get(j);
			t=new Triangle3D(p1,p2,p3);
			triangles.add(t);
			completeSUR(p1,p2,p3);
			
			
			p1 = v1.get(i+1);
			p2 = v2.get(j+1);
			p3 = v2.get(j);
			t=new Triangle3D(p1,p2,p3);
			triangles.add(t);
			completeSUR(p1,p2,p3);
			
			if(i == (s1-2)){
				i++;
				j=i;
				
				p1 = v1.get(i);
				p2 = v1.get(0);
				p3 = v2.get(j);
				t=new Triangle3D(p1,p2,p3);
				triangles.add(t);
				completeSUR(p1,p2,p3);
				

				p1 = v1.get(0);
				p2 = v2.get(0);
				p3 = v2.get(j);
				t=new Triangle3D(p1,p2,p3);
				triangles.add(t);
				completeSUR(p1,p2,p3);
			}
		}
	}
	
}
