

package draw3D.scenario.picker;

import java.awt.Rectangle;

import org.jouvieje.opengl.scene.PickingEvent;

import net.java.games.jogl.GLDrawable;

public interface Pickable
{
	
	public void processPicking(GLDrawable glDrawable, PickingEvent mode, Rectangle region);
}