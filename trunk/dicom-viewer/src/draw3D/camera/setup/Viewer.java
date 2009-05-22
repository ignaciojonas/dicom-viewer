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

public class Viewer implements Viewable
{
	//Position in the fixed axis system
	private Vector3f position = new Vector3f();
	/*
	 * Orientation
	 * y angle : turn around upward vector (ie look toward your sides).
	 * x angle : turn around sidewward vector (ie look toward the ground or the sky).
	 * z angle : ignored
	 */
	private Vector3f rotation = new Vector3f();
	
	private Vector3f forward = new Vector3f(0.0f, 0.0f, 1.0f);
	private Vector3f up = new Vector3f(0.0f, 1.0f, 0.0f);
	private Vector3f left = new Vector3f(1.0f, 0.0f, 0.0f);
	
	private Vector3f speed = new Vector3f();
	
	private float viewerHeight = 0.0f;
	
	public Viewer(){}
	
	public Vector3f getRotation()
	{
		return rotation;
	}
	public void setRotation(float x, float y, float z)
	{
		rotation.setX(x);
		rotation.setY(y);
		rotation.setZ(z);
	}
	public void setRotation(Vector3f orientation)
	{
		this.rotation = orientation;
	}
	
	public Vector3f getPosition()
	{
		return position;
	}
	public void setPosition(float x, float y, float z)
	{
		position.setX(x);
		position.setY(y);
		position.setZ(z);
	}
	public void setPosition(Vector3f position)
	{
		this.position = position;
	}
	
	public Vector3f getSpeed()
	{
		return speed;
	}
	public void setSpeed(Vector3f speed)
	{
		this.speed = speed;
	}
	
	public Vector3f getForward()
	{
		return forward;
	}
	public void setForward(Vector3f forward)
	{
		this.forward = forward;
	}
	public Vector3f getUp()
	{
		return up;
	}
	public void setUp(Vector3f up)
	{
		this.up = up;
	}
	public Vector3f getLeft()
	{
		return left;
	}
	public void setLeft(Vector3f left)
	{
		this.left = left;
	}
	
	public float getViewerHeight()
	{
		return viewerHeight;
	}
	public void setViewerHeight(float viewerHeight)
	{
		this.viewerHeight = viewerHeight;
	}
}