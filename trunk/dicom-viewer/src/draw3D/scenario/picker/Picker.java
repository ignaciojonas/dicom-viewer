package draw3D.scenario.picker;

import java.awt.Rectangle;
import java.nio.IntBuffer;
import java.util.Vector;
import net.java.games.jogl.GL;
import net.java.games.jogl.GLDrawable;
import net.java.games.jogl.GLU;
import net.java.games.jogl.util.BufferUtils;
import org.jouvieje.jogl.scene.Pickable;
import org.jouvieje.opengl.scene.PickingEvent;
import org.jouvieje.util.math.Vector3f;

import draw3D.OpenGLCanvas;
import draw3D.Setup3D;


public class Picker extends DefaultPicking implements Pickable{
	OpenGLCanvas z;
	public int maxTargetsCyl=9;
	public Picker(OpenGLCanvas z) {
		this.z=z;
	}
	public boolean pickEvent(PickingEvent mode, Rectangle region)
	{
		
		switch(mode)
		{
			default:
				return false;
			case LeftClick:
				return true;
			
		}
	}
	public int PICKABLE_PRECISION = 3;
	private IntBuffer selectBuffer = null;
	public void processPicking(GLDrawable glDrawable, PickingEvent mode, Rectangle region)
	{
		if(!pickEvent(mode, region)) return;
		
		GL gl = glDrawable.getGL();
		GLU glu = glDrawable.getGLU();
		
		//Calculate select buffer capacity and allocate data if necessary
		int capacity = 4*getObjectCount()*getNameStackDepth();		//Each object take in maximium : 4 * name stack depth
		if(selectBuffer == null || selectBuffer.capacity() < capacity)
			selectBuffer = BufferUtils.newIntBuffer(capacity);
		
		//Send select buffer to OpenGl and use select mode to track object hits
		gl.glSelectBuffer(selectBuffer.capacity(), selectBuffer);
		gl.glRenderMode(GL.GL_SELECT);
		
		//Retrieve viewport (x, y, width, height) & projection matrix
		int[] viewport = new int[4];
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport);
		float[] projection = new float[16];
		gl.glGetFloatv(GL.GL_PROJECTION_MATRIX, projection);
		
		//Select the projection matrix
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPushMatrix();
			gl.glLoadIdentity();
			
			//Restrict drawing in our picking region.
			int x = (int)region.getCenterX();
			int y = (int)region.getCenterY();
			int width = region.width   == 0 ? PICKABLE_PRECISION : region.width;
			int height = region.height == 0 ? PICKABLE_PRECISION : region.height;
			glu.gluPickMatrix(x, y, width, height, viewport);
			
			//Defines the viewing volume
			gl.glMultMatrixf(projection);
			
			//Select tje modelview matrix for the drawing
			gl.glMatrixMode(GL.GL_MODELVIEW);
			
			//Draw the scene
			displayObjects(glDrawable);

			//Return to render mode, glRenderMode returns the number of hits (only because GL_SELECT was selected before)
			int nbRecords = gl.glRenderMode(GL.GL_RENDER);
			
			gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glMatrixMode(GL.GL_MODELVIEW);
		
		if(nbRecords <= 0){
			return;	//Don't pick any objects
		}
		
		//Multiple selection mode ?
		boolean multiple = (mode == PickingEvent.Area) || (mode == PickingEvent.Drag);
		
	
		Vector<int[]> selection = multiple ? new Vector<int[]>() : null;
		int[] object = null;
		
		int index = 0, depth = 0;
		for(int i = 0; i < nbRecords; i++)
		{
			if(index == 0 || multiple || selectBuffer.get(index+2) < depth)
			{
				int stack = selectBuffer.get(index++);	//Depth of the name stack
				depth = selectBuffer.get(index++);		//Min depth
				index++;								//Skip max depth
				
				object = new int[stack];
				for(int j = 0; j < stack; j++){
					object[j] = selectBuffer.get(index++);
				}
				
				if(multiple) selection.add(object);
			}
			else
			{
				int stack = selectBuffer.get(index++);
				index += 2 + stack;
			}
		}
		
		if(object == null) {
			return;
		}//Don't found any object picked !!!
		
		//Single selection mode
		if(!multiple)
		{
			selection = new Vector<int[]>();
			selection.add(object);
		}
		
		
		//Process picked objects
		if(selection.size() > 0) processPicked(mode, region, selection);
	}
	public void displayObjects(GLDrawable glDrawable)
	{
		GL gl = glDrawable.getGL();

		/*
		 * Here RenderMode is GL_SELECT.
		 */

		gl.glLoadIdentity();                                         //Reset The View
		z.camera.lookAt(glDrawable);

		//Initialize name stack
		gl.glInitNames();
		gl.glPushName(0);

		/*
		 * Here we draw only pickable objects. Don't draw the others.
		 * Rem: the drawing is done out of the screen ie this will not be drawn in the screen.
		 */
		
		z.drawObjects(glDrawable);
		
	}
	public void initTargets()
	{
	
		float[] lightPosition= Setup3D.lightPosition0;
		float[] lightPosition1= Setup3D.lightPosition1;
		float[] lightPosition2= Setup3D.lightPosition2;
		Setup3D.targets.add(createTarget(-1.0f,-1.0f,-1.0f));//este lo cargo pero no lo dibujo, sino no anda el picker
		
		Setup3D.targets.add(createTarget(lightPosition[0],lightPosition[1],lightPosition[2]));
		
		Setup3D.targets.add(createTarget(lightPosition1[0],lightPosition1[1],lightPosition1[2]));
		
		Setup3D.targets.add(createTarget(lightPosition2[0],lightPosition2[1],lightPosition2[2]));
		
	}


	private Target createTarget(float x,float y,float z)
	{
		return new TargetLight(new Vector3f(x,y,z));
	}
	
	private Target killTarget(int i)
	{
		
		z.targetKill=i;
		Target t= Setup3D.targets.get(i);
		Setup3D.enableLight(i-1,!t.on);
		return t.kill(z.camera.getCameraPosition());
	}

	public void processPicked(PickingEvent mode, Rectangle region, Vector<int[]> selection)
	{
	
		
		for(int[] select : selection)
		{
			
			switch(select[0])
			{
				case 20:
						if(select.length > 0){
							
							Setup3D.targets.set(select[1]+1, killTarget(select[1]+1));
							
						}
					break;
				
			}
		}
	}

	public int getObjectCount()
	{
		return Setup3D.targets.size()+2;
	}

	public int getNameStackDepth()
	{
		return 2;
	}
	
}
