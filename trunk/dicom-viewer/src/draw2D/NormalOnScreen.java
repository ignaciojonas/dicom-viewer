package draw2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import draw3D.Line;

public class NormalOnScreen {

	private Vector<Line> normales = new Vector<Line>();
	private Color color = Color.WHITE;
	
	public NormalOnScreen(Vector<Line> normales) {
		this.normales = normales;
	}
	public void draw(Graphics g){
		Line l;
		for (int i=0;i<normales.size();i++){
			g.setColor(color);
			l = normales.get(i);
			Point p = l.getPoint1();
			Point p2=l.getPoint2();
			g.drawLine(p.x, p.y, p2.x, p2.y);
			
			
		}
	}
}
