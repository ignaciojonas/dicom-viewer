package data.io;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Vector;

public class SaveFile {
private String path;
private PrintWriter pw = null;

public SaveFile(String path) {
		this.path=path;
		if(!path.endsWith(".crl")){
			this.path+=".crl";
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
	public void save(Vector<Point> points){
		for (Iterator iterator = points.iterator(); iterator.hasNext();) {
			Point point = (Point) iterator.next();
			pw.println(point.x+":"+point.y);	
		}
		pw.close();
	}
}
