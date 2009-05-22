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

import java.awt.event.*;

import org.jouvieje.util.math.*;


import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static org.jouvieje.util.math.VectorMath.*;

/**
 * Implementation using Euler angles
 */
public abstract class IViewerCameraEuler extends AbstractViewerCamera implements KeyListener, MouseListener, MouseMotionListener
{
	public IViewerCameraEuler(){ super(); }
	public IViewerCameraEuler(Viewable viewer){ super(viewer); }
	
	/*
	 * 			MECHANICAL APPLICATION FOR MOVEMENT
	 * 
	 * The formula looks very difficult, here is how to find them.
	 * 
	 * If you do some mathematical/mechanical studies, you have probably heard about rotation matrices.
	 * Here, the orientation in the 3D will be tracked using rotation matrices, composed of two rotations :
	 *  - rotation around the vertical/up axis : we keep track of the y angle
	 *  - rotation around the sideward/left axis for the head angle : we keep track of the x angle
	 * 
	 * Here we have two axis system :
	 * 	- the fixed axis system  (also called world axis)
	 * 	- the camera axis system
	 * 
	 * The basis of camera is to move depending on the camera view.
	 * We want, for example, to move along the vector (dx, dy, dz) expressed in the camera system.
	 * What is the displacement in the fixed axis system ?
	 * 
	 * For the first rotation (y), we have this rotation matrix (called "Matrice de passage" in French):
	 * M[0->1] = | cos(y)  0  -sin(y)  |
	 *           | 0       1   0       |
	 *           | sin(y)  0   cos(y)  |
	 * For the second rotation (x), we have this :
	 * M[1->2] = | 1   0       0      |
	 *           | 0   cos(x)  sin(x) |
	 *           | 0  -sin(x)  cos(x) |
	 * By multiplying these two matrices, we have :
	 * M[0->2] = M[0->1]M[1->2] = | cos(y)   sin(x)*sin(y)  -cos(x)*sin(y) |
	 *                            | 0        cos(x)          sin(x)        |
	 *                            | sin(y)  -sin(x)*cos(y)   cos(x)*cos(y) |
	 * 
	 * With this, we rotates first in the y axis then we rotate around the x transformed axis.
	 * Rotation is simple, as we just increase the angle values.
	 * 
	 * To translate, we have to convert the displacement expressed in the camera system
	 * into the world system. We use the matrix M[0->2].
	 * M[0->2] = |M11 M12 M13|
	 *           |M21 M22 M23|
	 *           |M31 M32 M33|
	 * A displacement (dx, dy, dz) in the camera system correspond to a displacement (dx', dy', dz')
	 * in the world system :
	 * |dx'| = |M11 M12 M13| |dx| = |dx*M11+dy*M12+dz*M13|
	 * |dy'|   |M21 M22 M23| |dy|   |dx*M21+dy*M22+dz*M23|
	 * |dz'|   |M31 M32 M33| |dz|   |dx*M31+dy*M32+dz*M33|
	 * 
	 * NOTE: Don't forgot to convert degree to radian when using math functions.
	 * 
	 * --------------------
	 * *Bonus* EULER ANGLES
	 * --------------------
	 * The mechanical definition of Euler Angles is as follow.
	 * Euler Angles defines a rotation composed of the 3 rotations :
	 *  - z rotation (z1)
	 *  - x rotation (x2)
	 *  - z rotation (z3)
	 * 
	 * Here are rotation matrices :
	 *      First rotation                Second rotation           Third rotation
	 * 	|  cos(z1)   sin(z1)  0 |    | 1   0        0       |   |  cos(z3)  sin(z3)  0 |
	 * 	| -sin(z1)   cos(z1)  0 |    | 0   cos(x2)  sin(x2) |   | -sin(z3)  cos(z3)  0 |
	 * 	|  0         0        1 |    | 0  -sin(x2)  cos(x2) |   |  0        0        1 |
	 * 
	 * After the first multiplication, we have :
	 *            First * Second rotation                    Third rotation
	 * 	|  cos(z1)   sin(z1)cos(x2)  sin(z1)sin(x2) |   |  cos(z3)  sin(z3)  0 |
	 * 	| -sin(z1)   cos(z1)cos(x2)  cos(z1)sin(x2) |   | -sin(z3)  cos(z3)  0 |
	 * 	|  0        -sin(x2)         cos(x2)        |   |  0        0        1 |
	 * 
	 * Finally, we have this BIG matrix :
	 * 	|  cos(z1)cos(z3)-sin(z1)cos(x2)sin(z3)   cos(z1)sin(z3)+sin(z1)cos(x2)cos(z3)  sin(z1)sin(x2) |
	 * 	| -sin(z1)cos(z3)-cos(z1)cos(x2)sin(z3)  -sin(z1)sin(z3)+cos(z1)cos(x2)cos(z3)  cos(z1)sin(x2) |
	 * 	|  sin(x2)sin(z3)                        -sin(x2)cos(z3)                        cos(x2)        |
	 */
	
	/**
	 * Convert a vector (dx, dy, dz) expressed in eye axis system to a vector expressed
	 * in the fixed axis system depended on the mode.
	 * 
	 * mode == FREE_DEPLACEMENT_MODE   : exact convertion
	 * mode == NORMAL_DEPLACEMENT_MODE : don't take in account x rotation
	 */
	protected Vector3f vectorInFixedSystem(float dx, float dy, float dz, CameraMode mode)
	{
		//Don't calculate for nothing ...
		if(dx == 0.0f & dy == 0.0f && dz == 0.0f)
			return new Vector3f();
		
		float x,y,z;
		
		double yRot = toRadians(getRotation().getY());
		if(mode == CameraMode.FREE)
		{
			double xRot = toRadians(getRotation().getX());
			
			//Formulas: See big note above
			x = (float)( dx*cos(yRot) + dy*sin(xRot)*sin(yRot) - dz*cos(xRot)*sin(yRot) );
			y = (float)(              + dy*cos(xRot)           + dz*sin(xRot)           );
			z = (float)( dx*sin(yRot) - dy*sin(xRot)*cos(yRot) + dz*cos(xRot)*cos(yRot) );
		}
		else
		{
			//Here x rotation is ignored, so use the simplified formula to same time (less cos and sin)
			x = (float)( dx*cos(yRot)     - dz*sin(yRot) );
			y =                        dy                 ;
			z = (float)( dx*sin(yRot)     + dz*cos(yRot) );
		}
		return new Vector3f(x, y, z);
	}
	
	protected void rotateImpl(Vector3f rotation)
	{
		setRotation(add(getRotation(), rotation));
	}
	
	protected void checkRotation()
	{
		if(moveMode != CameraMode.FREE)
		{
			if(getRotation().getX() > MAX_ANGLE_X)
				getRotation().setX(MAX_ANGLE_X);
			else if(getRotation().getX() < MIN_ANGLE_X)
				getRotation().setX(MIN_ANGLE_X);
		}
	}
	
					/* Getter & Setter */
	
	public Vector3f getRotation(){ return getViewable().getRotation(); }
	public void setRotation(Vector3f vector)
	{
		getViewable().setRotation(vector);
		checkRotation();
	}
	
	public Vector3f getForward(){ return vectorInFixedSystem(0.0f, 0.0f, 1.0f, CameraMode.FREE); }
	public void setForward(Vector3f forward){ throw new UnsupportedOperationException(); }
	
	public Vector3f getUp(){ return vectorInFixedSystem(0.0f, 1.0f, 0.0f, CameraMode.FREE); }
	public void setUp(Vector3f up){ throw new UnsupportedOperationException(); }
	
	public Vector3f getLeft(){ return vectorInFixedSystem(1.0f, 0.0f, 0.0f, CameraMode.FREE); }
	public void setLeft(Vector3f left){ throw new UnsupportedOperationException(); }
}