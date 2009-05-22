/***********************
 *       Camera        *
 *********************************************
 * Created on 17 mars 2004                   *
 * @author Jérôme Jouvie (Jouvieje)          *
 *********************************************/

/**
 * 				OpenGl BaseCode
 * 
 * Copyright © 2004-2007 Jérôme JOUVIE (Jouvieje)
 * 
 * 
 * WANT TO CONTACT ME ?
 * E-mail :
 * 		jerome.jouvie@gmail.com
 * My web sites :
 * 		http://jerome.jouvie.free.fr/
 * 		http://topresult.tomato.co.uk/~jerome/
 * 
 * 
 * THIRD PARTY
 * BaseCode contains Third parties works.
 * 
 * TgaTextureLoader class from the package org.jouvieje.opengl.texture was created by Ron Sullivan :
 *     TgaTextureLoader    (I've modify the original source to use this class with Jogl and Gl4java)
 * 
 * 
 * GNU GENERAL PUBLIC LICENSE (GPL)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package draw3D.camera.setup;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static org.jouvieje.util.math.VectorMath.*;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.jouvieje.util.math.Vector3f;

/**
 * Implementation using forward/left/up vector
 */
public abstract class IViewerCameraVector extends AbstractViewerCamera implements KeyListener, MouseListener, MouseMotionListener
{
	public IViewerCameraVector(){ super(); }
	public IViewerCameraVector(Viewable viewer){ super(viewer); }
	
	/**
	 * Convert a vector (dx, dy, dz) expressed in eye axis system to a vector expressed
	 * in the fixed axis system depended on the mode.
	 * 
	 * mode == FREE_DEPLACEMENT_MODE   : exact conversion
	 * mode == NORMAL_DEPLACEMENT_MODE : don't take in account x rotation
	 */
	protected Vector3f vectorInFixedSystem(float dx, float dy, float dz, CameraMode mode)
	{
		//Don't calculate for nothing ...
		if(dx == 0.0f & dy == 0.0f && dz == 0.0f)
			return new Vector3f();
		
		float x,y,z;
		if(mode == CameraMode.FREE)
		{
			x = dx*getLeft().getX() + dy*getUp().getX() + dz*getForward().getX();
			y = dx*getLeft().getY() + dy*getUp().getY() + dz*getForward().getY();
			z = dx*getLeft().getZ() + dy*getUp().getZ() + dz*getForward().getZ();
		}
		else
		{
			//Vector are not normalized here !
//			x = dx*getLeft().getX() +                    dz*getForward().getX();
//			y =                       dy*getUp().getY()                        ;
//			z = dx*getLeft().getZ() +                    dz*getForward().getZ();
			
			Vector3f left = getLeft().clone(); left.setY(0); left.normalize();
			Vector3f forw = getForward().clone(); forw.setY(0); forw.normalize();
			
			x = dx*left.getX() +      dz*forw.getX();
			y =                   dy                ;
			z = dx*left.getZ() +      dz*forw.getZ();
		}
		return new Vector3f(x, y, z);
	}
	
	protected void rotateImpl(Vector3f rotation)
	{
		float angleX = rotation.getX();
		float angleY = rotation.getY();
//		float angleZ = rotation.getZ();		//FIXME angleZ not used
		
		if(angleY != 0.0f)
		{
			/*
			 * Rotation around up vector of the fixed coordinate system
			 */
			
			//Projection in the x/z plane of the fixed coordinate system
			Vector3f forwardProjected = getForward().clone();
			forwardProjected.setY(0.0f);
			double magnitude = magnitude(forwardProjected);
			forwardProjected = normalize(forwardProjected);
			//TODO (0, 1, 0) ?
			Vector3f leftProjected = normalize(crossProduct(new Vector3f(0, 1, 0), forwardProjected));
			
			double radians = toRadians(angleY);
			
			//Z' = -sin?.X+cos?.Z
			Vector3f v1 = multiply(leftProjected,    -(float)sin(radians));
			Vector3f v2 = multiply(forwardProjected,  (float)cos(radians));
			Vector3f v = add(v1, v2);
			v = magnitude(v, magnitude);
			v.setY(getForward().getY());
			setForward(normalize(v));
			
			//X' = cos?.X+sin?.Z
			v1 = multiply(leftProjected,    (float)cos(radians));
			v2 = multiply(forwardProjected, (float)sin(radians));
			setLeft(normalize(add(v1, v2)));
			
			//Y' = Z ^ X ( = Y )
			setUp(normalize(crossProduct(getForward(), getLeft())));
		}
		if(angleX  != 0.0f)
		{
		    //X' = X
		    
			//Z' = sin?.Y+cos?.Z
			double radians = toRadians(angleX);
			Vector3f v1 = multiply(getUp(),      (float)sin(radians));	//sin?.Y
			Vector3f v2 = multiply(getForward(), (float)cos(radians));	//cos?.Z
			setForward(normalize(add(v1, v2)));

			//Y' = Z' ^ X'
			setUp(normalize(crossProduct(getForward(), getLeft())));
		}
		
		checkRotation();
	}
	
	protected void checkRotation()
	{
		if(moveMode != CameraMode.FREE)
		{
//			//Projection in the x/z plane of the fixed coordinate system
//			Vector3f forwardRef = normalize(crossProduct(getLeft(), new Vector3f(0, 1, 0)));
//			//Angle between those vectors (absolute angle)
//			float angleX = (float)toDegrees(acos(dotProduct(getForward(), forwardRef)));
//			Debug.outPrintln("angleX(°)="+angleX);
			
//			if(getRotation().getX() > MAX_ANGLE_X)
//				getRotation().setX(MAX_ANGLE_X);
//			else if(getRotation().getX() < MIN_ANGLE_X)
//				getRotation().setX(MIN_ANGLE_X);
		}
	}
	
							/* Getter & Setter */
	
	public Vector3f getRotation(){ throw new UnsupportedOperationException(); }	//FIXME Calculate rotation
	public void setRotation(Vector3f vector){ throw new UnsupportedOperationException(); }
	
	public Vector3f getForward(){ return getViewable().getForward(); }
	public void setForward(Vector3f forward){ getViewable().setForward(forward); }
	
	public Vector3f getUp(){ return getViewable().getUp(); }
	public void setUp(Vector3f up){ getViewable().setUp(up); }
	
	public Vector3f getLeft(){ return getViewable().getLeft(); }
	public void setLeft(Vector3f left){ getViewable().setLeft(left); }
	
}