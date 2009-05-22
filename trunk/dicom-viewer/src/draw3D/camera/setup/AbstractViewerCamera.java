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

import static org.jouvieje.util.math.VectorMath.add;
import static org.jouvieje.util.math.VectorMath.magnitude;
import static org.jouvieje.util.math.VectorMath.multiply;
import static org.jouvieje.util.math.VectorMath.normalize;
import static org.jouvieje.util.math.VectorMath.subtract;

import java.awt.Component;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.jouvieje.opengl.terrain.Terrain;
import org.jouvieje.util.log.Output;
import org.jouvieje.util.math.Vector3f;
import org.jouvieje.util.math.Vector4f;

abstract class AbstractViewerCamera
{
	public static boolean DEBUG = true;
	
	/* Viewer - Current position/orientation */
	protected Viewable viewer;
	protected Vector3f nextPos = null, nextRotation = null;		//For time based movement
	
	/* Walk*/
	public Vector3f walkSpeed;
	public Vector3f walkSlowSpeed;
	public float rotationSpeed;
	
	/* Duck */
	public Vector3f duckSpeed;
	public Vector3f duckSlowSpeed;
	protected float duckHeightFactor = 1.0f;
	public float duckHeightFactorSpeed;
	public float minDuckHeightFactor;
	
	/* Jump / Free fall */
	public final static float GRAVITY_ACCELERATION = 9.81f;		//Gravity acceleration
	public float jumpStartSpeed;
	
	/** Camera offset (3rd person view) */
	protected boolean thirdPersonView = true;
	protected float cameraOffset = 0.0f;		//Offset = 0.0f <=> Normal view
	protected float nextCameraOffset = 0.0f;	//For time based movement
	public float cameraOffsetSpeed;
	public float cameraOffsetStep;
	
	/** Camera scaling */
	public float ZOOM = 1.0f;
	public float MOVE_SCALE = 1.0f;
	
				/** Keybord state */
	/*
	 * With keyPressed event, by pressing left and up keys at the same time : one event is missed !
	 * We use this to use many keys at the same time.
	 */
	protected boolean frontKey = false;
	protected boolean backKey = false;
	protected boolean leftKey = false;
	protected boolean rightKey = false;
	protected boolean upKey = false;
	protected boolean downKey = false;
	protected boolean jumpKey = false;
	protected boolean duckKey = false;
	protected boolean slowKey = false;
	
				/** Mouse controller */
	//Mouse control
	protected Robot robot = null;
	private boolean mouseControlledFlag = false;
	protected boolean mouseControlled = false;
	private boolean resetMouse = true;
	//Mouse sensibility (for rotation)
	public float mouseSensibility;
	
				/** Displacement mode */
	protected CameraMode moveMode = CameraMode.FREE;
	/** Viewing restriction - Used with moveMode != FREE */
	//Min/Max position
	public float MIN_X = -Float.MAX_VALUE;
	public float MAX_X = Float.MAX_VALUE;
	public float MIN_Y = -Float.MAX_VALUE;
	public float MAX_Y = Float.MAX_VALUE;
	public float MIN_Z = -Float.MAX_VALUE;
	public float MAX_Z = Float.MAX_VALUE;
	//Min/Max angle
	public float MIN_ANGLE_X = -90.0f;
	public float MAX_ANGLE_X = 90.0f;
	/** Used with terrain mode */
	public Terrain terrain;
	
	public AbstractViewerCamera()
	{
		//TODO Store it in a default settings
		walkSpeed = new Vector3f(2.8f, 1.0f, 4.0f);
		walkSlowSpeed = new Vector3f(1.4f, 0.5f, 2.0f);
		duckSpeed = new Vector3f(1.3f, 1.0f, 1.8f);
		duckSlowSpeed = new Vector3f(0.5f, 0.5f, 1.0f);
		duckHeightFactorSpeed = 1.0f;
		minDuckHeightFactor = 0.6f;
		jumpStartSpeed = 3.5f;			//Seem to be a good value !
		rotationSpeed = 360.0f;
		cameraOffsetSpeed = 1.0f;
		cameraOffsetStep = 1.0f;
		mouseSensibility = 4.0f;
		
		try
		{
			robot = new Robot();
		}
		catch(Exception e)
		{
			Output.printStackTrace(e, "java.awt.Robot object can't be created");
			robot = null;
		}
	}
	public AbstractViewerCamera(Viewable viewer)
	{
		this();
		attachViewable(viewer);
	}
	
	public synchronized void attachViewable(Viewable viewer)
	{
		this.viewer = viewer;
		reset();
		checkPosition();
		checkRotation();
	}
	
	protected void reset()
	{
		nextPos = null;
		nextRotation = null;
		nextCameraOffset = 0.0f;
	}
	
	/**
	 * Call this at each draw.
	 */
	public synchronized void update(long timePassedMs)
	{
		//Calculate frame time in seconds
		float time = timePassedMs / 1000.0f;
		
		/**
		 * Third person view
		 * -----------------
		 */
		if(thirdPersonView && nextCameraOffset != 0.0f)
		{
			float maxOffset = cameraOffsetSpeed * time;
			
			float offset = nextCameraOffset;
			if(Math.abs(nextCameraOffset) > Math.abs(maxOffset))
			{
				offset = nextCameraOffset >= 0.0f ? maxOffset : -maxOffset;
				nextCameraOffset -= offset;
			}
			else
				nextCameraOffset = 0.0f;
			
			float newCamOffset = this.cameraOffset+offset < 0.0f ? 0.0f : cameraOffset+offset;
			if(newCamOffset != this.cameraOffset)
				this.cameraOffset = newCamOffset;
		}
		
		/**
		 * Gravity : Free fall & Jump
		 * --------------------------
		 */
		boolean onGround = true;
		if(moveMode == CameraMode.TERRAIN && terrain != null)
		{
			float height = terrain.getHeight(getViewable().getPosition());
			if(getViewable().getPosition().getY() < height)
				getViewable().getPosition().setY(height);
			if(getViewable().getPosition().getY() == height)
			{
				getViewable().setSpeed(new Vector3f());
				if(jumpKey)
					getViewable().getSpeed().addY(jumpStartSpeed);
			}
			
			if(getViewable().getPosition().getY() > height || getViewable().getSpeed().getY() != 0.0f)
			{
				if(getViewable().getPosition().getY() > height)
					onGround = false;
				
				/**
				 * Gravity : Free fall / Jump
				 * --------------------------
				 * The only force is the gravity so : m*gravity=m*acceleration
				 * => acceleration=gravity => speed=gravity*dt+const => displacement=gravity*dt²/2+const*dt+const2
				 * For dt=0, the displacement is 0
				 * => displacement=gravity*dt²/2+const*dt
				 * And finally
				 * => displacement=gravity*dt²/2+speedY*dt
				 */
				translateImpl(new Vector3f(getViewable().getSpeed().getX()*time,
						-GRAVITY_ACCELERATION*time*time/2+getViewable().getSpeed().getY()*time,
						getViewable().getSpeed().getZ()*time));
				getViewable().getSpeed().addY(-GRAVITY_ACCELERATION*time);
			}
		}
		
		/**
		 * Duck
		 * ----
		 */
		if(duckKey && onGround)		//Only if the viewer is on the ground
		{
			if(duckHeightFactor > minDuckHeightFactor)
			{
				float factor = duckHeightFactor - duckHeightFactorSpeed * time;
				if(factor < minDuckHeightFactor)
					factor = minDuckHeightFactor;
				duckHeightFactor = factor;
			}
		}
		else if(duckHeightFactor != 1.0f)
		{
			float factor = duckHeightFactor + duckHeightFactorSpeed * time;
			if(factor > 1.0f)
				factor = 1.0f;
			duckHeightFactor = factor;
		}
		
		/**
		 * Time based movement
		 * -------------------
		 */
		if(onGround)
		{
			//Maximum deplacement
			Vector3f max = multiply(slowKey ? (duckKey ? duckSlowSpeed : walkSlowSpeed) : (duckKey ? duckSpeed : walkSpeed), time);
			
			/**
			 * If a key is pressed, we increase movement.
			 * Only if walk on ground
			 */
			if(!lock)
			{
				if(moveMode != CameraMode.TERRAIN || (moveMode == CameraMode.TERRAIN && onGround))
				{
					if(frontKey || backKey || rightKey || leftKey || upKey || downKey)
					{
						//TODO: Problem in non free mode when view approches vertical axis
						float x = rightKey && leftKey  ? 0.0f : rightKey ? max.getX() : leftKey ? -max.getX() : 0.0f;
						float y = downKey  && upKey    ? 0.0f : upKey    ? max.getY() : downKey ? -max.getY() : 0.0f;
						float z = backKey  && frontKey ? 0.0f : frontKey ? max.getZ() : backKey ? -max.getZ() : 0.0f;
						
						translate(x, y, z);
					}
				}
			}
			
			if(nextPos != null)
			{
				//Deplacement for the current frame
				Vector3f move = new Vector3f(
						Math.abs(nextPos.getX()) > max.getX() ? Math.signum(nextPos.getX()) * max.getX() : nextPos.getX(),
						Math.abs(nextPos.getY()) > max.getY() ? Math.signum(nextPos.getY()) * max.getY() : nextPos.getY(),
						Math.abs(nextPos.getZ()) > max.getZ() ? Math.signum(nextPos.getZ()) * max.getZ() : nextPos.getZ());
				
				//Decrease nextPosition for next frames
				nextPos.subtract(move);
				
				//Now, translate
				translateImpl(move);
				getViewable().getSpeed().setX(move.getX()/time);
				getViewable().getSpeed().setZ(move.getZ()/time);
			}
		}
		
		if(nextRotation != null)
		{
			float maxAngle = rotationSpeed * time;
			
			Vector3f rotation = nextRotation;
			if(magnitude(nextRotation) > maxAngle)
			{
				rotation = normalize(nextRotation);
				rotation = multiply(rotation, maxAngle);
				nextRotation =  subtract(nextRotation, rotation);
			}
			else
			{
				nextRotation = null;
			}
			
			rotateImpl(rotation);
		}
	}
	
								/*Translation*/
	
	public void setPosition(float x, float y, float z){ setPosition(new Vector3f(x, y, z));}
	public void setPosition(Vector3f vector)
	{
		if(!vector.equals(getViewable().getPosition()))
		{
			getViewable().setPosition(vector);
			checkPosition();
		}
	}
	
	public void translate(Vector3f vector){ translate(vector.getX(), vector.getY(), vector.getZ()); }
	/**
	 * Translate the camera.
	 * dx, dy and dz are expressed in the camera axis system.
	 */
	public synchronized void translate(float dx, float dy, float dz)
	{
//		Vector3f v = vectorInFixedSystem(dx, dy, dz, moveMode);
//		float x = v.getX();
//		float y = v.getY();
//		float z = v.getZ();
		
		float x = -dx;
		float y = dy;
		float z = dz;
		
		if(nextPos == null)
			nextPos = new Vector3f();
		
		if((x != 0.0f && nextPos.getX() * x <= 0.0f)
				|| (nextPos.getX() >= 0.0f && nextPos.getX() < x)
				|| (nextPos.getX() <  0.0f && nextPos.getX() > x))
			nextPos.setX(x);
		if((y != 0.0f && nextPos.getY() * y <= 0.0f)
				|| (nextPos.getY() >= 0.0f && nextPos.getY() < y)
				|| (nextPos.getY() <  0.0f && nextPos.getY() > y))
			nextPos.setY(y);
		if((z != 0.0f && nextPos.getZ() * z <= 0.0f)
				|| (nextPos.getZ() >= 0.0f && nextPos.getZ() < z)
				|| (nextPos.getZ() <  0.0f && nextPos.getZ() > z))
			nextPos.setZ(z);
	}
	private void translateImpl(Vector3f deplacement)
	{
		Vector3f v = multiply(deplacement, MOVE_SCALE);
		v = vectorInFixedSystem(v.getX(), v.getY(), v.getZ(), moveMode);
		setPosition(v.add(getViewable().getPosition()));
		
		if(nextPos != null && nextPos.isNullVector()) nextPos = null;
	}
	
	private void checkPosition()
	{
		if(moveMode == CameraMode.TERRAIN)
		{
			Vector4f bounds = terrain.getBounds();
			float minX = bounds.getX();
			float maxX = minX + bounds.getZ();
			float minZ = bounds.getY();
			float maxZ = minZ + bounds.getW();
			
			if(getViewable().getPosition().getX() > maxX)
				getViewable().getPosition().setX(maxX);
			else if(getViewable().getPosition().getX() < minX)
				getViewable().getPosition().setX(minX);
			if(getViewable().getPosition().getZ() > maxZ)
				getViewable().getPosition().setZ(maxZ);
			else if(getViewable().getPosition().getZ() < minZ)
				getViewable().getPosition().setZ(minZ);
			
			float terrainHeight = terrain.getHeight(getViewable().getPosition());
			if(getViewable().getPosition().getY() < terrainHeight)
			{
				getViewable().getPosition().setY(terrainHeight);
				getViewable().setSpeed(new Vector3f());
			}
		}
		else if(moveMode != CameraMode.FREE)
		{
			if(getViewable().getPosition().getX() > MAX_X)
				getViewable().getPosition().setX(MAX_X);
			else if(getViewable().getPosition().getX() < MIN_X)
				getViewable().getPosition().setX(MIN_X);
			if(getViewable().getPosition().getY() > MAX_Y)
				getViewable().getPosition().setY(MAX_Y);
			else if(getViewable().getPosition().getY() < MIN_Y)
				getViewable().getPosition().setY(MIN_Y);
			if(getViewable().getPosition().getZ() > MAX_Z)
				getViewable().getPosition().setZ(MAX_Z);
			else if(getViewable().getPosition().getZ() < MIN_Z)
				getViewable().getPosition().setZ(MIN_Z);
		}
	}
	
	/**
	 * Convert a vector expressed in eye axis system to a vector expressed
	 * in the fixed axis system.
	 */
	public Vector3f vectorInFixedSystem(Vector3f vector)
	{
		return vectorInFixedSystem(vector.getX(), vector.getY(), vector.getZ());
	}
	/**
	 * Convert a vector (dx, dy, dz) expressed in eye axis system to a vector expressed
	 * in the fixed axis system.
	 */
	public Vector3f vectorInFixedSystem(float dx, float dy, float dz)
	{
		return vectorInFixedSystem(dx, dy, dz, CameraMode.FREE);
	}
	protected abstract Vector3f vectorInFixedSystem(float dx, float dy, float dz, CameraMode mode);
	
							/*Rotation*/
	
	public abstract Vector3f getRotation();
	public void setRotation(float x, float y, float z){ setRotation(new Vector3f(x, y, z)); }
	public abstract void setRotation(Vector3f vector);
	
	public abstract Vector3f getForward();
	public void setForward(float x, float y, float z){ setForward(new Vector3f(x, y, z)); }
	public abstract void setForward(Vector3f forward);
	
	public abstract Vector3f getUp();
	public void setUp(float x, float y, float z){ setUp(new Vector3f(x, y, z)); }
	public abstract void setUp(Vector3f up);
	
	public abstract Vector3f getLeft();
	public void setLeft(float x, float y, float z){ setLeft(new Vector3f(x, y, z)); }
	public abstract void setLeft(Vector3f left);
	
	public void rotate(Vector3f vector){ rotate(vector.getX(), vector.getY(), vector.getZ()); }
	/**
	 * Rotate the camera.
	 * @param angleX rotation in degrees around the sideward vector.
	 * @param angleY rotation in degrees around the up vector.
	 */
	public synchronized void rotate(float angleX, float angleY)
	{
		rotate(angleX, angleY, 0.0f);
	}
	/**
	 * Rotate the camera.
	 * @param angleX rotation in degrees around the sideward vector.
	 * @param angleY rotation in degrees around the up vector.
	 * @param angleZ this parameter is not take in account.
	 */
	public synchronized void rotate(float angleX, float angleY, float angleZ)
	{
		float newAngleX = angleX % 360.0f;
		float newAngleY = angleY % 360.0f;
		float newAngleZ = angleZ % 360.0f;	//TODO Ignored
		
		if(nextRotation == null)
			nextRotation = new Vector3f();
		
		if((nextRotation.getX() * newAngleX <= 0.0f && newAngleX != 0.0f)
				|| (nextRotation.getX() >= 0.0f && nextRotation.getX() < newAngleX)
				|| (nextRotation.getX() <  0.0f && nextRotation.getX() > newAngleX))
			nextRotation.setX(newAngleX);
		if((nextRotation.getY() * newAngleY <= 0.0f && newAngleY != 0.0f)
				|| (nextRotation.getY() >= 0.0f && nextRotation.getY() < newAngleY)
				|| (nextRotation.getY() <  0.0f && nextRotation.getY() > newAngleY))
			nextRotation.setY(newAngleY);
		if((nextRotation.getZ() * newAngleZ <= 0.0f && newAngleZ != 0.0f)
				|| (nextRotation.getZ() >= 0.0f && nextRotation.getZ() < newAngleZ)
				|| (nextRotation.getZ() <  0.0f && nextRotation.getZ() > newAngleZ))
			nextRotation.setZ(newAngleZ);
	}
	protected abstract void rotateImpl(Vector3f rotation);
	protected abstract void checkRotation();
	
					/*Reshape*/
	
	/**
	 * Call this at the reshape event.
	 */
	public void reshape()
	{
		resetMouse = true;
	}
	
					/*Key Events*/
	
	public void keyTyped(KeyEvent ke){}
	public void keyPressed(KeyEvent ke)
	{
		switch(ke.getKeyCode())
		{
			case KeyEvent.VK_F1:
				if(DEBUG)
				{
					try {
						System.out.println("CamPosition="+getCameraPosition());
						System.out.println("Forward="+getForward());
						System.out.println("Up="+getUp());
						System.out.println("Left="+getLeft());
						System.out.println("Rotation="+getRotation());
					} catch(RuntimeException e){}
				}
				break;
			
			case KeyEvent.VK_ADD:
				if(thirdPersonView)
					nextCameraOffset = cameraOffsetStep;
				break;
			case KeyEvent.VK_SUBTRACT:
				if(thirdPersonView)
					nextCameraOffset = -cameraOffsetStep;
				break;
			
			case KeyEvent.VK_PAGE_UP: if(moveMode == CameraMode.FREE) { upKey = true; } break;
			case KeyEvent.VK_PAGE_DOWN: if(moveMode == CameraMode.FREE) { downKey = true; } break;
			
			case KeyEvent.VK_W: frontKey = true; break;
			case KeyEvent.VK_S: backKey = true; break;
			case KeyEvent.VK_A: leftKey = true; break;
			case KeyEvent.VK_D: rightKey = true; break;
			case KeyEvent.VK_SPACE: jumpKey = true; break;
			case KeyEvent.VK_CONTROL: duckKey = true; break;
			case KeyEvent.VK_SHIFT: slowKey = true; break;
		}
	}
	
	public void keyReleased(KeyEvent ke)
	{
		switch(ke.getKeyCode())
		{
			case KeyEvent.VK_C: setMouseControlled(!isMouseControlled()); break;
			case KeyEvent.VK_Q:
				if(DEBUG)
				{
					switch(moveMode)
					{
						case FREE:
							moveMode = CameraMode.NORMAL;
							break;
						default:
						case NORMAL:
							if(terrain != null)
								moveMode = CameraMode.TERRAIN;
							else
								moveMode = CameraMode.FREE;
							break;
						case TERRAIN:
							moveMode = CameraMode.FREE;
							break;
					}
				}
				break;
				
			case KeyEvent.VK_PAGE_UP: upKey = false; break;
			case KeyEvent.VK_PAGE_DOWN: downKey = false; break;
			
			case KeyEvent.VK_W: frontKey = false; break;
			case KeyEvent.VK_S: backKey = false; break;
			case KeyEvent.VK_A: leftKey = false; break;
			case KeyEvent.VK_D: rightKey = false; break;
			case KeyEvent.VK_SPACE: jumpKey = false; break;
			case KeyEvent.VK_CONTROL: duckKey = false; break;
			case KeyEvent.VK_SHIFT: slowKey = false; break;
		}
	}
	
	/**
	 * Enable if setUseThirdPaersonView is enabled
	 */
	public void setCameraOffset(float cameraOffset)
	{
		if(thirdPersonView)
			nextCameraOffset = cameraOffset < 0.0f ? 0.0f : cameraOffset;
	}
	public float getCameraOffset(){ return cameraOffset; }
	
					/*Mouse events*/
	
	public void mouseClicked(MouseEvent me){}
	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){}
	public void mouseMoved(MouseEvent me)
	{
		mouseAction(me);
	}
	public void mouseDragged(MouseEvent me)
	{
		mouseAction(me);
	}
	private void mouseAction(MouseEvent me)
	{
		if(mouseControlled == true && robot != null && !mouseControlledFlag)
		{
			Component source = (Component)me.getSource();
			
			if(resetMouse)
			{
				centerTheMouse(source);
				resetMouse = false;
				return;
			}
			
			if(!lock)
			{
				float centerX = source.getWidth()/2;
				float centerY = source.getHeight()/2;
				centerx = centerX;
				centery = centerY;
				Point mouse = me.getPoint();
				
				float angleX = (centerY-mouse.y)/mouseSensibility;		//center-mouse because the (0, 0) is a the top left
				float angleY = (mouse.x-centerX)/mouseSensibility;
				rotate(angleX, angleY);
			}
			
			centerTheMouse(source);
		}
	}
	
	private float centerx;
	private float centery;
	
	public float getCenterx() {
		return centerx;
	}
	public float getCentery() {
		return centery;
	}
	private void centerTheMouse(Component component)
	{
		if(robot != null)
		{
			mouseControlledFlag = true;
			robot.mouseMove(component.getLocationOnScreen().x+component.getWidth()/2, component.getLocationOnScreen().y+component.getHeight()/2);
			mouseControlledFlag = false;
		}
	}
	
						/* Access to Viewer properties*/
	
	public Vector3f getCameraPosition()
	{
		Vector3f cameraPos = getViewable().getPosition().clone();
		cameraPos.addY(getViewable().getViewerHeight() * duckHeightFactor * ZOOM);
		//Camera offset (3rd person view)
		if(getCameraOffset() != 0.0f)
			cameraPos = add(cameraPos, multiply(getForward(), -getCameraOffset()));
		return cameraPos;
	}
	
					/* States */
	
	protected boolean lock = false;
	public void lock()
	{
		lock = true;
	}
	public void unlock()
	{
		lock = false;
	}
	
	public boolean isThirdPersonView()
	{
		return thirdPersonView;
	}
	public void setThirdPersonView(boolean useThirdPersonView)
	{
		this.thirdPersonView = useThirdPersonView;
	}
	
						/*Getters & setters*/
	
	public void setMouseControlled(boolean control)
	{
		resetMouse = true;
		mouseControlled = control;
	}
	public boolean isMouseControlled()
	{
		return mouseControlled;
	}
	
	public void setMoveMode(CameraMode mode)
	{
		moveMode = mode;
	}
	public CameraMode getMoveMode()
	{
		return moveMode;
	}

	public Viewable getViewable()
	{
		if(viewer == null)
			throw new RuntimeException("You need to attach a viewer with attachViewer(Viewable)");
		return viewer;
	}
}