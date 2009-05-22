package draw3D.scenario.picker;

import draw3D.Setup3D;

import org.jouvieje.jogl.tools.SimpleShape;
import org.jouvieje.util.math.Vector3f;
import sound.PlayWav;
import net.java.games.jogl.GL;
import net.java.games.jogl.GLDrawable;
import net.java.games.jogl.GLU;
import net.java.games.jogl.GLUquadric;

public class TargetLight extends Target{

	public TargetLight(Vector3f position) {
		super(position);
		float[] color= {1.0f, 1.0f,0.0f};
		this.setColor(color);
				
	}

	@Override
	public Target kill(Vector3f pos) {
		
		if(on){
			new PlayWav(Setup3D.light,0);
			on=false;
		}
		else{
			on=true;
			new PlayWav(Setup3D.light2,0);
			
		}	
		return this;
	}

	@Override
	public void draw(int i, GL gl, GLDrawable glDrawable, GLUquadric quadric) {
		
		gl.glPushMatrix();
		if(on)
			gl.glColor3f(color[0], color[1], color[2]);
		else
			gl.glColor3f(0.5f, 0.5f,0.5f);
		
		gl.glTranslatef(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
		SimpleShape.drawGLUSphere(glDrawable, 0.1f, 15, false, GLU.GLU_FILL);
		gl.glLoadName(i);
		
	gl.glPopMatrix();
		
	}

}
