package draw2D.filtering.rg;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Vector;

public abstract class Criterio {

	int radio=0;
	double K=0;
	String type="Default";
	public Criterio(int radio, double k2) {
		this.radio=radio;
		this.K=k2;
	}
	
	public String getType() {
		return type;
	}
	public int getRadio() {
		return radio;
	}
	public Vector<Point> getNeight(Point aux, int radio2, BufferedImage bi) {
		int x= aux.x;
		int y= aux.y;
		
		int xmin=x-radio2;
		int ymin=y-radio2;
		int xmax=x+radio2;
		int ymax=y+radio2;
		
		if (x-radio2 < 0)
			xmin=0;
		if (y-radio2 < 0)
			ymin=0;
		
		if (x+radio2 > bi.getWidth())
			xmax= bi.getWidth();
		if (y+radio2 > bi.getHeight())
			ymax=bi.getHeight();
		
	
		Vector ret=new Vector();
		for (int i = xmin; i <= xmax; i++) {
			for (int j = ymin; j <= ymax; j++) {
					ret.add(new Point(i,j));
				
			}
		}

		return ret;
	}
	public boolean evaluar(Point point, BufferedImage bi, double desvio,
			double intensidadCaracteristica) {
		// TODO Auto-generated method stub
		return false;
	}

	public abstract String getStringName() ;
	
}
