package data.io;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Vector;

public class LoadFile {
	private String path;
	private BufferedReader br;

	public LoadFile(String path) {
			this.path=path;
			FileReader fr;
			try {
				fr = new FileReader(path);
				br = new BufferedReader (fr); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//se crea el archivo file.txt
	}

	public Vector<Point> load(){
		String linea=null;
		String point[];
		Point p;
		Vector<Point> ret=new Vector<Point>();
		int x,y=0;
		try {
			while((linea=br.readLine())!=null){
				point=linea.split(":");
				x=Integer.parseInt(point[0]);
				y=Integer.parseInt(point[1]);
				p=new Point(x,y);
				ret.add(p);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
}
