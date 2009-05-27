package data.io;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Vector;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;

import draw3D.Mesh;
import draw3D.scenario.screen.Triangle3D;

public class SaveMeshSur {
private String path;
private PrintWriter pw = null;

public SaveMeshSur(String path) {
		this.path=path;
		if(!path.endsWith(".sur")){
			this.path+=".sur";
		}
		FileWriter fw;
		BufferedWriter bw;
		try {
			fw = new FileWriter(this.path);
			bw = new BufferedWriter (fw);
			pw = new PrintWriter (bw);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//se crea el archivo file.txt
	}
	
	public void save2(){
		Vector<Triangle3D> mesh = Mesh.getTriangles();
		Hashtable pointNum=new Hashtable();
		String points="";
		String triangles="";
		String point;
		int cont=1;
	
		pw.println("*ELEMENT GROUPS");
		pw.println(" 1");
		pw.println("1 "+mesh.size()+" Tri3");
		pw.println("");
		pw.println("*INCIDENCE");
		System.out.println("escribiendo ");
		pw.println(Mesh.trianglesSUR);
		pw.println("*COORDINATES");
		
		pw.println(" "+Mesh.cantPoints3D);
		
		pw.println(Mesh.pointsSUR);
		pw.println("*FRONT_VOLUME");
		pw.println(" -0.683257 0.296420 0.000000 5.982628 -0.436983 0.436896");
		pw.close();
	}
}
