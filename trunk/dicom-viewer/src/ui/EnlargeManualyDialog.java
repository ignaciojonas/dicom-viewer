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
public class EnlargeManualyDialog extends javax.swing.JDialog {
	
	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private JLabel jLabel1;
	private JButton jButton1;
	
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
	private JButton jButtonClean;
	private JButton jButtonPreview;
	private JButton jButtonDelete;
	private JButton jButtonOpen;
	private JPanel jPanel2;
	private JLabel jLabel6;
	private JPanel jPanel1;

	private JSpinner jSpinner1;

	private JSpinner jSpinner2;
	private JLabel jLabel2;
	private HandImage handImage;
	private JButton jButton2;
	private JButton jButtonOK;
	private AllCirclesOnScreen allCircles;
	
	public EnlargeManualyDialog(JFrame frame,HandImage handImage) {
		super(frame);
		this.setTitle("Enlarge Manualy");
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
				jLabel1.setText("Max. Distance: ");
				jLabel1.setBounds(12, 23, 78, 16);
			}
			{
				jSpinner1 = new JSpinner();
				getContentPane().add(jSpinner1);
				jSpinner1.setValue(ImagesData.MAX_DISTANCE);
				jSpinner1.setBounds(120, 21, 39, 19);
							}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2);
				jLabel2.setText("Tolerance Distance: ");
				jLabel2.setBounds(12, 43, 111, 40);
			}
			{
 
					
				jSpinner2 = new JSpinner();
				getContentPane().add(jSpinner2);
				jSpinner2.setValue(ImagesData.MAX_DISTANCE_NEIG);
				jSpinner2.setBounds(120, 53, 39, 19);
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
				jButton1 = new JButton();
				getContentPane().add(jButton1);
				jButton1.setText("Apply");
				jButton1.setBounds(64, 78, 68, 21);
				jButton1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton1ActionPerformed(evt);
					}
				});
			}
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1);
				jPanel1.setBounds(2, 11, 188, 98);
				jPanel1.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
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
				jButtonOpen = new JButton();
				getContentPane().add(jButtonOpen);
				jButtonOpen.setText("Open Circle");
				jButtonOpen.setBounds(204, 142, 112, 23);
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
				jButtonDelete.setBounds(204, 204, 112, 23);
				jButtonDelete.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/deleteCircle.gif")));
				jButtonDelete.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jButtonDeleteMouseClicked(evt);
					}
				});
			}
			{
				jButtonPreview = new JButton();
				getContentPane().add(jButtonPreview);
				jButtonPreview.setText("Preview");
				jButtonPreview.setBounds(22, 142, 71, 23);
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
				jButtonClean.setBounds(103, 142, 59, 23);
				jButtonClean.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jButtonCleanMouseClicked(evt);
					}
				});
			}
			{
				jButtonOK = new JButton();
				getContentPane().add(jButtonOK);
				jButtonOK.setBounds(73, 171, 50, 38);
				jButtonOK.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/forward.gif")));
				jButtonOK.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jButtonOKMouseClicked(evt);
					}
				});
			}
			{
				jButton2 = new JButton();
				getContentPane().add(jButton2);
				jButton2.setText("Save Circle");
				jButton2.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/savecircle.gif")));
				jButton2.setBounds(204, 175, 112, 23);
				jButton2.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jButton2MouseClicked(evt);
					}
				});
			}
			this.setResizable(false);
			this.setLocation(850, 100);
			this.setSize(363, 265);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jButton1ActionPerformed(ActionEvent evt) {

		ImagesData.MAX_DISTANCE=((Integer) jSpinner1.getValue());
		ImagesData.MAX_DISTANCE_NEIG=((Integer) jSpinner2.getValue());

		
	
	}
	
	private void jPanel1MouseClicked(MouseEvent evt) {
		new DialogColor(this,normalColorPanel);
	}
	private void enlargeColorPanelMouseClicked(MouseEvent evt) {
		new DialogColor(this,enlargeColorPanel);
	}
	private void reduceColorPanelMouseClicked(MouseEvent evt) {
		new DialogColor(this,reduceColorPanel);
	}
	private void initColorPanelMouseClicked(MouseEvent evt) {
		new DialogColor(this,initColorPanel);
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
		}
	}
	
	private void jButtonPreviewMouseClicked(MouseEvent evt) {
		handImage.setImage();
		
		handImage.previewCircle(handImage.getIndex(),initColorPanel.getBackground(),enlargeColorPanel.getBackground(),reduceColorPanel.getBackground(),normalColorPanel.getBackground());
		
	}
	
	private void jButtonCleanMouseClicked(MouseEvent evt) {
		handImage.setImage();
	}
	
	private void jButtonOKMouseClicked(MouseEvent evt) {
		handImage.enlargeCircle(initColorPanel.getBackground(),enlargeColorPanel.getBackground(),reduceColorPanel.getBackground(),normalColorPanel.getBackground());
		handImage.nextImage();
		handImage.drawInitCircle();
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
	


}
