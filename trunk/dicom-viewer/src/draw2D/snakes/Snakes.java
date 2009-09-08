package draw2D.snakes;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.swing.SwingUtilities;

import data.ImagesData;
import data.VisualData;
import draw2D.CircleOnScreen;
import draw3D.Normal;
import draw3D.scenario.screen.Point3D;

import ui.HandImage;
import ui.MainFrame;

public class Snakes extends Thread{
	
	public static int BN_strech=10;
	public static int BN_bend=10;
	public static int BN_inflate=3;
	public static int BN_image=5;
	public static int BN_numSteps = 100;	//numero de pasos de deformacion 
	public static float BN_timeStep=0.005f;
	int BN_umbral;
	float BN_eps;
	float BN_err;
	int BN_met;
	int BN_kmax;
	float BN_alfa;
	int nx=0;
	int ny=0;
	

	BufferedImage image;
	HandImage handImage;
	
	private Vector<PointF> Nodes = new Vector<PointF>();
	private Vector<PointF> NodesAnt = new Vector<PointF>();
	private Vector<Normal> Normales;
	
	private Vector<PointF> Forces;
	private Vector<PointF> FStrech;
	private Vector<PointF> FBend;
	private Vector<PointF> FInflation;
	private Vector<PointF> FImage;
	
	private Vector<Gradient> Gradients;
	private Vector<Double> Potencial;
	
	private CircleOnScreen initCircle;
	private int cantPoints = 0;
	
	public Snakes(HandImage handImage,BufferedImage image) {
	
		this.image=image;
		nx = image.getWidth();
		ny = image.getHeight();
		this.handImage=handImage;
		initCircle = handImage.getInitCircle();
		cantPoints = initCircle.getCantPoints();
		
		Point p;
		for(int i=0;i< cantPoints;i++){
			p= initCircle.getPoint(i);
			Nodes.add(new PointF(p.x,p.y)); //Puntos del circulo inicial
			NodesAnt.add(new PointF(p.x,p.y)); //Clonar los mismos puntos inicales
		
			
		}
		
		
		Normales= new Vector<Normal>();
		
		Forces = new Vector<PointF> ();
		FStrech = new Vector<PointF> ();
		FBend = new Vector<PointF> ();
		FInflation = new Vector<PointF> ();
		FImage = new Vector<PointF> ();
		
		Gradients = new Vector<Gradient>();
		Potencial = new Vector<Double>();
		
		int tam = nx * ny;
		for(int i=0; i<tam; i++)
			  Gradients.add(new Gradient(new PointF(0,0),-1));		
		
		for(int i=0; i<=initCircle.getCantPoints(); i++)  
			  Potencial.add(new Double(-1));/* FALTA ESTO, NO SABEMOS QUE ES
			    if( BN_dirNorm )
		     		N_cambioNormal[i]= 0;//no hay cambio
			  */
	}

	public void CalculateStepDeformation()
	{
		CalculateNormals();
		InicialisateForces();
		FStrech();
		FBend();
		FInflation();
		FImage();
	}
	
	private void CalculateNormals() {
		//Caso extremo primer punto del circulo
		Normal n = getVectorNormal( Nodes.get(1), Nodes.get(0),Nodes.get(cantPoints-1));
		Normales.add(n);
		for(int i = 1;i<Nodes.size()-1;i++){
			n = getVectorNormal(Nodes.get(i+1), Nodes.get(i), Nodes.get(i-1));
			Normales.add(n);
		}
		//Caso extremo ultimo punto del circulo
		n = getVectorNormal(Nodes.get(0), Nodes.get(cantPoints-1), Nodes.get(cantPoints-2));
		Normales.add(n);
	}

	public PointF getNormal(PointF p1,PointF p2){
		float vx = p2.x - p1.x;
		float vy = p2.y - p1.y;
		
		return new PointF(-vy,vx);
	}
	private Vector normalsDraw = new Vector<PointF>();
	public Normal getVectorNormal(PointF p1,PointF p2,PointF p3){
		PointF aux1= getNormal(p1, p2);
		PointF aux2= getNormal(p2, p3);
		double x=(aux1.x+aux2.x)/2.0;
		double y=(aux1.y+aux2.y)/2.0;
		
//		normalsDraw.add(new Point((int) ((int)p2.x + x),(int)((int)p2.y + y)));
		
		double modulo = Math.sqrt((x*x + y*y));
		if(modulo==0)
			modulo=1;
		double xx =x/modulo;
		double yy=y/modulo;
		return new Normal(xx,yy);
		
	}

	
	private boolean CheckConvergence(int k, float difpaso_int) {
		double difx,dify,dif, difmax, difprom;
		PointF p,pant;
		difmax=0;  //difprom= 0;

		for(int i=0; i< cantPoints; i++) 
		{
			p = Nodes.get(i);
			pant = NodesAnt.get(i);
		 	  difx= Math.abs(p.x - pant.x);
			  dify= Math.abs(p.y - pant.y); //|xn - xn-1|
			  dif= Math.sqrt(difx*difx+dify*dify);
			  
//			  difprom+= dif;
			  if(dif>difmax)
				  difmax= dif;
		  
		}
//		difprom/= (double)cant_vertices;

		if( difmax>BN_eps )  //se puede considerar difprom
			return false;
		return true;
	}

	private void CalculateEulerStep(){
		PointF n;
		PointF s,b,in,im;
		PointF aux;
		for(int i=0;i< cantPoints;i++){
			n = Nodes.get(i);
			NodesAnt.add(new PointF(n.x,n.y));
			
			s = FStrech.get(i);
			b = FBend.get(i);
			in = FInflation.get(i);
			im = FImage.get(i);
			aux = new PointF();
			aux.x = (s.x*BN_strech - b.x*BN_bend + in.x*BN_inflate + im.x*BN_image);
			aux.y = (s.y*BN_strech - b.y*BN_bend + in.y*BN_inflate + im.y*BN_image);
			Forces.add(aux);
			
			Nodes.remove(i);
			PointF p=new PointF((aux.x * BN_timeStep),(aux.y * BN_timeStep));
			p.x+=n.x;
			p.y+=n.y;
			Nodes.add(i,p);
		}
	}
	
	
	private double GradientePto(Point p){
		float gr_x,gr_y,mod = 0;
		int dir = p.y*nx+p.x;
		Gradient g = Gradients.get(dir);
		
		if( g.getGmod()==-1 )  //si no está calculado
		{ 
			gr_x= getPointValue(new Point(p.x-1,p.y-1)) - getPointValue(new Point(p.x+1,p.y-1))
				 + 2*getPointValue(new Point(p.x-1,p.y))- 2*getPointValue(new Point(p.x+1,p.y))
				 + getPointValue(new Point(p.x-1,p.y+1))- getPointValue(new Point(p.x+1,p.y+1));

			gr_y= getPointValue(new Point(p.x-1,p.y+1)) - getPointValue(new Point(p.x-1,p.y-1))
				 +2*getPointValue(new Point(p.x,p.y+1)) - 2*getPointValue(new Point(p.x,p.y-1))
				 +getPointValue(new Point(p.x+1,p.y+1)) - getPointValue(new Point(p.x+1,p.y-1));

			mod= (float) Math.sqrt(gr_x*gr_x+gr_y*gr_y);

			g.setP(new PointF(gr_x,gr_y));
			
			g.setGmod(mod);
		}
		return mod;
	}
	

	public int getPointValue(Point p){
		return this.image.getRGB(p.x, p.y);
	}
	public double Potencial(Point p){
		 if( (p.x>0) && (p.y>0) && (p.x<nx)&&(p.y<ny))
			return -GradientePto(p);
		else 
			return -Double.MAX_VALUE; //salgo de la imagen-> valor alto
	}
	private void FImage() {
		double gpx,gpy,mod, len;
		PointF f;
		double dirp;
		Point p;
		PointF aux;
		Normal n;
		
		for(int i=0; i<cantPoints; i++)  
		{	
			aux = Nodes.get(i);
			p=new Point();
			p.x=(int) aux.x;
			p.y=(int) aux.y;
			
			  
			//if(N_frozen[i]) continue;

			gpx= Potencial(new Point(p.x-1,p.y-1)) - Potencial(new Point(p.x+1,p.y-1))	//aplica un Sobel pero habria que considerar un gradiente 
				 + 2*Potencial(new Point(p.x-1,p.y))- 2*Potencial(new Point(p.x+1,p.y))	//direccional considerando varios puntos (como en paper Cacic´08)
				 + Potencial(new Point(p.x-1,p.y+1))- Potencial(new Point(p.x+1,p.y+1));

			gpy= Potencial(new Point(p.x-1,p.y+1)) - Potencial(new Point(p.x-1,p.y-1))
				 +2*Potencial(new Point(p.x,p.y+1))- 2*Potencial(new Point(p.x,p.y-1))
				 +Potencial(new Point(p.x+1,p.y+1)) - Potencial(new Point(p.x+1,p.y-1));

		//	mod= sqrt(gpx*gpx+gpy*gpy);  
		//	if( mod>0 )
		//	{ gpx/= mod; gpy/= mod; }
		
			n = Normales.get(i);
			len= gpx*n.x + gpy*n.y;

			if( len>BN_image )
				len= BN_image;
			
			if( len< -BN_image )
				len= -BN_image;
		
			f = new PointF();
			f.x = (float) (len*n.x);//El cast lo agregue porque al fin y al cabo va a ser int 
			f.y = (float) (len*n.y);
			FImage.remove(i);
			FImage.add(i, f);
			
		} 
		
	}


	private int getNodeValue(int i){
		
//			long vx,vy dir;
//			vx= (long)(N_nodos[i].vX);
//			vy= (long)(N_nodos[i].vY);
//
//			dir= vy*nx + vx; 
//			if( 0<dir && dir<nx*ny )
//				return ivoxels[dir];  //ivoxels es el array asociado a la imagen
		PointF aux = Nodes.get(i);
		Point p=new Point();
		p.x=(int) aux.x;
		p.y=(int) aux.y;
		int x,y;
		x=p.x;
		y=p.y;
		
		
	if(x<0)
		x=0;
	if (y<0)
		y=0;
		
	if((x<0)||(y<0))
		
		if(p.x>image.getWidth())
			x=image.getWidth()-1;
		if(p.y>image.getHeight())
			y=image.getHeight()-1;
	
		return image.getRGB(x,y);

		
	}
	
	private void FInflation() {
		int val;
		int factor;
		PointF f;
		Normal n;
		
		for(int i=0; i<cantPoints; i++) 
		{	
//		 if( !N_cambioNormal[i] ) //si se mantiene la direccion de la normal, recalculo fuerza; sino nada
//		 { 
			//if(N_frozen[i]) continue;

			val= getNodeValue(i);
			
//			if (val == -16776961)
//				factor= 1;
//			else
//				factor= -1;
			
//			if( val >= BN_umbral )   //esto seguro no va a andar para el tipo de imagen de IVUS
//				if( Math.abs(val - media_reg) <= k*desvio_reg )  //y esto igualmente no creo... vamos a tener que considerar un entorno alrededor del pto. i
//					factor= 1;
//				else 
//					factor= -1;  //probar con -0.1
//
					
			//Ver las normales que dan 0.3132131
			factor=1;
			n = Normales.get(i);
			f = new PointF();
			f= n.getNormalAdd(f, factor);
			f.x=  (float) (n.x *factor);
			f.y=  (float) (n.y *factor);

			FInflation.remove(i);
			FInflation.add(i, f);
		 //} 
		} 
	}

	private void Bend(PointF one, PointF two, PointF three, int i){
		float distx,disty;
		PointF f = new PointF(0,0);
		distx= one.x - two.x;
		disty= one.y - two.y;
		f.x+= distx; 
		f.y+= disty;
				
		distx= one.x - three.x;
		disty= one.y - three.y;
		f.x+= distx; 
		f.y+= disty;

		FBend.remove(i);
		FBend.add(i, f);
	}

	private void FBend() {
		
		PointF one;
		PointF two;
		PointF three;
		
		//1° inicio
		one = FStrech.get(0);
		two = FStrech.get(cantPoints-1);
		three = FStrech.get(1);
		Bend(one, two, three, 0);
		for(int i=1; i< cantPoints-1; i++) 
		{	
//		 if( !N_cambioNormal[i] ) //si se mantiene la direccion de la normal, recalculo fuerza; sino nada
//		 { 
			
			one = FStrech.get(i);
			two = FStrech.get(i-1);
			three = FStrech.get(i+1);
			Bend(one, two, three, i);
		 //} 
		}
		one = FStrech.get(cantPoints-1);
		two = FStrech.get(cantPoints-2);
		three = FStrech.get(0);
		
		Bend(one, two, three, cantPoints-1);
		
	}


	private void Strech(PointF one, PointF two, PointF three, int i){
		float distx,disty;
		PointF f = new PointF(0,0);
		
		
		distx= one.x - two.x;
		disty= one.y - two.y;
		f.x+= distx; 
		f.y+= disty;
		
		distx= one.x - three.x;
		disty= one.y - three.y;
		f.x+= distx; 
		f.y+= disty;
		FStrech.remove(i);
		FStrech.add(i, f);
		
	}
	private void FStrech() {
		PointF f;
		PointF one;
		PointF two;
		PointF three;
	
		
		//1° inicio
		f = new PointF(0,0);
		one = Nodes.get(0);
		two = Nodes.get(cantPoints-1);
		three = Nodes.get(1);
		Strech(one, two, three, 0);
		
		for(int i=1; i< cantPoints-1; i++)	
		{	
//		  if( !(N_cambioNormal[i]) ) //si se mantiene la direccion de la normal, recalculo fuerza; sino nada
//		  { 
			f = new PointF(0,0);
			one = Nodes.get(i);
			two = Nodes.get(i-1);
			three = Nodes.get(i+1);
			Strech(one, two, three, i);
		  //}
		} 
		//fin
		one = Nodes.get(cantPoints-1);
		two = Nodes.get(cantPoints-2);
		three = Nodes.get(0);
		
		Strech(one, two, three, cantPoints-1);
	}


	private void InicialisateForces() {
		for(int i=0; i<cantPoints; i++) 
		{	
			Forces.add(new PointF(0,0));
			FStrech.add(new PointF(0,0));
			FBend.add(new PointF(0,0));
			FInflation.add(new PointF(0,0));
			FImage.add(new PointF(0,0));
			
		}
		
	}

	

	public void run(){

		 VisualData.jProgressBar.setMaximum(BN_numSteps);
		 VisualData.jProgressBar.setValue(0);
		 VisualData.jProgressBar.setStringPainted(true);
		    
		boolean converg= false;
		float difpaso_int= 0;
		Point p;
		for(int k=0; (k<BN_numSteps)&&(!converg); k++)
		{
			CalculateStepDeformation();
			CalculateEulerStep();
			converg= CheckConvergence(k,difpaso_int);
			VisualData.jProgressBar.setValue(k);  
	    	VisualData.jProgressBar.repaint();
	    	
	    	
	    	
		}

		Vector<Point> puntos=new Vector<Point>();
		PointF aux;
		Point pinit;
		int j=0;
		for (int i=0;i<cantPoints;i++){
			Point po=new Point();
			aux = Nodes.get(i);
			po.x=(int) aux.x;
			po.y=(int) aux.y;
			pinit=initCircle.getPoint(i);
			if((pinit.x==po.x)&&(pinit.y==po.y)){
				j++;
				System.out.println("No movió "+j);
			}
			puntos.add(po);
		}
		System.out.println(cantPoints);
		this.handImage.addToAllCircle(new CircleOnScreen(puntos,VisualData.enlargeColor),new CircleOnScreen(normalsDraw,VisualData.reduceColor));
		this.handImage.setImage();
		this.handImage.repaint();
		System.gc();
		VisualData.jProgressBar.setValue(BN_numSteps);
		VisualData.jProgressBar.repaint();

		
	}


}

