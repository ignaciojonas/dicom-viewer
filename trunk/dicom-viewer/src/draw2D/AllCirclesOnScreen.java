package draw2D;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import data.VisualData;
import draw3D.Line;

public class AllCirclesOnScreen {

	 private Vector<CircleOnScreen> circleAllPointsEnlarge = new Vector<CircleOnScreen>();
	 private Vector<CircleOnScreen> circleAllPointsReduce = new Vector<CircleOnScreen>();
	 private Vector<InitCircleOnScreen> circleAllPointsInit = new Vector<InitCircleOnScreen>();
	 private Vector<NormalOnScreen> circleAllNormales = new Vector<NormalOnScreen>(); 
	 
	 public AllCirclesOnScreen() {
		// TODO Auto-generated constructor stub
	}
	 public void draw(Graphics g, int index){
		if ((VisualData.viewCircleAll)&&(size()>0)){
			try{
				circleAllNormales.get(index).draw(g);
				circleAllPointsEnlarge.get(index).draw(g);
				circleAllPointsReduce.get(index).draw(g);
				circleAllPointsInit.get(index).draw(g);
				
			}catch(Exception e){}
		}
	 }
	
	 public void add(CircleOnScreen enlarge,CircleOnScreen reduce,InitCircleOnScreen init){
		 circleAllPointsEnlarge.add(enlarge);
		 circleAllPointsReduce.add(reduce);
		 circleAllPointsInit.add(init);
		 circleAllNormales.add(getNormalOnScreen(init,enlarge));
	 }
	 public void add(CircleOnScreen enl, CircleOnScreen redu,InitCircleOnScreen initCircle, NormalOnScreen norm, int index) {
		 try{
		 circleAllPointsEnlarge.remove(index);
		 circleAllPointsReduce.remove(index);
		 circleAllPointsInit.remove(index);
		 circleAllNormales.remove(index);
		 }catch(Exception e){}
		 
		 circleAllPointsEnlarge.add(index,enl);
		 circleAllPointsReduce.add(index,redu);
		 circleAllPointsInit.add(index,initCircle);
		 circleAllNormales.add(index,norm);
			
	}
	 
	 public void add(CircleOnScreen enl, CircleOnScreen redu,InitCircleOnScreen initCircle, NormalOnScreen norm) {
		
		 
		 circleAllPointsEnlarge.add(enl);
		 circleAllPointsReduce.add(redu);
		 circleAllPointsInit.add(initCircle);
		 circleAllNormales.add(norm);
			
	}
	 
	 private NormalOnScreen getNormalOnScreen(InitCircleOnScreen init, CircleOnScreen enlarge) {
		 Vector<Line> normals = new Vector<Line>();
		 Vector<Point> in = init.getCirclePoints();
		 Vector<Point> en = enlarge.getCirclePoints();
		 for(int i=0;i<enlarge.getCantPoints();i++){
			 normals.add(new Line(in.get(i),en.get(i))); 
		 }
		 return new NormalOnScreen(normals);
	}
	public int size(){
		 return circleAllPointsEnlarge.size();
	 }
	 public CircleOnScreen getEnlargeCircle(int i){
		 return circleAllPointsEnlarge.get(i);
	 }
	
	
}
