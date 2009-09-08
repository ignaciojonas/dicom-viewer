package ui;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
public class EnlargeSnakesDialog extends javax.swing.JDialog {
	
	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JLabel jLabel1;

	private JPanel normalColorPanel;
	public Color normalColor = VisualData.normal ;
	
	private JPanel initColorPanel;
	public Color initColor = VisualData.initColor;
	
	private JPanel enlargeColorPanel;
	public Color enlargeColor = VisualData.enlargeColor ;
	
	private JPanel reduceColorPanel;
	public Color reduceColor = VisualData.reduceColor;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JSpinner jSpinner3;
	private JLabel jLabel8;
	private JLabel jLabel7;
	private JButton jButtonClean;
	private JButton jButtonPreview;
	private JPanel jPanel2;
	private JLabel jLabel6;

	private JSpinner jSpinner1;

	private JSpinner jSpinner2;
	private JLabel jLabel2;
	private HandImage handImage;
	private JSpinner jSpinner6;
	private JSpinner jSpinner5;
	private JSpinner jSpinner4;
	private JButton jButtonOK;
	private AllCirclesOnScreen allCircles;
	
	public EnlargeSnakesDialog(JFrame frame,HandImage handImage) {
		super(frame);
		this.setTitle("Snakes Enlarge");
		this.handImage = handImage;
		this.allCircles = handImage.getAllCircles();
		initGUI();
	}
	
	private void initGUI() {
		try {
			getContentPane().setLayout(null);
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1);
				jLabel1.setText("Strech:");
				jLabel1.setBounds(12, 23, 78, 16);
			}
			{
				jSpinner1 = new JSpinner();
				getContentPane().add(jSpinner1);
				jSpinner1.setValue(Snakes.BN_strech);
				jSpinner1.setBounds(64, 21, 40, 20);
							}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2);
				jLabel2.setText("Bend:");
				jLabel2.setBounds(12, 46, 111, 21);
			}
			{
 
					
				jSpinner2 = new JSpinner();
				getContentPane().add(jSpinner2);
				jSpinner2.setValue(Snakes.BN_bend);
				jSpinner2.setBounds(64, 45, 40, 20);
			}
			{
				normalColorPanel = new JPanel();
				getContentPane().add(normalColorPanel);
				normalColorPanel.setBounds(283, 104, 23, 20);
				normalColorPanel.setBackground(this.normalColor);
				normalColorPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				normalColorPanel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jPanel1MouseClicked(evt);
					}
				});
			}
			{
				enlargeColorPanel = new JPanel();
				getContentPane().add(enlargeColorPanel);
				enlargeColorPanel.setBounds(283, 48, 23, 20);
				enlargeColorPanel.setBackground(this.enlargeColor);
				enlargeColorPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				enlargeColorPanel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						enlargeColorPanelMouseClicked(evt);
					}
				});
			}
			{
				reduceColorPanel = new JPanel();
				getContentPane().add(reduceColorPanel);
				reduceColorPanel.setBounds(283, 76, 23, 20);
				reduceColorPanel.setBackground(this.reduceColor);
				reduceColorPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				reduceColorPanel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						reduceColorPanelMouseClicked(evt);
					}
				});
			}
			{
				initColorPanel = new JPanel();
				getContentPane().add(initColorPanel);
				initColorPanel.setBounds(283, 19, 23, 20);
				initColorPanel.setBackground(this.initColor);
				initColorPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				initColorPanel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						initColorPanelMouseClicked(evt);
					}
				});
			}
			{
				jLabel3 = new JLabel();
				getContentPane().add(jLabel3);
				jLabel3.setText("Enlarge Color:");
				jLabel3.setBounds(210, 51, 80, 14);
			}
			{
				jLabel4 = new JLabel();
				getContentPane().add(jLabel4);
				jLabel4.setText("Init Color:");
				jLabel4.setBounds(229, 23, 80, 14);
			}
			{
				jLabel5 = new JLabel();
				getContentPane().add(jLabel5);
				jLabel5.setText("Reduce Color:");
				jLabel5.setBounds(210, 79, 80, 14);
			}
			{
				jLabel6 = new JLabel();
				getContentPane().add(jLabel6);
				jLabel6.setText("Normal Color:");
				jLabel6.setBounds(213, 106, 80, 14);
			}
			{
				jPanel2 = new JPanel();
				getContentPane().add(jPanel2);
				jPanel2.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				jPanel2.setBounds(200, 11, 121, 120);
			}
			{
				jButtonPreview = new JButton();
				getContentPane().add(jButtonPreview);
				jButtonPreview.setText("Preview");
				jButtonPreview.setBounds(10, 190, 71, 23);
				jButtonPreview.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jButtonPreviewMouseClicked(evt);
					}
				});
			}
			{
				jButtonClean = new JButton();
				getContentPane().add(jButtonClean);
				jButtonClean.setText("Clean");
				jButtonClean.setBounds(91, 200, 59, 23);
				jButtonClean.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jButtonCleanMouseClicked(evt);
					}
				});
			}
			{
				jButtonOK = new JButton();
				getContentPane().add(jButtonOK);
				jButtonOK.setBounds(233, 169, 50, 38);
				jButtonOK.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/forward.gif")));
				jButtonOK.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jButtonOKMouseClicked(evt);
					}
				});
			}
			{
				jLabel7 = new JLabel();
				getContentPane().add(jLabel7);
				jLabel7.setText("Inflate:");
				jLabel7.setBounds(10, 73, 53, 14);
			}
			{
				jLabel8 = new JLabel();
				getContentPane().add(jLabel8);
				jLabel8.setText("Image:");
				jLabel8.setBounds(10, 98, 34, 14);
			}
			{
				
				jSpinner3 = new JSpinner();
				getContentPane().add(jSpinner3);
				jSpinner3.setValue(Snakes.BN_inflate);
				jSpinner3.setBounds(63, 70, 40, 20);
			}
			{
				
				jSpinner4 = new JSpinner();
				getContentPane().add(jSpinner4);
				jSpinner4.setValue(Snakes.BN_image);
				jSpinner4.setBounds(63, 95, 40, 20);
			}
			{
				
				jSpinner5 = new JSpinner();
				getContentPane().add(jSpinner5);
			
				jSpinner5.setValue(Snakes.BN_numSteps);
				jSpinner5.setBounds(63, 126, 40, 21);
			}
			{
				
				jSpinner6 = new JSpinner();
				getContentPane().add(jSpinner6);
				int a = (int) (Snakes.BN_timeStep*1000);
				jSpinner6.setValue(a);
				jSpinner6.setBounds(63, 158, 40, 21);
			}
			this.setResizable(false);
			this.setLocation(850, 100);
			this.setSize(363, 265);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jButton1ActionPerformed(ActionEvent evt) {

		
		jButtonPreviewMouseClicked(null);
		
	
	}
	
	private void jPanel1MouseClicked(MouseEvent evt) {
		new DialogColor(normalColorPanel);
	}
	private void enlargeColorPanelMouseClicked(MouseEvent evt) {
		new DialogColor(enlargeColorPanel);
	}
	private void reduceColorPanelMouseClicked(MouseEvent evt) {
		new DialogColor(reduceColorPanel);
	}
	private void initColorPanelMouseClicked(MouseEvent evt) {
		new DialogColor(initColorPanel);
	}
	
	private void jButtonDeleteMouseClicked(MouseEvent evt) {
		handImage.deleteCircle();
	}
	
	private void jButtonOpenMouseClicked(MouseEvent evt) {
		JFileChooser filechooser = new JFileChooser(new File("c:\\"));
		filechooser.setMultiSelectionEnabled(false);
		FFilter crlFilter; 
		File file;
		crlFilter = new FFilter("crl", "Circle Files");
		filechooser.addChoosableFileFilter(crlFilter);
		filechooser.setAcceptAllFileFilterUsed(true);
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			file=filechooser.getSelectedFile();
			LoadFile ld=new LoadFile(file.getAbsolutePath());
			handImage.setCirclePoints(ld.load());
			handImage.setImage();
			handImage.setImage();
		}
	}
	
	private void jButtonPreviewMouseClicked(MouseEvent evt) {
		Snakes.BN_strech = ((Integer) jSpinner1.getValue());
		Snakes.BN_bend = ((Integer) jSpinner2.getValue());
		Snakes.BN_inflate = ((Integer) jSpinner3.getValue());
		Snakes.BN_image= ((Integer) jSpinner4.getValue());
		Snakes.BN_numSteps= ((Integer) jSpinner5.getValue());
		Snakes.BN_timeStep= ((Integer) jSpinner6.getValue())/1000f;
		System.out.println(Snakes.BN_timeStep);
		Snakes s = new Snakes(handImage,handImage.getImage());
		s.start();
	}
	
	private void jButtonCleanMouseClicked(MouseEvent evt) {
		handImage.setImage();
	}
	
	private void jButtonOKMouseClicked(MouseEvent evt) {
		
	}
	
	


}
