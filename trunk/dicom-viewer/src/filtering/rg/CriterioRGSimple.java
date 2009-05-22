package filtering.rg;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class CriterioRGSimple extends Criterio{

	public CriterioRGSimple(int radio,double k) {
		super(radio,k);
		this.type="RGSimple";
		
	}

	@Override
	public boolean evaluar(Point point, BufferedImage bi,double desvio,double intensidadCaracteristica) {
		int auxRGB = bi.getRGB(point.x, point.y);
		int intensidad=new Color(auxRGB).getRed();
		if(Math.abs(intensidad-intensidadCaracteristica)<=(K * desvio))
			return true;
		return false;
	}
	public String getStringName() {
		String name="RGSimple radio:"+radio+" k:"+K;
		return name;
	}

}
