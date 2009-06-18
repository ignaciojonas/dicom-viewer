package ui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import data.ImagesData;
import data.io.LoadFile;
import draw2D.AllCirclesOnScreen;
import draw2D.filtering.MeanFilter;
import draw2D.filtering.MedianFilter;


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
	public Color normalColor = Color.WHITE ;
	
	private JPanel initColorPanel;
	public Color initColor = Color.RED ;
	
	private JPanel enlargeColorPanel;
	public Color enlargeColor = Color.GREEN ;
	
	private JPanel reduceColorPanel;
	public Color reduceColor = Color.CYAN ;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JButton jButtonDelete;
	private JButton jButtonOpen;
	private JPanel jPanel2;
	private JLabel jLabel6;
	private JPanel jPanel1;

	private JSpinner jSpinner1;

	private JSpinner jSpinner2;
	private JLabel jLabel2;
	private HandImage handImage;
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
				jButton1 = new JButton();
				getContentPane().add(jButton1);
				jButton1.setText("Apply");
				jButton1.setBounds(22, 149, 59, 22);
				jButton1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton1ActionPerformed(evt);
					}
				});
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
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1);
				jPanel1.setBounds(2, 11, 188, 70);
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
				jPanel2.setBounds(200, 13, 121, 120);
			}
			{
				jButtonOpen = new JButton();
				getContentPane().add(jButtonOpen);
				jButtonOpen.setText("Open Circle");
				jButtonOpen.setBounds(91, 152, 87, 23);
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
				jButtonDelete.setBounds(194, 152, 89, 23);
				jButtonDelete.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jButtonDeleteMouseClicked(evt);
					}
				});
			}
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setSize(540, 339);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jButton1ActionPerformed(ActionEvent evt) {

		ImagesData.MAX_DISTANCE=((Integer) jSpinner1.getValue());
		ImagesData.MAX_DISTANCE_NEIG=((Integer) jSpinner2.getValue());

		
	
	}
	
	private void jPanel1MouseClicked(MouseEvent evt) {
		new DialogColor(this,normalColor,normalColorPanel);
	}
	private void enlargeColorPanelMouseClicked(MouseEvent evt) {
		new DialogColor(this,enlargeColor,enlargeColorPanel);
	}
	private void reduceColorPanelMouseClicked(MouseEvent evt) {
		new DialogColor(this,reduceColor,reduceColorPanel);
	}
	private void initColorPanelMouseClicked(MouseEvent evt) {
		new DialogColor(this,initColor,initColorPanel);
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

}
