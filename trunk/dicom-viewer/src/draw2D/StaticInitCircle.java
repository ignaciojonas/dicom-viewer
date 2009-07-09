package draw2D;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Vector;

import draw3D.Normal;

import ui.HandImage;

public class StaticInitCircle {
	public static int POINTSBETWEEN = 10;

	public static boolean drawPC = false;
	public static boolean manualyC = false;
	
	//Mover el circulo
	public static Point origen;
	
	//Draw Perfect Circle
	public static int DPCxC;
	public static int DPCyC;
	
	//Mover un solo punto
	public static Point origenOP;
	public static Point leftOOP;
	public static Point rightOOP;
	public static Point destinoOP;
	public static int indexMoveOP= -1;
	
	public static int neighborhoodInvolved = 16;
	public static double neighborhoodFact = 0.7;
	
	
	
	public static  void drawPerfectCircle(MouseEvent evt,HandImage handImage) {

		int radio = Math.abs(StaticInitCircle.DPCxC - evt.getX())/2;
		StaticInitCircle.DPCxC = StaticInitCircle.DPCxC +radio;
		StaticInitCircle.DPCyC = StaticInitCircle.DPCyC +Math.abs(StaticInitCircle.DPCyC - evt.getY())/2;
		
		Vector<Point> circle = new Vector<Point>();
		int sum = 10 - POINTSBETWEEN/10;
		for (int x = radio; x >= 0; x -= sum) {
			double y = Math.sqrt((Math.pow(radio, 2) - (Math.pow(x, 2))));
			circle.add(new Point(-(x) + StaticInitCircle.DPCxC, new Double(-(y) + StaticInitCircle.DPCyC).intValue()));
		}
		for (int x = 0; x <= radio; x += sum) {
			double y = Math.sqrt((Math.pow(radio, 2) - (Math.pow(x, 2))));
			circle.add(new Point(x + StaticInitCircle.DPCxC, new Double(-(y) + StaticInitCircle.DPCyC).intValue()));
		}
		for (int x = radio; x >= 0; x -= sum) {
			double y = Math.sqrt((Math.pow(radio, 2) - (Math.pow(x, 2))));
			circle.add(new Point(x + StaticInitCircle.DPCxC, new Double(y + StaticInitCircle.DPCyC).intValue()));
		}
		for (int x = 0; x <= radio; x += sum) {
			double y = Math.sqrt((Math.pow(radio, 2) - (Math.pow(x, 2))));
			circle.add(new Point(-(x) + StaticInitCircle.DPCxC, new Double(y + StaticInitCircle.DPCyC).intValue()));
		}
		handImage.setCirclePoints(circle);
		
		StaticInitCircle.DPCxC = StaticInitCircle.DPCxC -radio;
		StaticInitCircle.DPCyC = StaticInitCircle.DPCyC -Math.abs(StaticInitCircle.DPCyC - evt.getY())/2;
		
		handImage.setImage();
		
	}
	public static void moveInitCircle(MouseEvent evt,HandImage handImage) {
		InitCircleOnScreen circle=handImage.getInitCircle();
		if(StaticInitCircle.origen!=null){
			int x_origen= StaticInitCircle.origen.x;
			int y_origen= StaticInitCircle.origen.y;
			int sum_x=evt.getX()-x_origen;
			int sum_y=evt.getY()-y_origen;
			circle.sumarXY(sum_x, sum_y);
			handImage.setImage();
			handImage.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			StaticInitCircle.origen = new Point(evt.getX(),evt.getY());

		}
		else{
			if(circle.isSelected(evt.getX(), evt.getY())){
				StaticInitCircle.origen = new Point(evt.getX(),evt.getY());
			}
		}
	}
	
	public static void moveOnePoint(MouseEvent evt,HandImage handImage){
		
		InitCircleOnScreen init = handImage.getInitCircle();
		Point p = init.getPoint(indexMoveOP);
		p.x = destinoOP.x;
		p.y = destinoOP.y;
		handImage.setImage();
	}
	
	
	public static void moveNormalOnePoint(MouseEvent evt, HandImage handImage) {

		InitCircleOnScreen init = handImage.getInitCircle();
		Point p = init.getPoint(indexMoveOP);
		// Distancia a sumar
		int add = (int) getDistance(destinoOP, origenOP);
		init.calculateCenter();

		// Verifico se mover hacia dentro del circulo o fuera, comparando las
		// distacias entre el centro y origen y el centro y destino
		int auxO = (int) getDistance(origenOP, init.getCentro());
		int auxD = (int) getDistance(destinoOP, init.getCentro());
		if (auxO < auxD)
			add = add * -1;
		// Sumo esta distancia al punto en cuestion
		Point p1 = init.getLeftIndex(indexMoveOP);
		Point p3 = init.getRightIndex(indexMoveOP);
		Normal normal = getVectorNormal(p1, origenOP, p3);
		Point set = normal.getNormalAdd(origenOP, add);// Calculo el nuevo
		p.x = set.x;
		p.y = set.y;

		// Muevo los de la izquierda y derecha
		Point aux;
		Point auxPO;
		Point pR;
		Point pL;
		int initialAdd = add;
		for (int i = 0; i < lefts.size() - 1; i++) {
			// izquierda
			add = (int) (add*neighborhoodFact);
			aux = lefts.get(i);
			auxPO = init.getPoint(leftsI.get(i));
			pL = lefts.get(i + 1);
			if (i == 0) {
				pR = origenOP;
			} else {
				pR = lefts.get(i - 1);
			}
			normal = getVectorNormal(pL, aux, pR);
			set = normal.getNormalAdd(aux, add);// Calculo el nuevo
			auxPO.x = set.x;
			auxPO.y = set.y;
			
		}
		add = initialAdd;
		for (int i = 0; i < rights.size() - 1; i++) {
			// derecha
			add = (int) (add*neighborhoodFact);
			aux = rights.get(i);
			auxPO = init.getPoint(rightsI.get(i));
			pR = rights.get(i + 1);
			if (i == 0) {
				pL = origenOP;
			} else
				pL = rights.get(i - 1);
			normal = getVectorNormal(pL, aux, pR);
			set = normal.getNormalAdd(aux, add);// Calculo el nuevo
			auxPO.x = set.x;
			auxPO.y = set.y;
			
		}

		handImage.setImage();
	}
	
	public static Point getNormal(Point p1,Point p2){
		int vx = p2.x - p1.x;
		int vy = p2.y - p1.y;
		
		return new Point(-vy,vx);
	}
	public static Normal getVectorNormal(Point p1,Point p2,Point p3){
		Point aux1= getNormal(p1, p2);
		Point aux2= getNormal(p2, p3);
		double x=(aux1.x+aux2.x)/2.0;
		double y=(aux1.y+aux2.y)/2.0;
		
		double modulo = Math.sqrt((x*x + y*y));
		if(modulo==0)
			modulo=1;
		double xx =x/modulo;
		double yy=y/modulo;
		return new Normal(xx,yy);
		
	}
	private static double getDistance(Point point1, Point point2){
		int x = point2.x - point1.x;
		int y = point2.y - point1.y;
		return Math.sqrt(x*x + y*y);
	}
	private static Vector<Point> lefts;
	private static Vector<Point> rights;
	private static Vector<Integer> leftsI;
	private static Vector<Integer> rightsI;
	public static void setOrigenOP(Point point,HandImage handImage) {
		origenOP = point;
		InitCircleOnScreen init = handImage.getInitCircle();
	
		Point p = init.getLeftIndex(indexMoveOP);
		leftOOP = new Point(p.x,p.y);
		p = init.getRightIndex(indexMoveOP);
		rightOOP= new Point(p.x,p.y);
		
		//Guardo los puntos originales
		leftsI = init.getLeftIndex(indexMoveOP,neighborhoodInvolved);
		rightsI = init.getRightIndex(indexMoveOP,neighborhoodInvolved);
		
		lefts = new Vector<Point>();
		rights = new Vector<Point>();
		Point p1;
		int size = rightsI.size();
		for(int i =0;i<size;i++){
			p = init.getPoint(leftsI.get(i));
			p1 = new Point(p.x,p.y);
			lefts.add(p1);
			p =init.getPoint(rightsI.get(i));
			p1 = new Point(p.x,p.y);
			rights.add(p1);
		}
		
		
		
	}
}
