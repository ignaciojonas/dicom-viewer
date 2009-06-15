package draw3D.scenario.screen;

import draw3D.Setup3D;
import net.java.games.jogl.GL;

public class Triangle3D {

	private Point3D v0;
	private Point3D v1;
	private Point3D v2;
	
	private Point3D normal;
	public Triangle3D(Point3D v0, Point3D v1, Point3D v2) {
		super();
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
		normal = getNormal2();
	}
	
	public Point3D getV0() {
		return v0;
	}
	public Point3D getV1() {
		return v1;
	}
	public Point3D getV2() {
		return v2;
	}
	
	public void draw(GL gl){
		gl.glVertex3f( v0.getX(),v0.getY(),v0.getZ());
		gl.glVertex3f( v1.getX(),v1.getY(),v1.getZ());
		gl.glVertex3f( v2.getX(),v2.getY(),v2.getZ());
		//if (Setup3D.lightPosition0[3]==0)
			gl.glNormal3f( normal.getX(),normal.getY(),normal.getZ());
		
	}
	public String getSUR(int num1, int num2, int num3){
		String ret="";
		ret+=" "+num1+" "+v0.getX()+" "+v0.getY()+" "+v0.getZ()+"\n";
	
		ret+=" "+num2+" "+v1.getX()+" "+v1.getY()+" "+v1.getZ()+"\n";
	
		ret+=" "+num3+" "+v2.getX()+" "+v2.getY()+" "+v2.getZ()+"\n";
		return ret;
		
	}
	
	private Point3D getNormal2() {
		float qx,qy,qz,px,py,pz,nx,ny,nz;
		float mult = 25;
		qx = v1.getX() - v0.getX();
		qy = v1.getY() - v0.getY();
		qz = v1.getZ() - v0.getZ();
		
		px = v2.getX() - v0.getX();
		py = v2.getY() - v0.getY();
		pz = v2.getZ() - v0.getZ();
		//matriz para calcular la normal
		nx = py*qz - pz*qy;
		ny = pz*qx - px*qz;
		nz = px*qy - py*qx;
		return new Point3D(-nx*mult,-ny*mult,-nz*mult); 
	}
	
	

	private Point3D getNormal() {
		float x1,x2,x3,y1,y2,y3,z1,z2,z3;
		x1 = v0.getX();
		y1 = v0.getY();
		z1 = v0.getZ();
		x2 = v1.getX();
		y2 = v1.getY();
		z2 = v1.getZ();
		x3 = v2.getX();
		y3 = v2.getY();
		z3 = v2.getZ();
		float v1x,v1y,v1z,v2x,v2y,v2z,nx,ny,nz;
		//llevo los vectores al (0,0,0)
		v1x = x1-x2;
		v1y = y1-y2;
		v1z = z1-z2;
		v2x = x1-x3;
		v2y = y1-y3;
		v2z = z1-z3;
		//matriz para calcular la normal
		nx = v1y*v2z - v1z*v2y;
		ny = v2x*v1z - v2z*v1x;
		nz = v1x*v2y - v1y*v2x;
		return new Point3D(nx*50,ny*50,nz*50); 
	}
}
