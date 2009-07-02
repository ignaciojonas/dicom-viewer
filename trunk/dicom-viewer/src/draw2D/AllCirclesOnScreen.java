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
	 public void setCircleAllPointsEnlarge(
				Vector<CircleOnScreen> circleAllPointsEnlarge) {
			this.circleAllPointsEnlarge = circleAllPointsEnlarge;
		}
	 public Vector<CircleOnScreen> getCircleAllPointsEnlarge() {
			return circleAllPointsEnlarge;
		}
	 public void draw(Graphics g, int index){
		if ((VisualData.viewCircleAll)&&(size()>0)){
			try{
				circleAllPointsEnlarge.get(index).draw(g);
				circleAllNormales.get(index).draw(g);
				circleAllPointsEnlarge.get(index).draw(g);
				circleAllPointsReduce.get(index).draw(g);
				circleAllPointsInit.get(index).draw(g);
				
			}catch(Exception e){}
		}
	 }
	 public CircleOnScreen getRecudeCircle(int i){
		 return circleAllPointsReduce.get(i);
	 }

	 public void add(CircleOnScreen enl, CircleOnScreen redu,InitCircleOnScreen initCircle, NormalOnScreen norm, int index) {
		 try{
		 circleAllPointsEnlarge.remove(index);
		 circleAllPointsReduce.remove(index);
		 circleAllPointsInit.remove(index);
		 circleAllNormales.remove(index);
		 
		 
		 }catch(Exception e){}
		 try{
		 circleAllPointsEnlarge.add(index,enl);
		 circleAllPointsReduce.add(index,redu);
		 circleAllPointsInit.add(index,initCircle);
		 circleAllNormales.add(index,norm);
		 }catch(Exception e){}
	}
	 
	 public void add(CircleOnScreen enl, CircleOnScreen redu,InitCircleOnScreen initCircle, NormalOnScreen norm) {
		
		 
		 circleAllPointsEnlarge.add(enl);
		 circleAllPointsReduce.add(redu);
		 circleAllPointsInit.add(initCircle);
		 circleAllNormales.add(norm);
			
	}
	 
	
	public int size(){
		 return circleAllPointsEnlarge.size();
	 }
	 public CircleOnScreen getEnlargeCircle(int i){
		 return circleAllPointsEnlarge.get(i);
	 }
	
	
}
