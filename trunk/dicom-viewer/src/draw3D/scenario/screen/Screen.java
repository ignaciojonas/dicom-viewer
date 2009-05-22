package draw3D.scenario.screen;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.util.Vector;
import net.java.games.jogl.GL;
import net.java.games.jogl.GLDrawable;
import net.java.games.jogl.util.GLUT;
import org.jouvieje.jogl.tools.SimpleShape;
import org.jouvieje.jogl.tools.ViewingTools;



public final class Screen {


	private static GL gl;
	private static GLDrawable glDrawable;
	
	
	
	public Screen(GLDrawable glDrawable) {
		this.glDrawable=glDrawable;
		this.gl=glDrawable.getGL();
		
	}
	

	
	
	 public static Vector getScreenResolutions()
	    {
	        Vector resolutions = new Vector();
	        DisplayMode adisplaymode[];
	        int j = (adisplaymode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayModes()).length;
	        for(int i = 0; i < j; i++)
	        {
	            DisplayMode display = adisplaymode[i];
	            int width = display.getWidth();
	            int height = display.getHeight();
	            Dimension d = new Dimension(width, height);
	            if(resolutions.indexOf(d) == -1)
	                resolutions.add(d);
	        }

	        return resolutions;
	    }
	 
	  public static DisplayMode searchDisplayMode(DisplayMode displayModes[], int requestedWidth, int requestedHeight, int requestedDepth, int requestedRefreshRate)
	    {
	        DisplayMode displayMode = searchDisplayModeImpl(displayModes, requestedWidth, requestedHeight, requestedDepth, requestedRefreshRate);
	        if(displayMode == null)
	        {
	            displayMode = searchDisplayModeImpl(displayModes, requestedWidth, requestedHeight, -1, -1);
	            if(displayMode == null)
	            {
	                displayMode = searchDisplayModeImpl(displayModes, requestedWidth, -1, -1, -1);
	                if(displayMode == null)
	                    displayMode = searchDisplayModeImpl(displayModes, -1, -1, -1, -1);
	            }
	        }
	        return displayMode;
	    }
	  private static DisplayMode searchDisplayModeImpl(DisplayMode displayModes[], int requestedWidth, int requestedHeight, int requestedDepth, int requestedRefreshRate)
	    {
	        DisplayMode displayModeToUse = null;
	        for(int i = 0; i < displayModes.length; i++)
	        {
	            DisplayMode displayMode = displayModes[i];
	            if((requestedWidth == -1 || displayMode.getWidth() == requestedWidth) && (requestedHeight == -1 || displayMode.getHeight() == requestedHeight) && (requestedHeight == -1 || displayMode.getRefreshRate() == requestedRefreshRate) && (requestedDepth == -1 || displayMode.getBitDepth() == requestedDepth))
	                displayModeToUse = displayMode;
	        }

	        return displayModeToUse;
	    }

	
	private GLUT glut = new GLUT();
	int font = GLUT.BITMAP_HELVETICA_18;
	



}
