package draw3D;

import java.util.Vector;

import net.java.games.jogl.GL;
import net.java.games.jogl.GLDrawable;
import org.jouvieje.jogl.texture.TextureLoader;

import draw3D.scenario.picker.Target;



public class Setup3D {

	

	public static final String light="sounds/light.wav";
	public static final String light2="sounds/light2.wav";
	public static String cross = "textures/cross3.png";
	
	
	public static String FPSInfo="FPS";
	
	
	public static final float GRID_SIZE = 4.0f;
	
	
	public static final Vector<Target> targets=new Vector<Target>();
	
	public static final int[] textures=new int[1];
	
	public static boolean loadTextures(GLDrawable glDrawable)
	{
		TextureLoader textureLoader = new TextureLoader(glDrawable);
		textureLoader.readPicture("textures/metaltechfloor01final.jpg");
		textures[0] = textureLoader.loadTexture(true,GL.GL_LINEAR, GL.GL_LINEAR_MIPMAP_LINEAR);
		return true;
	}
	
	//LIGHT
		 								  //ancho,alto,lejos	
	public static float[] lightPosition0= {2.0f, 2.0f, 7.0f, 1.0f};
	public static float[] lightPosition1= {8.0f, 2.0f, 7.0f, 1.0f};
	public static float[] lightPosition2= {8.0f, 2.0f, 1.0f, 1.0f};
	public static boolean enableLight0=true;
	public static boolean enableLight1=true;
	public static boolean enableLight2=true;
	public static void enableLight(int i,boolean bol){
		if(i==0)
			enableLight0=bol;
		if(i==1)
			enableLight1=bol;	
		if(i==2)
			enableLight2=bol;	
	}
}	


