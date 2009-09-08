package ui;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import data.ImagesData;
import data.VisualData;
import data.io.LoadFile;
import data.io.SaveFile;
import draw2D.AllCirclesOnScreen;
import draw2D.CircleOnScreen;
import draw2D.InitCircleOnScreen;
import draw2D.NormalOnScreen;
import draw2D.filtering.MeanFilter;
import draw2D.filtering.MedianFilter;
import draw2D.snakes.Snakes;
import draw3D.Line;
import draw3D.Mesh;


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
public class Packages extends javax.swing.JDialog {
	
	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JLabel jLabel1;

	public Color normalColor = VisualData.normal ;

	public Color initColor = VisualData.initColor;

	public Color enlargeColor = VisualData.enlargeColor ;

	public Color reduceColor = VisualData.reduceColor;
	private JButton jButtonPreview;

	private JSpinner jSpinner1;

	private HandImage handImage;
	private JButton jButtonOK;
	
	int cantP = Mesh.endSequence - Mesh.startSequence;
	
	public Packages(JFrame frame,HandImage handImage) {
		super(frame);
		this.setTitle("Packages");
		this.handImage = handImage;
		
		initGUI();
	}
	
	private void initGUI() {
		try {
			getContentPane().setLayout(null);
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1);
				jLabel1.setText("Package Number ("+cantP+"):");
				jLabel1.setBounds(12, 23, 112, 16);
			}
			{
				jSpinner1 = new JSpinner();
				getContentPane().add(jSpinner1);
				jSpinner1.setValue(1);
				jSpinner1.setBounds(119, 23, 42, 17);
				jSpinner1.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent evt) {
						jSpinner1StateChanged(evt);
					}
				});
							}
			{
				jButtonPreview = new JButton();
				getContentPane().add(jButtonPreview);
				jButtonPreview.setText("Preview");
				jButtonPreview.setBounds(104, 63, 71, 23);
				jButtonPreview.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jButtonPreviewMouseClicked(evt);
					}
				});
			}
			{
				jButtonOK = new JButton();
				getContentPane().add(jButtonOK);
				jButtonOK.setBounds(244, 16, 82, 23);
				jButtonOK.setText("Reload");
				jButtonOK.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jButtonOKMouseClicked(evt);
					}
				});
			}
			this.setResizable(false);
			this.setLocation(850, 100);
			this.setSize(373, 144);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	

	private int pack =0;
	private void jButtonPreviewMouseClicked(MouseEvent evt) {
		
		
		
		pack = ((Integer) jSpinner1.getValue())-1;
		handImage.startWorkPackage(pack);
		
		Vector<BufferedImage> temp= new Vector<BufferedImage>();
		int i=Mesh.startSequence + pack;
		int size = handImage.getSizeAllImagesB();
		while(i< size){
			temp.add(handImage.getFromAllImagesB(i));
			i+=cantP;
		}
		handImage.setImagesB(temp);
		handImage.setImage();
	}
	
	
	private void jButtonOKMouseClicked(MouseEvent evt) {
		handImage.stopWorkPackage();
		handImage.reloadImageB();
		handImage.setImage();
	}
	
	private void jSpinner1StateChanged(ChangeEvent evt) {
		int a = ((Integer) jSpinner1.getValue());
		if(a > cantP)
			jSpinner1.setValue(new Integer(cantP));
		else
			if(a<1)
				jSpinner1.setValue(new Integer(1));
	}

}
