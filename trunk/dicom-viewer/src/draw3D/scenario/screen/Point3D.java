package draw3D.scenario.screen;



public class Point3D {

	private float x;
	private float y;
	private float z;
	
   

	public Point3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float getX() {
		return x;
	}
		
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return z;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setZ(float z) {
		this.z = z;
	}

	public String toSUR() {
		
		return x+" "+y+" "+z;
	}
	
	
	
}
