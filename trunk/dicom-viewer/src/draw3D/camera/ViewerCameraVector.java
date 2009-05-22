/***********************
 *       Camera        *
 *********************************************************************************************
 *                                                                                           *
 * Created on 17 mars 2004                                                                   *
 * @author Jérôme Jouvie (Jouvieje)                                                          *
 *                                                                                           *
 * WANT TO CONTACT ME ?                                                                      *
 * E-mail :                                                                                  *
 * 		jerome.jouvie@gmail.com                                                              *
 * My web sites :                                                                            *
 * 		http://jerome.jouvie.free.fr/                                                        *
 * 		http://topresult.tomato.co.uk/~jerome/                                               *
 *                                                                                           *
 * ABOUT                                                                                     *
 * Tihs class allows you to move in 3D world.                                                *
 * You can deplace (translate) in the 3 axis. You can allow rotate in the x and y axis in    *
 * the viewer coordinates.                                                                   *
 * The direction of the view is the z axis. The sens could be towards the positive or        *
 * negative z.                                                                               *
 *                                                                                           *
 * Keys and Mouse                                                                            *
 * -> In pressing directional keys, you can move in the three directions.                    *
 * If shift is also pressed with these keys, you rotate instead of translate                 *
 * -> With the C key, you can enabled/disables the mouse control. Mouse control is used to   *
 * keep the mouse cursor in the screen center. In addition, we don't need to press the left  *
 * button to rotate the view.                                                                *
 * ->With the P key, it print our position                                                   *
 * ->In moving the mouse, you can rotate or translate your position/view. The deplacement    *
 * depends of which mouse button is pressed and of the mouse control propertie.              *
 *********************************************************************************************/

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

package draw3D.camera;


import org.jouvieje.util.math.Vector3f;

import draw3D.camera.setup.Viewable;

import net.java.games.jogl.GLDrawable;


public class ViewerCameraVector extends draw3D.camera.setup.IViewerCameraVector implements Camera
{
	public ViewerCameraVector(){ super(); }
	public ViewerCameraVector(Viewable viewer){ super(viewer); }
	
	public void lookAt(GLDrawable glDrawable)
	{
		Vector3f up = getUp();
		Vector3f forward = getForward();
		Vector3f pos = getCameraPosition();
		
		/*
		 * Set the view to the eye coordinates system
		 * The direction is the z axis, the sens is the value of: sens
		 */
		glDrawable.getGLU().gluLookAt(pos.getX(), pos.getY(), pos.getZ(),
				pos.getX()+forward.getX(), pos.getY()+forward.getY(), pos.getZ()+forward.getZ(),
				up.getX(), up.getY(), up.getZ());
	}
}