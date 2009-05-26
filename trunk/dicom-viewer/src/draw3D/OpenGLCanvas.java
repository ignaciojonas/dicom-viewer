package draw3D;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.ImageIcon;
import org.jouvieje.jogl.font.GlutFont;
import org.jouvieje.jogl.font.FontRenderer.FontAlign;
import org.jouvieje.jogl.scene.Scene;
import org.jouvieje.jogl.tools.SimpleShape;
import org.jouvieje.jogl.tools.ViewingTools;
import org.jouvieje.opengl.load.Load;
import org.jouvieje.opengl.load.Settings;
import org.jouvieje.opengl.terrain.Terrain;
import org.jouvieje.util.math.Vector3f;
import org.jouvieje.util.math.Vector4f;

import data.VisualData;
import draw3D.camera.ViewerCamera;
import draw3D.camera.setup.CameraMode;
import draw3D.camera.setup.Viewer;
import draw3D.scenario.MazeGenerator;
import draw3D.scenario.picker.Picker;
import draw3D.scenario.picker.Target;
import draw3D.scenario.screen.Triangle3D;
import net.java.games.jogl.*;


public class OpenGLCanvas extends Scene
{
	
	private Picker pickable = new Picker(this);

	public int targetKill =-1;


	//Camera
	private Viewer viewer;
	public ViewerCamera camera;

	
	//Labyrinth
	private MazeGenerator labyrinth;
	private final Dimension labyrinthSize = new Dimension(20, 20);
	public final int OBJECT_ID = 20;

	private GLUquadric quadric;


	//GL
	private GL gl;
	private GLU glu;
	private GLDrawable glDrawable;
	private GlutFont printer = GlutFont.getRenderer();
	
	float cylinderRot;


	

	private float rotPyramid;			//rotation of the pyramid
	
	
	public OpenGLCanvas() {
		getCounter().setEnabled(true);
		getCounter().setFPSEnabled(true);
	}
	

	
	
	public static void main(String[] args)
	{
		Load.load("Zhooter",	OpenGLCanvas.class,Settings.Renderer.JOGL);
	}
	private static Cursor loadCursor(String cursorName, Point hotSpot)
	{
		Image cursor = new ImageIcon(OpenGLCanvas.class.getClassLoader().getResource(cursorName)).getImage();
		return Toolkit.getDefaultToolkit().createCustomCursor(cursor, hotSpot,cursorName);
		
	}
	
	
	public void reshape(GLDrawable glDrawable, int x, int y, int width, int height)
	{
		super.reshape(glDrawable, x, y, width, height);
		camera.reshape();
	}
	

	/*****************************
	 *     The init method       *
	 *****************************/
	public void init(GLDrawable glDrawable)
	{
		super.init(glDrawable);
		gl = glDrawable.getGL();
		glu = glDrawable.getGLU();
	
		
		//Viewer intialization
		viewer = new Viewer();
		viewer.setViewerHeight(2.5f);
		viewer.setPosition(1.0f, 5.0f, 1.0f);
		viewer.setRotation(0.0f, 0.0f, 0.0f);

		//Camera initialisation
		camera = new ViewerCamera(viewer);
		camera.setMoveMode(CameraMode.TERRAIN);
		camera.terrain = new Terrain(){
			Vector4f bounds = new Vector4f(-1, -1,labyrinthSize.width + 2, labyrinthSize.height  + 2);
			public float getHeight(float posx, float posy, float posz){ return 0; }
			public float getHeight(Vector3f vector){ return 0; }
			public Vector4f getBounds() { return bounds; }
		};
		camera.setMouseControlled(true);
		
		glDrawable.addKeyListener(camera);
		glDrawable.addMouseListener(camera);
		glDrawable.addMouseMotionListener(camera);
		
		
//		For object selection
		addPickable( pickable);
		
		pickable.initTargets();

		quadric = glu.gluNewQuadric();


	
		Setup3D.loadTextures(glDrawable);
		
		gl.glShadeModel(GL.GL_SMOOTH);							//Smooth color shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);				//Clearing color
		gl.glClearDepth(1.0);									//Enable Clearing of the Depth buffer
		gl.glDepthFunc(GL.GL_LEQUAL);							//Type of Depth test
		gl.glEnable(GL.GL_DEPTH_TEST);							//Enable Depth Testing

		//Define the correction done to the perspective calculation (perspective looks a it better)
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		
		
		labyrinth = new MazeGenerator(labyrinthSize);
		labyrinth.generate();
			
		initLight(gl);
		gl.glEnable(GL.GL_LIGHT0);
		gl.glEnable(GL.GL_LIGHTING);
		
		getAnimator().start();
	}
	
	

	private boolean draw=true;

	public boolean isDraw() {
		return draw;
	}
	public void setDraw(boolean draw) {
		this.draw = draw;
	}
	
	//define the light0 properties
	float[] lightDiffuse = {1.0f, 0.0f, 0.0f, 1.0f};	//yellow diffuse : color where light hit directly the object's surface
	float[] lightAmbient = {1.0f, 0.0f, 0.0f, 1.0f};	//red ambient    : color applied everywhere
	
	
	
	
	float[] objectPosition= {5.0f, 2.0f, 14.0f, 1.0f};
	
	public void drawCube(GL gl, float size)
	{
		gl.glBegin(GL.GL_QUADS);
		  //Quad 1
			gl.glNormal3f(1.0f, 0.0f, 0.0f);   //N1
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( size/2, size/2, size/2);    //V2
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( size/2,-size/2, size/2);    //V1
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( size/2,-size/2,-size/2);    //V3
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( size/2, size/2,-size/2);    //V4
		  //Quad 2
			gl.glNormal3f(0.0f, 0.0f, -1.0f);  //N2
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( size/2, size/2,-size/2);    //V4
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( size/2,-size/2,-size/2);    //V3
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-size/2,-size/2,-size/2);    //V5
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-size/2, size/2,-size/2);    //V6
		  //Quad 3
			gl.glNormal3f(-1.0f, 0.0f, 0.0f);  //N3
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-size/2, size/2,-size/2);    //V6
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-size/2,-size/2,-size/2);    //V5
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-size/2,-size/2, size/2);    //V7
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-size/2, size/2, size/2);    //V8
		  //Quad 4
			gl.glNormal3f(0.0f, 0.0f, 1.0f);   //N4
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-size/2, size/2, size/2);    //V8
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-size/2,-size/2, size/2);    //V7
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( size/2,-size/2, size/2);    //V1
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( size/2, size/2, size/2);    //V2
		  //Quad 5
			gl.glNormal3f(0.0f, 1.0f, 0.0f);   //N5
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-size/2, size/2,-size/2);    //V6
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-size/2, size/2, size/2);    //V8
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( size/2, size/2, size/2);    //V2
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( size/2, size/2,-size/2);    //V4
		  //Quad 6
			gl.glNormal3f(1.0f, -1.0f, 0.0f);  //N6
			gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-size/2,-size/2, size/2);    //V7
			gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-size/2,-size/2,-size/2);    //V5
			gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( size/2,-size/2,-size/2);    //V3
			gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( size/2,-size/2, size/2);    //V1
		gl.glEnd();
	}

	
	/***********************
	 *    Draw the scene   *
	 ***********************/
	public void displayScene(GLDrawable glDrawable)
	{
	if(draw){
		this.glDrawable = glDrawable;
		
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);       	//Clear The Screen And The Depth Buffer
		gl.glLoadIdentity();                                         		//Reset The View
		
		camera.update(getCounter().getTimePassed());
		camera.lookAt(glDrawable);

		/* Fill with one stencil buffer where the ground is */
		
		gl.glColorMask(false, false, false, false);				
		gl.glDepthMask(true);
		gl.glEnable(GL.GL_STENCIL_TEST);						
		gl.glStencilFunc(GL.GL_ALWAYS, 1, 1);					
		gl.glStencilOp(GL.GL_KEEP, GL.GL_KEEP, GL.GL_REPLACE);	
	
		gl.glDepthMask(true);
		gl.glColorMask(true, true, true, false);				
		
		
		/* Reflection */
		gl.glStencilFunc(GL.GL_EQUAL,1 , 1);					
		gl.glStencilOp(GL.GL_KEEP, GL.GL_KEEP, GL.GL_KEEP);		
		
//		gl.glPushMatrix();
//			gl.glScalef(1.0f, -1.0f, 1.0f);		//Mirror Y
//			drawObjects(glDrawable);
//			gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, Setup3D.lightPosition0);
//	        gl.glEnable(GL.GL_LIGHTING);   
//	        drawTriangles();
//	        gl.glDisable(GL.GL_LIGHTING);
//		gl.glPopMatrix();
//		gl.glDisable(GL.GL_STENCIL_TEST);

		/* Draw the ground */
		//drawGround(gl);
		
		drawObjects(glDrawable);
	
		//drawTriangles();
		gl.glStencilFunc(GL.GL_EQUAL, 1, 1);					
		gl.glStencilOp(GL.GL_KEEP, GL.GL_KEEP, GL.GL_KEEP);		
		
		gl.glPushMatrix();
			//gl.glScalef(1.0f, -1.0f, 1.0f);						//Mirror Y
			gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, Setup3D.lightPosition0);
	        gl.glEnable(GL.GL_LIGHTING);   
	        drawTriangles();
	        gl.glDisable(GL.GL_LIGHTING);
	       
		gl.glPopMatrix();
		gl.glDisable(GL.GL_STENCIL_TEST);
		
		

//		FPS information
		drawFPSInformation(glDrawable);
	}
	}

	/*
	 * Initialize all the lights
	 */
	public void initLight(GL gl)
	{
		//light properties
		float[] zero = {0.0f, 0.0f, 0.0f, 1.0f};
		float[] one = {1.0f, 1.0f, 1.0f, 1.0f};
		float[] position = {1.0f, 1.0f, 0.3f, 0.0f};
		
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, zero);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, one);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, one);		//Default value for GL_LIGHT0 
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, Setup3D.lightPosition0);
		
		//light model properties
		float[] model_ambient = {1.4f, 0.4f, 0.4f, 1.0f};						//0=2sided, 1=1sided

		gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, model_ambient);				//small white ambient light
	
		gl.glLightModeli(GL.GL_LIGHT_MODEL_LOCAL_VIEWER, 0);
			
		
	}
	
	
	float[] no_mat = {0.0f, 0.0f, 0.0f, 1.0f};
	float[] mat_ambient = {0.7f, 0.7f, 0.7f, 1.0f};
	float[] mat_ambient_color = {0.8f, 0.8f, 0.2f, 1.0f};
	float[] mat_diffuse = {0.1f, 0.5f, 0.8f, 1.0f};
	float[] mat_specular = {1.0f, 1.0f, 1.0f, 1.0f};
	float no_shininess = 0.0f;
	float low_shininess = 5.0f;
	float high_shininess = 100.0f;
	float[] mat_emission = {0.3f, 0.2f, 0.2f, 0.0f};
	
	public void drawTriangles(){
//		if(Setup3D.enableLight0)
//			gl.glEnable(GL.GL_LIGHT0);
//		if(Setup3D.enableLight1)
//			gl.glEnable(GL.GL_LIGHT1);
//		if(Setup3D.enableLight2)
//			gl.glEnable(GL.GL_LIGHT2);
//		gl.glEnable(GL.GL_LIGHTING);
		gl.glTranslatef(objectPosition[0], objectPosition[1], objectPosition[2]+zOffset);
		
		gl.glBegin(GL.GL_TRIANGLES);
		 gl.glPushMatrix();
	       	gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, Setup3D.lightPosition0);
	        gl.glEnable(GL.GL_LIGHTING);   
	        	drawMesh();
			 gl.glDisable(GL.GL_LIGHTING);
	    gl.glPopMatrix();
		gl.glEnd();
		
	//Enable the first light ang thelighting mode
//	gl.glDisable(GL.GL_LIGHT2);
//	gl.glDisable(GL.GL_LIGHT1);
//	gl.glDisable(GL.GL_LIGHT0);
//	gl.glDisable(GL.GL_LIGHTING);
	}
	
	
	
	public void drawObjects(GLDrawable glDrawable)
	{
		GL gl = glDrawable.getGL();
		GLU glu = glDrawable.getGLU();

		gl.glLoadName(OBJECT_ID);
		gl.glPushName(0);
		for(int i = 1; i < Setup3D.targets.size(); i++)
		{
			Target target = Setup3D.targets.get(i);
			target.draw(i,gl,glDrawable,quadric);
		}
	
		gl.glPopName();
		
		
	}

	

	
	private void drawGround(final GL gl) {
		
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glBindTexture(GL.GL_TEXTURE_2D,  Setup3D.textures[0]);
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_ONE, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
		SimpleShape.drawXZRect(gl, labyrinthSize.width * Setup3D.GRID_SIZE, labyrinthSize.height * Setup3D.GRID_SIZE, 40);
		
		//SimpleShape.drawXZRectCentered(gl, 20, 20, 10);
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glDisable(GL.GL_BLEND);
		gl.glDisable(GL.GL_TEXTURE_2D);
		
	}


	private boolean showKeys=false;
	private void drawFPSInformation(GLDrawable glDrawable) {
		Dimension size = glDrawable.getSize();
	//	screen.drawScore(size);
		
		ViewingTools.beginOrtho(glDrawable);
		gl.glColor3f(1.0f, 1.0f, 1.0f);
		VisualData.jLabelFPS.setText("FPS = "+getCounter().getFPS());
		if(showKeys){
			printer.drawTextAt(glDrawable, "C : Take/Give-up mouse control", 10, size.height-20, 0);
			printer.drawTextAt(glDrawable, "Shift : Walk/Run", 10, size.height-40, 0);
			printer.drawTextAt(glDrawable, "Ctrl : Duck", 10, size.height-60, 0);
			printer.drawTextAt(glDrawable, "Space : Jump", 10, size.height-80, 0);
			printer.drawTextAt(glDrawable, "Q : Deplacement mode", 10, size.height-100, 0);
			
		}else{
			printer.drawTextAt(glDrawable, "F2 : Show/Hide Controls", 10, size.height-20, 0);
		}
		
		
		ViewingTools.endOrtho(glDrawable);
		
	}
	
	private boolean pressingKey=false;
	private int pressX=0;
	public void keyPressed(KeyEvent ke)
	{
	
		super.keyPressed(ke);
		switch(ke.getKeyCode())
		{
			case KeyEvent.VK_W: pressingKey=true; break;
			case KeyEvent.VK_S: pressingKey=true;break;
			case KeyEvent.VK_A: pressingKey=true;break;
			case KeyEvent.VK_D: pressingKey=true; break;
			case KeyEvent.VK_F2:showKeys=!showKeys;break;
					
		}
	}


	
	
	public void keyReleased(KeyEvent ke){
		pressingKey=false;
		
	
	}
	
	private float zOffset = -7.0f;
	private int sens = -1;
	public void increaseMovements(GLDrawable glDrawable)
	{
//		zOffset += sens*7.f*getCounter().getTimePassed()/2000;
//		if(zOffset < -15.0f)
//			sens = 1;
//		else if(zOffset > -5.5f)
//			sens = -1;
//	
		
		
	}
	
	private void drawMesh(){
		Vector<Triangle3D> triangles = Mesh.getTriangles();
		int s = triangles.size();
		for (int i =0;i<s;i++){
			triangles.get(i).draw(gl);
		}
	}

	
}