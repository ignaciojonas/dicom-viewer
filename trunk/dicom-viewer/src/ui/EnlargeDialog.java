package ui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import data.ImagesData;
import data.VisualData;
import draw2D.StaticInitCircle;
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
public class EnlargeDialog extends javax.swing.JDialog {
	
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
	private JLabel jLabel5;
	private JSpinner jSpinner3;
	private JLabel jLabel7;
	private JPanel normalColorPanel;
	private JPanel enlargeColorPanel;
	private JPanel reduceColorPanel;
	private JPanel initColorPanel;
	

	public Color normalColor = VisualData.normal ;
	
	
	public Color initColor = VisualData.initColor;
	

	public Color enlargeColor = VisualData.enlargeColor ;
	
	
	public Color reduceColor = VisualData.reduceColor;
	
	
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel6;
	private JPanel jPanel2;
	private JSpinner jSpinner1;

	private JSpinner jSpinner2;
	private JLabel jLabel2;
	public EnlargeDialog(JFrame frame) {
		super(frame);
		this.setTitle("Enlarge Set");
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
				jButton1.setBounds(135, 147, 83, 22);
				jButton1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/configenlarge.gif")));
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
				jSpinner1.setBounds(120, 21, 39, 21);
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
				jSpinner2.setBounds(120, 53, 39, 22);
			}
			{
				jLabel6 = new JLabel();
				getContentPane().add(jLabel6);
				jLabel6.setText("Normal Color:");
				jLabel6.setBounds(213, 106, 80, 14);
			}
			{
				jLabel5 = new JLabel();
				getContentPane().add(jLabel5);
				jLabel5.setText("Reduce Color:");
				jLabel5.setBounds(210, 79, 80, 14);
			}
			{
				jLabel4 = new JLabel();
				getContentPane().add(jLabel4);
				jLabel4.setText("Init Color:");
				jLabel4.setBounds(229, 23, 80, 14);
			}
			{
				jLabel3 = new JLabel();
				getContentPane().add(jLabel3);
				jLabel3.setText("Enlarge Color:");
				jLabel3.setBounds(210, 51, 80, 14);
			}
			{
				initColorPanel = new JPanel();
				getContentPane().add(initColorPanel);
				initColorPanel.setBackground(this.initColor);
				initColorPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				initColorPanel.setBounds(283, 19, 23, 20);
				initColorPanel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						initColorPanelMouseClicked(evt);
					}
				});
			}
			{
				reduceColorPanel = new JPanel();
				getContentPane().add(reduceColorPanel);
				reduceColorPanel.setBackground(this.reduceColor);
				reduceColorPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				reduceColorPanel.setBounds(283, 76, 23, 20);
				reduceColorPanel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						reduceColorPanelMouseClicked(evt);
					}
				});
			}
			{
				enlargeColorPanel = new JPanel();
				getContentPane().add(enlargeColorPanel);
				enlargeColorPanel.setBackground(this.enlargeColor);
				enlargeColorPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				enlargeColorPanel.setBounds(283, 48, 23, 20);
				enlargeColorPanel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						enlargeColorPanelMouseClicked(evt);
					}
				});
			}
			{
				normalColorPanel = new JPanel();
				getContentPane().add(normalColorPanel);
				normalColorPanel.setBackground(this.normalColor);
				normalColorPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
				normalColorPanel.setBounds(283, 104, 23, 20);
				normalColorPanel.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						jPanel1MouseClicked(evt);
					}
				});
			}
			{
				jPanel2 = new JPanel();
				getContentPane().add(jPanel2);
				jPanel2.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				jPanel2.setBounds(184, 16, 137, 120);
			}
			{
				jLabel7 = new JLabel();
				getContentPane().add(jLabel7);
				jLabel7.setText("Reduce Circle: ");
				jLabel7.setBounds(12, 72, 111, 40);
			}
			{
				jSpinner3 = new JSpinner();
				getContentPane().add(jSpinner3);
				
				jSpinner3.setValue(ImagesData.REDUCE);
				jSpinner3.setBounds(120, 82, 39, 22);
			}
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setSize(347, 226);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jButton1ActionPerformed(ActionEvent evt) {

		ImagesData.MAX_DISTANCE=((Integer) jSpinner1.getValue());
		ImagesData.MAX_DISTANCE_NEIG=((Integer) jSpinner2.getValue());
		ImagesData.REDUCE = ((Integer) jSpinner3.getValue());
		VisualData.enlargeColor = enlargeColorPanel.getBackground();
		VisualData.reduceColor = reduceColorPanel.getBackground();
		VisualData.normal = normalColorPanel.getBackground();
		VisualData.initColor = initColorPanel.getBackground();
	
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

}
