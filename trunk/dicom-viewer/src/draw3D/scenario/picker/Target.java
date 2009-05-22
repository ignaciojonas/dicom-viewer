
package draw3D.scenario.picker;

import java.util.Random;

import net.java.games.jogl.GL;
import net.java.games.jogl.GLDrawable;
import net.java.games.jogl.GLU;
import net.java.games.jogl.GLUquadric;
import org.jouvieje.util.math.Vector3f;
import org.jouvieje.util.math.Vector4f;


public abstract class Target
{
	protected static Random random = new Random();
	
	//Position
	protected Vector3f position;
	
	protected float[] color;
	protected boolean on=true;

	
	public Target(Vector3f position)
	{
		this.position = position;
	
		
	}

	
	public abstract void draw(int i, GL gl, GLDrawable glDrawable, GLUquadric quadric);
	public float[] getColor()
	{
		return color;
	}

	public void setColor(float[] color)
	{
		this.color = color;
	}

	

	public Vector3f getPosition()
	{
		return position;
	}

	
	public abstract Target kill(Vector3f pos);

	
}
