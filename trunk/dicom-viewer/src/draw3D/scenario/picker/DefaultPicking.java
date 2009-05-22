
package draw3D.scenario.picker;

import java.awt.Rectangle;
import java.nio.IntBuffer;
import java.util.Vector;


import org.jouvieje.opengl.scene.PickingEvent;

import net.java.games.jogl.GL;
import net.java.games.jogl.GLDrawable;
import net.java.games.jogl.GLU;
import net.java.games.jogl.util.BufferUtils;

public abstract class DefaultPicking implements Pickable
{
	
	public abstract boolean pickEvent(PickingEvent mode, Rectangle region);
	
	public abstract void displayObjects(GLDrawable glDrawable);
	
	public abstract void processPicked(PickingEvent mode, Rectangle region, Vector<int[]> selection);
	
	
	public abstract int getObjectCount();
	
	public abstract int getNameStackDepth();
	
	
	public int PICKABLE_PRECISION = 3;
	private IntBuffer selectBuffer = null;
	public void processPicking(GLDrawable glDrawable, PickingEvent mode, Rectangle region)
	{
		if(!pickEvent(mode, region)) return;
		
		GL gl = glDrawable.getGL();
		GLU glu = glDrawable.getGLU();
		
		int capacity = 4*getObjectCount()*getNameStackDepth();		
		if(selectBuffer == null || selectBuffer.capacity() < capacity)
			selectBuffer = BufferUtils.newIntBuffer(capacity);
		
		gl.glSelectBuffer(selectBuffer.capacity(), selectBuffer);
		gl.glRenderMode(GL.GL_SELECT);
		
		int[] viewport = new int[4];
		gl.glGetIntegerv(GL.GL_VIEWPORT, viewport);
		float[] projection = new float[16];
		gl.glGetFloatv(GL.GL_PROJECTION_MATRIX, projection);
		
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glPushMatrix();
			gl.glLoadIdentity();
			
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
		
		if(nbRecords <= 0) return;	//Don't pick any objects
		
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
				for(int j = 0; j < stack; j++)
					object[j] = selectBuffer.get(index++);
				
				if(multiple) selection.add(object);
			}
			else
			{
				int stack = selectBuffer.get(index++);
				index += 2 + stack;
			}
		}
		
		if(object == null) return;	//Don't found any object picked !!!
		
		//Single selection mode
		if(!multiple)
		{
			selection = new Vector<int[]>();
			selection.add(object);
		}
		
		//Process picked objects
		if(selection.size() > 0) processPicked(mode, region, selection);
	}
}