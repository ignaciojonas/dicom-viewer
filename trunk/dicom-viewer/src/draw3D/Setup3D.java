package draw3D;

import java.util.Vector;

import net.java.games.jogl.GL;
import net.java.games.jogl.GLDrawable;
import org.jouvieje.jogl.texture.TextureLoader;

import sound.PlayWav;

public class Setup3D {

	

	public static final String light="sounds/light.wav";
	public static final String light2="sounds/light2.wav";
	public static String cross = "textures/cross3.png";
	
	
	public static String FPSInfo="FPS";
	
	
	public static final float GRID_SIZE = 4.0f;
	
	
	public static final int[] textures=new int[1];
	
	public static boolean loadTextures(GLDrawable glDrawable)
	{
		TextureLoader textureLoader = new TextureLoader(glDrawable);
		textureLoader.readPicture("textures/metaltechfloor01final.jpg");
		textures[0] = textureLoader.loadTexture(true,GL.GL_LINEAR, GL.GL_LINEAR_MIPMAP_LINEAR);
		return true;
	}
	
	
	public static void setPositional(){
		if (Setup3D.lightPosition0[3]==0){
			Setup3D.lightPosition0[3]=1;
			Setup3D.lightPosition1[3]=1;
			Setup3D.lightPosition2[3]=1;
			Setup3D.lightPosition3[3]=1;
		}
		else{
			Setup3D.lightPosition0[3]=0;
			Setup3D.lightPosition1[3]=0;
			Setup3D.lightPosition2[3]=0;
			Setup3D.lightPosition3[3]=0;
		}
	}
	
	//LIGHT
	public static boolean eLight1 = true;
	
	public static void enableLight1(){
		if (eLight1){
			new PlayWav(Setup3D.light,0);
		}else
			new PlayWav(Setup3D.light2,0);
		eLight1=!eLight1;
	}
	public static boolean eLight2 = true;
	
	public static  void enableLight2(){
		if (eLight2){
			new PlayWav(Setup3D.light,0);
		}else
			new PlayWav(Setup3D.light2,0);
		eLight2=!eLight2;
	}
	
	public static boolean eLight0 = true;
	
	public static  void enableLight0(){
		if (eLight0){
			new PlayWav(Setup3D.light,0);
		}else
			new PlayWav(Setup3D.light2,0);
		eLight0=!eLight0;
	}
	

	public static boolean eLight3 = true;
	
	public static  void enableLight3(){
		if (eLight3){
			new PlayWav(Setup3D.light,0);
		}else
			new PlayWav(Setup3D.light2,0);
		eLight3=!eLight3;
	}
	
	
	

	
	
	
	  //ancho,alto,lejos	
	public static float[] lightPosition0= {2.0f, 2.0f, 4.0f, 0.0f};
	public static float[] lightPosition1= {8.0f, 2.0f, 4.0f, 0.0f};
	public static float[] lightPosition2= {2.0f, 2.0f, 10.0f, 0.0f};
	public static float[] lightPosition3= {8.0f, 2.0f, 10.0f, 0.0f};
	
	
	public static float[] objectPosition= {2.0f, 2.0f, 7.0f, 1.0f};
}	


