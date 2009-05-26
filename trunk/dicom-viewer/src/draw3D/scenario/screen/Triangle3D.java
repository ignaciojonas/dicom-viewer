package draw3D.scenario.screen;

import net.java.games.jogl.GL;

public class Triangle3D {

	private Point3D v0;
	private Point3D v1;
	private Point3D v2;
	public Triangle3D(Point3D v0, Point3D v1, Point3D v2) {
		super();
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
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
	}
	public String getSUR(int num1, int num2, int num3){
		String ret="";
		ret+=" "+num1+" "+v0.getX()+" "+v0.getY()+" "+v0.getZ()+"\n";
	
		ret+=" "+num2+" "+v1.getX()+" "+v1.getY()+" "+v1.getZ()+"\n";
	
		ret+=" "+num3+" "+v2.getX()+" "+v2.getY()+" "+v2.getZ()+"\n";
		return ret;
		
	}
}
