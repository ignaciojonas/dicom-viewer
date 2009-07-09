package ui;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
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
import javax.swing.JToggleButton;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.security.auth.module.JndiLoginModule;

import data.ImagesData;
import data.VisualData;
import data.io.LoadFile;
import data.io.SaveFile;
import draw2D.AllCirclesOnScreen;
import draw2D.CircleOnScreen;
import draw2D.InitCircleOnScreen;
import draw2D.NormalOnScreen;
import draw2D.StaticInitCircle;
import draw2D.filtering.MeanFilter;
import draw2D.filtering.MedianFilter;
import draw3D.Line;
import draw3D.OpenGLCanvas;
import draw3D.Setup3D;


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
public class EditInitcircleDialog extends javax.swing.JDialog {
	
	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Color normalColor = VisualData.normal ;
	
	private JPanel initColorPanel;
	public Color initColor = VisualData.initColor;

	public Color enlargeColor = VisualData.enlargeColor ;

	public Color reduceColor = VisualData.reduceColor;
	private JLabel jLabel4;
	private JLabel jLabel5;
	
	private JPanel jPanel1;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JSpinner jSpinnerNF;
	private JSpinner jSpinnerN;
	private JLabel jLabel1;
	private JSpinner jSpinner4;
	private JButton jButtonDelete;
	private JButton jButtonOpen;
	private JButton jButtonA;
	private JPanel jPanel2;

	private HandImage handImage;
	private JButton jButton2;

	
	public EditInitcircleDialog(JFrame frame,HandImage handImage) {
		super(frame);
		this.setTitle("Set Init Circle");
		this.handImage = handImage;
		
		initGUI();
	}
	
	private void initGUI() {
		try {
			getContentPane().setLayout(null);
			{
				initColorPanel = new JPanel();
				getContentPane().add(initColorPanel);
				initColorPanel.setBounds(99, 11, 23, 20);
				initColorPanel.setBackground(this.initColor);
				initColorPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				initColorPanel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						initColorPanelMouseClicked(evt);
					}
				});
			}
			{
				jLabel4 = new JLabel();
				getContentPane().add(jLabel4);
				jLabel4.setText("Init Color:");
				jLabel4.setBounds(10, 14, 80, 14);
			}
			{
				jButtonOpen = new JButton();
				getContentPane().add(jButtonOpen);
				jButtonOpen.setText("Open Circle");
				jButtonOpen.setBounds(10, 80, 127, 23);
				jButtonOpen.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/opencircle.gif")));
				jButtonOpen.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jButtonOpenMouseClicked(evt);
					}
				});
			}
			{
				jButtonDelete = new JButton();
				getContentPane().add(jButtonDelete);
				jButtonDelete.setText("DeleteCircle");
				jButtonDelete.setBounds(176, 109, 127, 23);
				jButtonDelete.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/deleteCircle.gif")));
				jButtonDelete.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jButtonDeleteMouseClicked(evt);
					}
				});
			}
			{
				jButton2 = new JButton();
				getContentPane().add(jButton2);
				jButton2.setText("Save Circle");
				jButton2.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/savecircle.gif")));
				jButton2.setBounds(10, 109, 127, 23);
				jButton2.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jButton2MouseClicked(evt);
					}
				});
			}
			{
				jButtonA = new JButton();
				getContentPane().add(jButtonA);
				jButtonA.setText("Apply");
				jButtonA.setBounds(108, 138, 86, 23);
				jButtonA.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/configInitC.gif")));
				jButtonA.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButtonAActionPerformed(evt);
					}
				});
			}
			{
				
				jSpinner4 = new JSpinner();
				getContentPane().add(jSpinner4);
				jSpinner4.setValue(StaticInitCircle.POINTSBETWEEN);
				jSpinner4.setBounds(98, 41, 39, 22);
			}
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1);
				jLabel1.setText("Points between:");
				jLabel1.setBounds(10, 44, 89, 14);
			}
			{
				jPanel2 = new JPanel();
				getContentPane().add(jPanel2);
				jPanel2.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				jPanel2.setBounds(4, 6, 140, 68);
			}
			{
				jSpinnerN = new JSpinner();
				getContentPane().add(jSpinnerN);
				jSpinnerN.setValue(StaticInitCircle.neighborhoodInvolved);
				jSpinnerN.setBounds(283, 42, 41, 23);
			}
			{
				jSpinnerNF = new JSpinner();
				getContentPane().add(jSpinnerNF);
				int a = (int) (StaticInitCircle.neighborhoodFact*10);
				jSpinnerNF.setValue(a);
				jSpinnerNF.setBounds(284, 11, 39, 24);
				jSpinnerNF.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent evt) {
						jSpinnerNFStateChanged(evt);
					}
				});
			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2);
				jLabel2.setText("Neighborhood Fact:");
				jLabel2.setBounds(161, 14, 103, 14);
			}
			{
				jLabel3 = new JLabel();
				getContentPane().add(jLabel3);
				jLabel3.setText("Neighborhood Involved:");
				jLabel3.setBounds(160, 46, 124, 14);
			}
			{
				jLabel5 = new JLabel();
				getContentPane().add(jLabel5);
				jLabel5.setText(" Move ");
				jLabel5.setBounds(190, -1, 40, 14);
				jLabel5.setOpaque(true);
				jLabel5.setFont(new java.awt.Font("Tahoma",1,11));
			}
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1);
				jPanel1.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				jPanel1.setBounds(153, 6, 178, 68);
			}
			{
				VisualData.jToggleButtonPC = new JToggleButton();
				getContentPane().add(VisualData.jToggleButtonPC);
				
				VisualData.jToggleButtonPC.setText(" Perfect Circle");
				VisualData.jToggleButtonPC.setBounds(176, 80, 127, 23);
				VisualData.jToggleButtonPC.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/DPC.gif")));
				
				
				VisualData.jToggleButtonPC.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jToggleButtonPCActionPerformed(evt);
					}
				});
			}
			this.setResizable(false);
			this.setLocation(850, 100);
			this.setSize(347, 197);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	private void jButton2MouseClicked(MouseEvent evt) {
		JFileChooser filechooser = new JFileChooser(new File("c:\\"));
		filechooser.setMultiSelectionEnabled(false);
		FFilter crlFilter; 
		File file;
		crlFilter = new FFilter("crl", "Circle Files");
		filechooser.addChoosableFileFilter(crlFilter);
		filechooser.setAcceptAllFileFilterUsed(true);
		filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (filechooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			file=filechooser.getSelectedFile();
			if(file.exists()){
				int response=JOptionPane.showConfirmDialog(null,"Overwrite existing file?","Confirm Overwrite",JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(response==JOptionPane.CANCEL_OPTION){
					return;
				}
			}
			SaveFile sv=new SaveFile(file.getAbsolutePath());
			sv.save(handImage.getInitCircle().getCirclePoints());
		}
	}
	
	private void jButtonDPCActionPerformed(ActionEvent evt) {
		StaticInitCircle.drawPC=true;
		handImage.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		
	}
	
	private void jButtonAActionPerformed(ActionEvent evt) {
		StaticInitCircle.POINTSBETWEEN = ((Integer) jSpinner4.getValue());
		double d = ((Integer) jSpinnerNF.getValue());
		StaticInitCircle.neighborhoodFact = d / 10;
		StaticInitCircle.neighborhoodInvolved = ((Integer) jSpinnerN.getValue());
		handImage.setInitCircleColor(initColorPanel.getBackground());
		handImage.setImage();
	}
	
	private void jSpinnerNFStateChanged(ChangeEvent evt) {
		int i = ((Integer) jSpinnerNF.getValue());
		if(i>10)
			jSpinnerNF.setValue(10);
		else if(i<0)
			jSpinnerNF.setValue(10);
	}
	
	private void jToggleButtonPCActionPerformed(ActionEvent evt) {
		StaticInitCircle.drawPC=true;
		handImage.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}

}
