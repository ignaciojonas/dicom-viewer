
package draw3D.scenario;

import java.awt.Dimension;

import net.java.games.jogl.GL;
import net.java.games.jogl.GLDrawable;

import org.jouvieje.jogl.container.FrameContainer;
import org.jouvieje.jogl.font.FontRenderer;
import org.jouvieje.jogl.font.GlutFont;
import org.jouvieje.jogl.font.FontRenderer.FontAlign;
import org.jouvieje.jogl.scene.Scene;
import org.jouvieje.jogl.texture.TextureLoader;
import org.jouvieje.jogl.tools.SimpleShape;
import org.jouvieje.opengl.load.Load;
import org.jouvieje.opengl.load.Settings;
import org.jouvieje.util.math.Vector2f;
import org.jouvieje.util.picture.RGBPicture;

public class JumpSimulator extends Scene
{
	public static void main(String[] args)
	{
		Load.loadScene("Jump simulator - Jouvieje's Tutorial",
				JumpSimulator.class,
				new Settings(Settings.Renderer.JOGL, new Dimension(SIZE, SIZE)));
	}
	
	private FontRenderer printer = GlutFont.getRenderer();
	
	//Render to a texture
	private final static int SIZE = 512;
	private int renderTexture;
	private boolean firstDraw = true;
	
	//Factor to fit the the screen
	private final int positionFactor = 130;
	private final int vectorFactor = 25;
	
	public JumpSimulator(FrameContainer frame)
	{
		frame.setResizable(false);
		
		getCounter().setEnabled(true);
		getCounter().setFPSEnabled(true);
	}
	
	public void reshape(GLDrawable glDrawable, int x, int y, int width, int height)
	{
		GL gl = glDrawable.getGL();
		
		gl.glViewport(0, 0, SIZE, SIZE);
		
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0, width, 0, height, -1, 1);
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
	
	/*****************************
	 *     The init method       *
	 *****************************/
	public void init(GLDrawable glDrawable)
	{
		super.init(glDrawable);
		GL gl = glDrawable.getGL();
		
		//Texture used to draw in it
		TextureLoader textureLoader = new TextureLoader(glDrawable);
		textureLoader.setPicture(new RGBPicture(SIZE, SIZE));
		renderTexture = textureLoader.loadTexture();
		if(!gl.glIsTexture(renderTexture))
		{
			System.err.println("Unable to create a texture !");
			System.exit(1);
		}
		gl.glBindTexture(GL.GL_TEXTURE_2D, renderTexture);
		
		gl.glShadeModel(GL.GL_SMOOTH);					//Smooth color shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);		//Clearing color
		gl.glClearDepth(1.0);							//Enable Clearing of the Depth buffer
		gl.glDepthFunc(GL.GL_LEQUAL);					//Type of Depth test
		gl.glEnable(GL.GL_DEPTH_TEST);					//Enable Depth Testing
		
		//Define the correction done to the perspective calculation (perspective looks a it better)
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

		//Draw the scene in loop
		getAnimator().start();
	}
	
	/***********************
	 *    Draw the scene   *
	 ***********************/
	public void displayScene(GLDrawable glDrawable)
	{
		GL gl = glDrawable.getGL();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); //Clear The Screen And The Depth Buffer
		gl.glLoadIdentity();                                         //Reset The View
		
		jump.update();
		
		int width = glDrawable.getSize().width;
		int height = glDrawable.getSize().height;
		
		//Draw previous frame
		gl.glEnable(GL.GL_TEXTURE_2D);
			SimpleShape.drawXYRect(gl, width, height);
		gl.glDisable(GL.GL_TEXTURE_2D);
		
		if(firstDraw)
		{
			//Draw Axis
			drawArray(gl, 10.0f, 10.0f, SIZE-10.0f, 10.0f, 1, 0);
			drawArray(gl, 10.0f, 10.0f, 10.0f, SIZE-10.0f, 0, 1);
		}
		
		//Jump
		gl.glPushMatrix();
			gl.glTranslatef(10.f, 10.0f, 0.0f);
			drawJump(glDrawable);
		gl.glPopMatrix();
		
		//Draw FPS
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		SimpleShape.drawXYRectAt(gl, width/4.0f, 20, 3.0f*width/4.0f, height-20, 0.0f);
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		printer.drawTextAt(glDrawable, "FPS = "+getCounter().getFPS(), FontAlign.BotRight, width-10, height-20, 0);
		
		//Save current frame
		gl.glCopyTexImage2D(GL.GL_TEXTURE_2D,
				0,
				GL.GL_RGB,
				0,
				0,
				SIZE,
				SIZE,
				0);
		
		firstDraw = false;
	}
	
	private void drawJump(GLDrawable glDrawable)
	{
		GL gl = glDrawable.getGL();
		//Draw v1 & v2
		if(firstDraw)
		{
			gl.glColor3f(0.0f, 1.0f, 1.0f);
			float k = startPosition.getX() * positionFactor;
			float z = startPosition.getY() * positionFactor;
				
			drawArray(gl, k, z, k+v1*vectorFactor, z, 1, 0);
			printer.drawTextAt(glDrawable, "V1", k+v1*vectorFactor+5.0f, z, 0);
			drawArray(gl, k, z, k, z+v2*vectorFactor, 0, 1);
			printer.drawTextAt(glDrawable, "V2", k+5.0f, z+v2*vectorFactor+5.0f, 0);
			
			gl.glBegin(GL.GL_LINES);
				gl.glVertex2f(k, z);
				gl.glVertex2f(k+v1*vectorFactor, z+v2*vectorFactor);
			gl.glEnd();
			printer.drawTextAt(glDrawable, "V", k+v1*vectorFactor+5.0f, z+v2*vectorFactor+5.0f, 0);
		}
		
		//Draw position
		gl.glColor3f(0.5f, 0.5f, 0.5f);
		gl.glBegin(GL.GL_POINTS);
			gl.glVertex2d(position.getX() * positionFactor, position.getY() * positionFactor);
		gl.glEnd();
		gl.glColor3f(1.0f, 1.0f, 1.0f);
	}
	
	private void drawArray(GL gl, float startX, float startY, float endX, float endY, int arrayX, int arrayY)
	{
		gl.glBegin(GL.GL_LINES);
			gl.glVertex2f(startX, startY);
			gl.glVertex2f(endX, endY);
			
			gl.glVertex2f(endX, endY);
			gl.glVertex2f(endX-5.0f*arrayX-5.0f*arrayY, endY-5.0f*arrayX-5.0f*arrayY);
			
			gl.glVertex2f(endX, endY);
			gl.glVertex2f(endX-5.0f*arrayX+5.0f*arrayY, endY+5.0f*arrayX-5.0f*arrayY);
		gl.glEnd();
	}
	
					/*Jump*/
	
	private Jump jump = new Jump();
	
	private final static float g = 9.81f;
	private final static float h = 1.75f;
	private final static float v1 = 2.0f;				//about 7.2km/h
	private final static float v2 = (float)Math.sqrt(2*g*h);
	
	private static Vector2f position = new Vector2f();
	private static Vector2f startPosition = new Vector2f(0.0f, h);
	
	class Jump
	{
		private long startTime = -1;
		private boolean end = false;
		
		private final float timeFactor = 0.25f;
		
		public Jump(){}
		
		public void update()
		{
			if(end)
				return;
			
			if(startTime == -1)
				startTime = System.currentTimeMillis();
			
			float elapsedTime = (float)(System.currentTimeMillis()-startTime)/1000.0f;
			
			updateFreeFall(elapsedTime * timeFactor);
		}
		
		private void updateFreeFall(float t)
		{
			/**
			 * Movement equation : air reaction neglected
			 *  x = v1 * t
			 *  y = -g*t²/2 + v2*t + h
			 */
			position.setX(v1*t);
			position.setY(-g*t*t/2.0f + v2*t + h);
			
			if(position.getY() < 0.0f)
				end = true;
		}
	}
}