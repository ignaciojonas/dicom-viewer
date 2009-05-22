package draw3D.scenario;

import net.java.games.jogl.GL;
import org.jouvieje.util.math.Vector2f;
import org.jouvieje.util.math.Vector3f;
import org.jouvieje.util.math.VectorMath;

public class Spring {

	public Spring(final GL gl,float posX,float posY,float posZ,float height,float r,float dr,float wave) {
		// TODO Auto-generated constructor stub
		this.gl = gl;
		this.posX=posX;
		this.posY=posY;
		this.posZ=posZ;
		this.HEIGHT=height;
		this.R=r;
		this.dr=dr; //radio central
		this.waves=wave;

	}
	
	public final float TWO_PI = 2*(float)Math.PI;
	private final GL gl;
	public final float HEIGHT;// = 4.0f;
	public float height;// = 3.0f;
	public float heightOsc = 0.9f;
	private float R;// = 1.5f;
	private float dr;// = 0.6f;
	private float waves;// = 5;
	//Modele précision
	private int precY = 4;
	private int precTeta = 2*precY;
	private float dTeta = TWO_PI / precTeta;
	//Normal mode used : true normals (per vertex), average normal (per vertex) or computed normals (per triangle)
	private int normalMode = 0;
	private boolean DEBUG_MODE = false;
	
	private float posX;// =14.0f;
	private float posY;// =0.0f;
	private float posZ;// =15.0f;
	
	//light
	float[] no_mat = {0.0f, 0.0f, 0.0f, 1.0f};
	float[] mat_ambient = {0.7f, 0.7f, 0.7f, 1.0f};
	float[] mat_ambient_color = {0.8f, 0.8f, 0.2f, 1.0f};
	float[] mat_diffuse = {0.1f, 0.5f, 0.8f, 1.0f};
	float[] mat_specular = {1.0f, 1.0f, 1.0f, 1.0f};
	float no_shininess = 0.0f;
	float low_shininess = 5.0f;
	float high_shininess = 100.0f;
	float[] mat_emission = {0.3f, 0.2f, 0.2f, 0.0f};
	
	
	public boolean intoPosition(Vector3f position){
		Vector3f aux=position;
		if((posX+R+0.3>aux.getX())&&(posX-R-0.3<aux.getX()))
			if((posZ+R+0.3>aux.getZ())&&(posZ-R-0.3<aux.getZ()))
				return true;
		return false;
	}
	
	/*
	 * Draw a 'complex' geometry using a true normals for smoother lighting.
	 * The shape is not really a spring, it is more a 'vacuum cleaner pipe'.
	 */
	public void drawSpring()
	{
		gl.glTranslatef(posX, posY, posZ);
				
		precTeta = 4*precY;
		dTeta = TWO_PI / precTeta;
		float r1 = 0.0f, r2 = 0.0f, y1 = 0.0f, y2 = 0.0f, teta = 0.0f;
		
					/*Computing an elementary geometry*/
		
		/*
		 * Calculate an elementary element of the geometry for a complete oscillation around R.
		 */
		float h = height/waves;
		Vector2f[] ry = new Vector2f[precY+1];
		for(int i = 0; i < ry.length-1; i++)	//Don't remove -1
		{
			y1 = i*h/precY;
			//Radius (function of y) : r = R + dr * cos(2*PI*y/h)
			r1 = R+dr*(float)Math.cos(TWO_PI*y1/h);
			ry[i] = new Vector2f(r1, y1);
		}
		ry[ry.length-1] = new Vector2f(ry[0].getX(), ry[0].getY()+h);	//Used to make the 'jointure'
		
		//Calculate normals 
		Vector2f[] n = new Vector2f[ry.length];
		if(normalMode == 0)
		{
			//True normals using mathematical model
			for(int i = 0; i < ry.length; i++)
			{
				r1 = ry[i].getX();
				y1 = ry[i].getY();
				/*
				 * At the point p=(r*cos(teta), y, r*sin(teta))
				 * We calculate the partial derivatives of p depending on the variables y and teta.
				 * Then the cross product, and we finally find for teta=0 the normal :
				 * N = | r 
				 *     | a*r*r0*sin(a*y)
				 *     | 0
				 *    with a = 2*PI/h
				 */
				float a = TWO_PI/h;

				n[i] = VectorMath.normalize(new Vector2f(r1, r1*a*dr*(float)Math.sin(a*y1)));

				//Debug mode : show normal vectors
				if(DEBUG_MODE)
				{
					gl.glBegin(GL.GL_LINES);
					gl.glVertex3f(r1, y1, 0.0f);
					gl.glVertex3f(r1+n[i].getX(), y1+n[i].getY(), 0.0f);
					gl.glEnd();
				}
			}
		}
		else if(normalMode == 1)
		{
			/*
			 * Average normals : good approximation
			 * We can see the difference for models with low precision (low resolutions)
			 */
			
			n[0] = new Vector2f(1.0f, 0.0f);
			for(int i = 1; i < n.length-1; i++)
			{
				r1 = ry[i-1].getX();
				y1 = ry[i-1].getY();
				r2 = ry[i+1].getX();
				y2 = ry[i+1].getY();

				n[i] = VectorMath.normalize(new Vector2f(y2-y1, r1-r2));
			}
			n[n.length-1] = n[0];
			
			//Show normal vectors
			if(DEBUG_MODE)
			{
				for(int i = 1; i < ry.length; i++)
				{
					r1 = ry[i].getX();
					y1 = ry[i].getY();

					if(i == n.length-1)
						gl.glColor3f(0.0f, 1.0f, 1.0f);
					
					gl.glBegin(GL.GL_LINES);
					gl.glVertex3f(r1, y1, 0.0f);
					gl.glVertex3f(r1+n[i].getX(), y1+n[i].getY(), 0.0f);
					gl.glEnd();
					
					gl.glColor3f(1.0f, 1.0f, 1.0f);
				}

			}
		}
		
					/*draw multiple time the elementary geometry*/
		
		//NbVertex = waves*precTeta*precY*4
		
		for(int oscY = 0; oscY < waves; oscY++)
		{
			for(int i = 0; i < precTeta; i++)
			{
				teta = i*TWO_PI/precTeta;
				
				gl.glBegin(GL.GL_QUADS);
					for(int j = 0; j < ry.length-1; j++)
					{
						r1 = ry[j].getX();
						y1 = ry[j].getY()+oscY*h;
						r2 = ry[j+1].getX();
						y2 = ry[j+1].getY()+oscY*h;

						float cosT = (float)Math.cos(teta);
						float cosDT = (float)Math.cos(teta+dTeta);
						float sinT = (float)Math.sin(teta);
						float sinDT = (float)Math.sin(teta+dTeta);
						
						if(normalMode == 0 || normalMode == 1)
						{
							//Per vertex normal
							gl.glNormal3f(n[j].getX()*cosT,    n[j].getY(),   n[j].getX()*sinT);
							gl.glVertex3f(r1*cosT,  y1, r1*sinT);
							gl.glNormal3f(n[j].getX()*cosDT,   n[j].getY(),   n[j].getX()*sinDT);
							gl.glVertex3f(r1*cosDT, y1, r1*sinDT);
							gl.glNormal3f(n[j+1].getX()*cosDT, n[j+1].getY(), n[j+1].getX()*sinDT); 
							gl.glVertex3f(r2*cosDT, y2, r2*sinDT);
							gl.glNormal3f(n[j+1].getX()*cosT,  n[j+1].getY(), n[j+1].getX()*sinT);
							gl.glVertex3f(r2*cosT,  y2, r2*sinT);
						}
						else
						{
							//Calculate normals : the 4 vertex are on the same plane
							Vector3f P1 = new Vector3f(r1*cosT,  y1, r1*sinT);
							Vector3f P2 = new Vector3f(r1*cosDT, y1, r1*sinDT);
							Vector3f P3 = new Vector3f(r2*cosT,  y2, r2*sinT);
//							Vector3D P4 = new Vector3D(r2*cosDT, y2, r2*sinDT);		//Don't need
	
							Vector3f V1 = VectorMath.subtract(P3, P1);
							Vector3f V2 = VectorMath.subtract(P2, P1);
							Vector3f N1 = VectorMath.normalize(VectorMath.crossProduct(V1, V2));
	
							//Per face normal
							gl.glNormal3f(N1.getX(), N1.getY(), N1.getZ());
							gl.glVertex3f(r1*cosT,  y1, r1*sinT);
							gl.glVertex3f(r1*cosDT, y1, r1*sinDT);
							gl.glVertex3f(r2*cosDT, y2, r2*sinDT);
							gl.glVertex3f(r2*cosT,  y2, r2*sinT);
						}
					}
				gl.glEnd();
			}
		}
		gl.glTranslatef(-posX, -posY, -posZ);
	}

	private void setMaterial(int spring){
		if(spring==1){
			gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, no_mat);
			gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_diffuse);
			gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, mat_specular);
			gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, high_shininess);
			gl.glMaterialfv(GL.GL_FRONT, GL.GL_EMISSION, no_mat);
		}
		if(spring==2){
			gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, mat_ambient_color);
        	gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_diffuse);
        	gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, mat_specular);
        	gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, high_shininess);
        	gl.glMaterialfv(GL.GL_FRONT, GL.GL_EMISSION, no_mat);
		}
		if(spring==3){
			gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, no_mat);
        	gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_diffuse);
        	gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, mat_specular);
        	gl.glMaterialf(GL.GL_FRONT, GL.GL_SHININESS, high_shininess);
        	gl.glMaterialfv(GL.GL_FRONT, GL.GL_EMISSION, mat_ambient_color);
		}
	}
	public void draw(float[] lightPos,int spring) {
		 gl.glPushMatrix();
	       	gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPos);
	        	gl.glEnable(GL.GL_LIGHTING);
	        	
	        	setMaterial(spring);
		    	drawSpring();
	    	 gl.glDisable(GL.GL_LIGHTING);
	    gl.glPopMatrix();
		
	}
	
}
