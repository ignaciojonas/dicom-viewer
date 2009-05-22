/*
 * Created on 6 août 07
 */
package draw3D.camera;

import net.java.games.jogl.GLDrawable;

public interface Camera
{
	public void update(long timePassedMs);
	public void lookAt(GLDrawable glDrawable);
}
