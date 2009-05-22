package filtering.rg;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Vector;

public class CriterioRGGradiente extends Criterio{

	double pg=0;
	double maxG=0;
	public CriterioRGGradiente(int radio,double k,double pg,double maxG) {
		super(radio,k);
		this.type="RGGradiente";
		this.maxG=maxG;
		this.pg=pg;
	}

	@Override
	public boolean evaluar(Point point, BufferedImage bi,double desvio,double intensidadCaracteristica) {
		//System.err.println("bi bounds: "+bi.getRGB(bi.getWidth(), bi.getHeight()));
		double gradiente=0;
		double gradiente_x=0;
		double gradiente_y=0;
		int rReal_x=radio;
		int rReal_y=radio;
		if((point.x+radio)>bi.getWidth()-1){
			rReal_x=bi.getWidth()-point.x-2;
		}
		if((point.y+radio)>bi.getHeight()-1){
			rReal_y=bi.getHeight()-point.y-2;
		}
		if((point.x-radio)<0){
			rReal_x=point.x;
		}
		if((point.y-radio)<0){
			rReal_y=point.y;
		}
		int intensidadX=0;
		int intensidadY=0;
		if(rReal_x!=0){
			for (int i = 1; i <= rReal_x; i++) {
//				int a=point.x+i;
//				int b=point.x-i;
//				System.out.println("x+i: "+a);
//				System.out.println("x-i: "+b);
//				System.out.println("bi w: "+bi.getWidth());
				int rgb1=bi.getRGB(point.x+i, point.y);
				int rgb2=bi.getRGB(point.x-i, point.y);
				double intensidad1=new Color(rgb1).getRed();
				double intensidad2=new Color(rgb2).getRed();
				intensidadX+=Math.abs(intensidad1-intensidad2);
			}
			gradiente_x=intensidadX/rReal_x;
		}
		if(rReal_y!=0){
			for (int i = 1; i <= rReal_y; i++) {
				try{
					intensidadY+=Math.abs(new Color(bi.getRGB(point.x, point.y+i)).getRed()-new Color(bi.getRGB(point.x, point.y-i)).getRed());
				}catch(Exception e){
					int a=point.y+i;
					int b=point.y-i;
					
				}
			}
			gradiente_y=intensidadY/rReal_y;
		}
		if(intensidadX<intensidadY){
		  gradiente=gradiente_y;
		
		}
		else{
			gradiente=gradiente_x;
		}
		
		if(gradiente<=(pg*maxG)){
			return true;
		}
		
		return false;
	}
	public String getStringName() {
		String name="RGGradiente radio:"+radio+" k:"+K+" pg:"+pg+" maxG:"+maxG;
		return name;
	}

}
