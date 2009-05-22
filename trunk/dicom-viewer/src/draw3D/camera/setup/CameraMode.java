/*
 * Created on 6 août 07
 */
package draw3D.camera.setup;

public enum CameraMode
{
	/** You can move everywhere you want in the 3D space. */
	FREE,
	/**
	 * Movement is restricted in a box specified by :<BR>
	 * <pre>  <code>MIN_X</code>, <code>MIN_Y</code>, <code>MIN_Z</code></pre>
	 * <pre>  <code>MAX_X</code>, <code>MAX_Y</code>, <code>MAX_Z</code></pre>
	 * By default, these fields defines all the 3D space.<BR><BR><BR>
	 * Moreover, the x rotation is restricted with :<BR>
	 * <pre>  <code>MIN_ANGLE_X</code>, <code>MAX_ANGLE_X</code></pre>
	 * By default, those fields restrict the rotation to [-90, 90] (in degrees).
	 */
	NORMAL,
	/**
	 * Displacement on a terrain with gravity enabled.
	 * Restrictions on the orientation defined in <code>NORMAL</code> field are also enabled here.
	 */
	TERRAIN;
}
