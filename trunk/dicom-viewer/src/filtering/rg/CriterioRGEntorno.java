package filtering.rg;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Vector;

public class CriterioRGEntorno extends Criterio{

	int radioEntorno=0;
	public CriterioRGEntorno(int radio,double k,int radioEntorno) {
		super(radio,k);
		this.radioEntorno=radioEntorno;
		this.type="RGEntorno";
		
	}
	
	@Override
	public boolean evaluar(Point point, BufferedImage bi,double desvio,double intensidadCaracteristica) {
		Vector<Point> s = getNeight(point, radioEntorno, bi);
		int auxRGB = bi.getRGB(point.x, point.y);
		int intensidad=new Color(auxRGB).getRed();
		int n=1;
		for (Iterator iterator = s.iterator(); iterator.hasNext();) {
			Point aux = (Point) iterator.next();
			auxRGB=bi.getRGB(aux.x, aux.y);
			intensidad+=new Color(auxRGB).getRed();
			n++;
		}
		double intensidadEntorno=intensidad/n;
		
		if(Math.abs(intensidadEntorno-intensidadCaracteristica)<=(K * desvio))
			return true;
		return false;
	}

	@Override
	public String getStringName() {
		String name="RGEntorno radio:"+radio+" k:"+K+" around:"+this.radioEntorno;
		return name;
	}

}
