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

import sound.PlayWav;
import ui.HandImage;

import data.VisualData;
import draw3D.camera.ViewerCamera;
import draw3D.camera.setup.CameraMode;
import draw3D.camera.setup.Viewer;
import draw3D.scenario.MazeGenerator;
import draw3D.scenario.screen.Triangle3D;
import net.java.games.jogl.*;


public class OpenGLCanvas extends Scene
{
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
	private HandImage handImage;
	
	public OpenGLCanvas(HandImage handImage) {
		getCounter().setEnabled(true);
		getCounter().setFPSEnabled(true);
		this.handImage = handImage;
	}
	
	private static Cursor loadCursor(String cursorName, Point hotSpot)
	{
		Image cursor = new ImageIcon(OpenGLCanvas.class.getClassLoader().getResource(cursorName)).getImage();
		return Toolkit.getDefaultToolkit().createCustomCursor(cursor, hotSpot,cursorName);
	}
	
	
	public void reshape(GLDrawable glDrawable, int x, int y, int width, int height)
	{	super.reshape(glDrawable, x, y, width, height);
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

		quadric = glu.gluNewQuadric();

		gl.glShadeModel(GL.GL_SMOOTH);							//Smooth color shading
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);				//Clearing color
		gl.glClearDepth(1.0);									//Enable Clearing of the Depth buffer
		gl.glDepthFunc(GL.GL_LEQUAL);							//Type of Depth test
		gl.glEnable(GL.GL_DEPTH_TEST);							//Enable Depth Testing
		
		labyrinth = new MazeGenerator(labyrinthSize);
		labyrinth.generate();
			
		//Define the correction done to the perspective calculation (perspective looks a it better)
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		
		//Ambient light component
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, lightAmbient);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, lightDiffuse);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular);
		
		
		
		//Light position
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, Setup3D.lightPosition0);

		//Enable the first light ang thelighting mode
		gl.glEnable(GL.GL_LIGHT0);
		
		
		
		
		
		//Ambient light component
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, lightAmbient);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, lightDiffuse);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, specular);
		
		//Light position
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, Setup3D.lightPosition1);

		//Enable the first light ang thelighting mode
		gl.glEnable(GL.GL_LIGHT1);
		
		
		
		
		
		
		//Ambient light component
		gl.glLightfv(GL.GL_LIGHT2, GL.GL_AMBIENT, lightAmbient);
		gl.glLightfv(GL.GL_LIGHT2, GL.GL_DIFFUSE, lightDiffuse);
		gl.glLightfv(GL.GL_LIGHT2, GL.GL_SPECULAR, specular);
		
		//Light position
		gl.glLightfv(GL.GL_LIGHT2, GL.GL_POSITION, Setup3D.lightPosition2);

		//Enable the first light ang thelighting mode
		gl.glEnable(GL.GL_LIGHT2);
		
		//Ambient light component
		gl.glLightfv(GL.GL_LIGHT3, GL.GL_AMBIENT, lightAmbient);
		gl.glLightfv(GL.GL_LIGHT3, GL.GL_DIFFUSE, lightDiffuse);
		gl.glLightfv(GL.GL_LIGHT3, GL.GL_SPECULAR, specular);
		
		//Light position
		gl.glLightfv(GL.GL_LIGHT3, GL.GL_POSITION, Setup3D.lightPosition3);

		//Enable the first light ang thelighting mode
		gl.glEnable(GL.GL_LIGHT3);
		
		
		gl.glEnable(GL.GL_LIGHTING);
		
		getAnimator().start();
	}

	//define the light properties
	float[] lightDiffuse = {1.0f, 0.0f, 0.0f, 1.0f};	//yellow diffuse : color where light hit directly the object's surface
	float[] lightAmbient = {1.0f, 0.0f, 0.0f, 1.0f};	//red ambient    : color applied everywhere
	float specular[] = {1.0f, 1.0f, 1.0f, 1.0f};
	
	
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

	
		gl.glDepthMask(true);
		gl.glColorMask(true, true, true, true);				
	
		
		//drawLights
		drawLights(glDrawable);
		
		
		
		//Mesh and Circle
		if (Setup3D.eLight0){
			gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, Setup3D.lightPosition0);
			gl.glEnable(GL.GL_LIGHT0);
		}
		if (Setup3D.eLight1){
			gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, Setup3D.lightPosition1);
			gl.glEnable(GL.GL_LIGHT1);
		}
		if (Setup3D.eLight2){
			gl.glLightfv(GL.GL_LIGHT2, GL.GL_POSITION, Setup3D.lightPosition2);
			gl.glEnable(GL.GL_LIGHT2);
		}
		if (Setup3D.eLight3){
			gl.glLightfv(GL.GL_LIGHT3, GL.GL_POSITION, Setup3D.lightPosition3);
			gl.glEnable(GL.GL_LIGHT3);
		}
		
		gl.glEnable(GL.GL_LIGHTING);
		float ret=1;
		if(Mesh.sequences){
			
			ret = indexStep*(Mesh.distanceCircles*(Mesh.endSequence-Mesh.startSequence));
			ret = -1*ret;
		}
		
		gl.glTranslatef(Setup3D.objectPosition[0], Setup3D.objectPosition[1], Setup3D.objectPosition[2]+ret);
		
		//SimpleShape.drawGLUSphere(glDrawable, 2.0f, 40, false, GLU.GLU_FILL);//Test Circle

		
		gl.glBegin(Setup3D.mesh);
		
		if(!Mesh.sequences)
			drawMesh();
		else
			drawMeshStepByStep();
    	
    	gl.glEnd();

		gl.glTranslatef(-Setup3D.objectPosition[0], -Setup3D.objectPosition[1], -(Setup3D.objectPosition[2]+ret));
		
		gl.glDisable(GL.GL_LIGHT3);
		gl.glDisable(GL.GL_LIGHT2);
		gl.glDisable(GL.GL_LIGHT1);
		gl.glDisable(GL.GL_LIGHT0);
		
		gl.glDisable(GL.GL_LIGHTING);
		 		 
		
		
		gl.glStencilFunc(GL.GL_EQUAL, 1, 1);					
		gl.glStencilOp(GL.GL_KEEP, GL.GL_KEEP, GL.GL_KEEP);		
		
//		FPS information
		drawFPSInformation(glDrawable);
	}
	}

	private void drawLights(GLDrawable glDrawable) {
		gl.glColor3f(0.5f, 0.5f,0.5f);
		
		SimpleShape.drawBoxAt(gl, 0.05f, 0.05f, 6f, Setup3D.lightPosition0[0], Setup3D.lightPosition0[1], Setup3D.lightPosition0[2]+3);
		SimpleShape.drawBoxAt(gl, 6f, 0.05f, 0.05f, Setup3D.lightPosition0[0]+3, Setup3D.lightPosition0[1], Setup3D.lightPosition0[2]);
		SimpleShape.drawBoxAt(gl, 0.05f, 0.05f, 6f, Setup3D.lightPosition1[0], Setup3D.lightPosition1[1], Setup3D.lightPosition1[2]+3);
		SimpleShape.drawBoxAt(gl, 6f, 0.05f, 0.05f, Setup3D.lightPosition2[0]+3, Setup3D.lightPosition2[1], Setup3D.lightPosition2[2]);
		
		
		//Light
			
			gl.glTranslatef(Setup3D.lightPosition0[0], Setup3D.lightPosition0[1], Setup3D.lightPosition0[2]);
			
			if (Setup3D.eLight0){
				gl.glColor3f(1.0f, 1.0f,0.0f);
			}else
				gl.glColor3f(0.5f, 0.5f,0.5f);
			SimpleShape.drawGLUSphere(glDrawable, 0.2f, 15, false, GLU.GLU_FILL);
					
			gl.glTranslatef(-Setup3D.lightPosition0[0], -Setup3D.lightPosition0[1], -Setup3D.lightPosition0[2]);
			
			gl.glTranslatef(Setup3D.lightPosition1[0], Setup3D.lightPosition1[1], Setup3D.lightPosition1[2]);
			if (Setup3D.eLight1){
				gl.glColor3f(1.0f, 1.0f,0.0f);
			}else
				gl.glColor3f(0.5f, 0.5f,0.5f);
			
			SimpleShape.drawGLUSphere(glDrawable, 0.2f, 15, false, GLU.GLU_FILL);
			gl.glTranslatef(-Setup3D.lightPosition1[0], -Setup3D.lightPosition1[1], -Setup3D.lightPosition1[2]);
		
		
			gl.glTranslatef(Setup3D.lightPosition2[0], Setup3D.lightPosition2[1], Setup3D.lightPosition2[2]);
			if (Setup3D.eLight2){
				gl.glColor3f(1.0f, 1.0f,0.0f);
			}else
				gl.glColor3f(0.5f, 0.5f,0.5f);
			SimpleShape.drawGLUSphere(glDrawable, 0.2f, 15, false, GLU.GLU_FILL);
			gl.glTranslatef(-Setup3D.lightPosition2[0], -Setup3D.lightPosition2[1], -Setup3D.lightPosition2[2]);
		
			gl.glTranslatef(Setup3D.lightPosition3[0], Setup3D.lightPosition3[1], Setup3D.lightPosition3[2]);
			if (Setup3D.eLight3){
				gl.glColor3f(1.0f, 1.0f,0.0f);
			}else
				gl.glColor3f(0.5f, 0.5f,0.5f);
			SimpleShape.drawGLUSphere(glDrawable, 0.2f, 15, false, GLU.GLU_FILL);
			gl.glTranslatef(-Setup3D.lightPosition3[0], -Setup3D.lightPosition3[1], -Setup3D.lightPosition3[2]);
	}

	
	private boolean showKeys=false;
	private void drawFPSInformation(GLDrawable glDrawable) {
		Dimension size = glDrawable.getSize();
	//	screen.drawScore(size);
		
		ViewingTools.beginOrtho(glDrawable);
		gl.glColor3f(0.0f, 0.0f, 0.0f);
		VisualData.jLabelFPS.setText("FPS = "+getCounter().getFPS());
		if(showKeys){
			printer.drawTextAt(glDrawable, "C : Take/Give-up mouse control", 10, size.height-20, 0);
			printer.drawTextAt(glDrawable, "Shift : Walk/Run", 10, size.height-40, 0);
			printer.drawTextAt(glDrawable, "Ctrl : Duck", 10, size.height-60, 0);
			printer.drawTextAt(glDrawable, "Space : Jump", 10, size.height-80, 0);
			printer.drawTextAt(glDrawable, "Q : Deplacement mode", 10, size.height-100, 0);
			printer.drawTextAt(glDrawable, "Up : Light go ahead", 10, size.height-120, 0);
			printer.drawTextAt(glDrawable, "Down : Light go back", 10, size.height-140, 0);
			printer.drawTextAt(glDrawable, "Left : Light go left", 10, size.height-160, 0);
			printer.drawTextAt(glDrawable, "Right : Light go right", 10, size.height-180, 0);
			printer.drawTextAt(glDrawable, "UpPage : Light go uo", 10, size.height-200, 0);
			printer.drawTextAt(glDrawable, "DowndPage : Light go down", 10, size.height-220, 0);
			printer.drawTextAt(glDrawable, "End : Light Positional", 10, size.height-240, 0);
			printer.drawTextAt(glDrawable, "1 : Enable Light 1", 10, size.height-260, 0);
			printer.drawTextAt(glDrawable, "2 : Enable Light 2", 10, size.height-280, 0);
			printer.drawTextAt(glDrawable, "3 : Enable Light 3", 10, size.height-300, 0);
			printer.drawTextAt(glDrawable, "4 : Enable Light 4", 10, size.height-320, 0);
			printer.drawTextAt(glDrawable, "M : Meash Line/Full", 10, size.height-340, 0);
			printer.drawTextAt(glDrawable, "N : Sequence On/Off", 10, size.height-360, 0);
			
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
			case KeyEvent.VK_PAGE_UP: moveLight(1, 0.1f);break;
			case KeyEvent.VK_PAGE_DOWN: moveLight(1, -0.1f);break;
			case KeyEvent.VK_LEFT: moveLight(0, 0.1f);break;
			case KeyEvent.VK_RIGHT: moveLight(0, -0.1f);break;
			case KeyEvent.VK_UP: moveLight(2, 0.1f);break;
			case KeyEvent.VK_DOWN: moveLight(2, -0.1f);break;
			case KeyEvent.VK_END:Setup3D.setPositional() ;break;
			case KeyEvent.VK_DELETE:setSmooth() ;break;
			case KeyEvent.VK_1:Setup3D.enableLight0() ;break;
			case KeyEvent.VK_2:Setup3D.enableLight1() ;break;
			case KeyEvent.VK_3:Setup3D.enableLight2() ;break;
			case KeyEvent.VK_4:Setup3D.enableLight3() ;break;
			case KeyEvent.VK_M:setMesh();break;
			case KeyEvent.VK_N:Mesh.sequences=!Mesh.sequences;break;
			
			
		}
	}

	private void setMesh(){
		if (Setup3D.mesh==GL.GL_LINE_STRIP)
			Setup3D.mesh=GL.GL_TRIANGLES;
		else
			Setup3D.mesh=GL.GL_LINE_STRIP;
		
	}
	
	private int smooth = GL.GL_SMOOTH;
	private void setSmooth(){
		if (smooth== GL.GL_SMOOTH){
			gl.glShadeModel(GL.GL_FLAT);
			smooth = 1;
			
		}
		else{
			gl.glShadeModel(GL.GL_SMOOTH);
			smooth = GL.GL_SMOOTH;
			
		}
	}
	

	private void moveLight(int index,float sum){
		Setup3D.lightPosition0[index]+=sum;
		Setup3D.lightPosition1[index]+=sum;
		Setup3D.lightPosition2[index]+=sum;
		Setup3D.lightPosition3[index]+=sum;
	}
	@Override
	public void mouseMoved(MouseEvent me) {
		// TODO Auto-generated method stub
		super.mouseMoved(me);
//		Setup3D.lightPosition0[0]=me.getX();
	}
	
	public void keyReleased(KeyEvent ke){
		pressingKey=false;
	}
	

	private boolean draw=true;

	public boolean isDraw() {
		return draw;
	}
	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	private void drawMesh(){
		Vector<Triangle3D> triangles = Mesh.getTriangles();
		int s = triangles.size();
		for (int i =0;i<s;i++){
			triangles.get(i).draw(gl);
		}
	}
	
	private float zOffset =0f;
	private int sens = -1;
	
	public void increaseMovements(GLDrawable glDrawable)
	{
		zOffset += (float)getCounter().getTimePassed()/(VisualData.sliderBarValue*10);
		if(zOffset>1){
			indexStep++;
			zOffset=0;
		}
		if(indexStep == cantSteps)
			indexStep=0;
	}
	
	
	int indexStep=0;
	int cantSteps = Mesh.trianglesStep.size();
	
	private void drawMeshStepByStep(){
		
		
		Vector<Vector> steps = Mesh.getTrianglesStep();
		if(steps.size()>0){	
			Vector<Triangle3D> triangles = steps.get(indexStep);
			int s = triangles.size();
			for (int i =0;i<s;i++){
				triangles.get(i).draw(gl);
			}
		}else
			Mesh.sequences=false;
		
	}
	
}