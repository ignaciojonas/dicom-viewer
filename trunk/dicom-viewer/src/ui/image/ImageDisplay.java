package ui.image;



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.dcm4che.imageio.plugins.DcmImageReadParam;

import data.ImagesData;

import ui.FFilter;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ImageDisplay extends JPanel {


	private static final long serialVersionUID = 8990314740702549979L;
	
    private JFrame frame = null;

   public ImageDisplay(Vector<BufferedImage> images,int index){
	   int aux = index+1;
		frame = new JFrame("Image Viewer - Image "+aux);
	   	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	   	frame.setIconImage(new ImageIcon("images/new_win.gif").getImage());
	   	frame.setResizable(false);
	   	displayImage(images,index);
   }
    
    private void displayImage( Vector<BufferedImage> images, int index){ 
    	    	
    		JPanel p = null;
			try {
				p = new ImageBox(images.get(index));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		frame.getContentPane().add(p);
    		frame.pack();
    		frame.setSize(Math.min(frame.getWidth(),1024),Math.min(frame.getHeight(),768));
    		frame.setVisible(true);

    	

    }
 
    
  
}