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

import org.jouvieje.util.math.Vector3f;

public interface Viewable
{
	public Vector3f getPosition();
	public void setPosition(Vector3f position);
	
	public Vector3f getSpeed();
	public void setSpeed(Vector3f speed);
	
	public Vector3f getRotation();
	public void setRotation(Vector3f rotation);
	
	public Vector3f getForward();
	public void setForward(Vector3f forward);
	public Vector3f getUp();
	public void setUp(Vector3f up);
	public Vector3f getLeft();
	public void setLeft(Vector3f left);
	
	public float getViewerHeight();
	public void setViewerHeight(float viewerHeight);
}