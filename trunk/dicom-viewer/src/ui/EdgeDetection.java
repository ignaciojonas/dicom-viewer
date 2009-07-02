package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;


import data.ImagesData;
import draw2D.filtering.MeanFilter;
import draw2D.filtering.edge.CannyEdgeDetector;
import draw2D.filtering.edge.CannyEdgeDetectorThread;
import draw2D.filtering.edge.EdgeDetectionAlls;


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
public class EdgeDetection extends javax.swing.JDialog {
	
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
	float lowT;
	float hightT;

	
	private JTabbedPane tabbedPane1;
	private JSpinner jSpinner2;
	private JSpinner jSpinner1;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JPanel jPanel3;
	private JPanel jPanel2;
	private JLabel jLabel8;
	private JLabel jLabel7;
	private JLabel jLabel6;
	private JCheckBox jCheckBoxS;
	private JCheckBox jCheckBoxT;
	private JSpinner jSpinnerTV;
	private JLabel jLabel3;
	private JPanel jPanel1;
	private JCheckBox jCheckBox1;
	private JComboBox jComboBox1;
	private JRadioButton jRadioButtonSobel;
	private JRadioButton jRadioButtonP;
	private JRadioButton jRadioButtonRC;
	private JRadioButton jRadioButtonSimple;
	private JRadioButton jRadioButtonCanny;
	private ButtonGroup buttonGroup1;
	private JLabel jLabel9;
	private JTextField jTextField2;
	private JTextField jTextField1;
	private JLabel jLabel2;
	private HandImage handImageFiltered;
	public EdgeDetection(JFrame frame, JTabbedPane tabbedPane1, HandImage handImageFiltered) {
		super(frame);
		this.setTitle("Edge Detection Set");
		this.handImageFiltered=handImageFiltered;
		this.tabbedPane1=tabbedPane1;
		initGUI();
	}
	
	private void initGUI() {
		try {
			getContentPane().setLayout(null);
			buttonGroup1 = new ButtonGroup();
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1);
				jLabel1.setText("Low Threshold: ");
				jLabel1.setBounds(10, 46, 90, 18);
			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2);
				jLabel2.setText("Hight Threshold: ");
				jLabel2.setBounds(10, 73, 90, 16);
			}
			{
				jTextField1 = new JTextField();
				getContentPane().add(jTextField1);
				jTextField1.setText("0.5");
				jTextField1.setBounds(132, 45, 57, 20);
			}
			{
				jTextField2 = new JTextField();
				getContentPane().add(jTextField2);
				jTextField2.setText("1");
				jTextField2.setBounds(132, 71, 55, 19);
			}
			{
				jSpinner1 = new JSpinner();
				getContentPane().add(jSpinner1);
				jSpinner1.setValue(16);
				jSpinner1.setBounds(132, 119, 55, 19);
			}
			{
				jSpinner2 = new JSpinner();
				getContentPane().add(jSpinner2);
				
				jSpinner2.setValue(2);
				jSpinner2.setBounds(132, 97, 55, 18);
			}
			{
				jLabel4 = new JLabel();
				getContentPane().add(jLabel4);
				jLabel4.setText("GaussianKernelWidth:");
				jLabel4.setBounds(10, 123, 111, 14);
			}
			{
				jLabel5 = new JLabel();
				getContentPane().add(jLabel5);
				jLabel5.setText("GaussianKernelRadius:");
				jLabel5.setBounds(10, 99, 122, 14);
			}
			{
				jCheckBox1 = new JCheckBox();
				getContentPane().add(jCheckBox1);
				jCheckBox1.setText("Contrast Normalized");
				jCheckBox1.setBounds(20, 148, 124, 23);
			}
			{
				jLabel3 = new JLabel();
				getContentPane().add(jLabel3);
				jLabel3.setText("  Canny Set");
				jLabel3.setBounds(20, 17, 71, 14);
				jLabel3.setOpaque(true);
				jLabel3.setFont(new java.awt.Font("Tahoma",1,11));
			}
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1);
				jPanel1.setBounds(6, 24, 199, 154);
				jPanel1.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
			}
			{
				
				jSpinnerTV = new JSpinner();
				getContentPane().add(jSpinnerTV);
				jSpinnerTV.setValue(50);
				jSpinnerTV.setBounds(344, 71, 56, 19);
			}
			{
				jCheckBoxT = new JCheckBox();
				getContentPane().add(jCheckBoxT);
				jCheckBoxT.setText("Thresholding");
				jCheckBoxT.setBounds(225, 95, 101, 23);
			}
			{
				jCheckBoxS = new JCheckBox();
				getContentPane().add(jCheckBoxS);
				jCheckBoxS.setText("Supression");
				jCheckBoxS.setBounds(225, 126, 77, 22);
			}
			{
				jLabel6 = new JLabel();
				getContentPane().add(jLabel6);
				jLabel6.setText("Threshold Val:");
				jLabel6.setBounds(225, 76, 71, 11);
			}
			{
				jLabel7 = new JLabel();
				getContentPane().add(jLabel7);
				jLabel7.setText("Gaussian Blur Intensity:");
				jLabel7.setBounds(225, 48, 119, 14);
			}
			{
				jLabel8 = new JLabel();
				getContentPane().add(jLabel8);
				jLabel8.setText("  Others Set");
				jLabel8.setFont(new java.awt.Font("Tahoma",1,11));
				jLabel8.setOpaque(true);
				jLabel8.setBounds(235, 17, 71, 14);
			}
			{
				jLabel9 = new JLabel();
				getContentPane().add(jLabel9);
				jLabel9.setText("  Algorithm");
				jLabel9.setFont(new java.awt.Font("Tahoma",1,11));
				jLabel9.setOpaque(true);
				jLabel9.setBounds(106, 189, 71, 15);
			}
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1);
				jButton1.setText("Apply");
				jButton1.setBounds(246, 256, 79, 22);
				jButton1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/filtroED.gif")));
				jButton1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton1ActionPerformed(evt);
					}
				});
			}
			{
				jPanel3 = new JPanel();
				getContentPane().add(jPanel3);
				getContentPane().add(getJComboBox1());
				{
					jPanel2 = new JPanel();
					getContentPane().add(jPanel2);
					jPanel2.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
					jPanel2.setBounds(214, 24, 196, 154);
				}
				jPanel3.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				jPanel3.setBounds(84, 197, 253, 136);
				jPanel3.setLayout(null);
				{
					jRadioButtonCanny = new JRadioButton();
					jPanel3.add(jRadioButtonCanny);
					jRadioButtonCanny.setText("Canny");
					jRadioButtonCanny.setSelected(true);
					jRadioButtonCanny.setBounds(12, 9, 130, 23);
					buttonGroup1.add(jRadioButtonCanny);
				}
				{
					jRadioButtonSimple = new JRadioButton();
					jPanel3.add(jRadioButtonSimple);
					jRadioButtonSimple.setText("Simple");
					jRadioButtonSimple.setBounds(12, 34, 78, 23);
					buttonGroup1.add(jRadioButtonSimple);
				}
				{
					jRadioButtonRC = new JRadioButton();
					jPanel3.add(jRadioButtonRC);
					jPanel3.add(getJRadioButtonP());
					jPanel3.add(getJRadioButtonSobel());
					jRadioButtonRC.setText("Roberts Cross");
					jRadioButtonRC.setBounds(12, 59, 93, 23);
					buttonGroup1.add(jRadioButtonRC);
				}
			}
			this.setResizable(false);
			this.setSize(435, 369);
			this.setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jButton1ActionPerformed(ActionEvent evt) {

		if(jRadioButtonCanny.isSelected()){
			int gaussianKernelWidth = (Integer)jSpinner1.getValue();
			float gaussianKernelRadius = (Integer)jSpinner2.getValue();
			boolean contrastNormalized = jCheckBox1.isSelected();
			
			CannyEdgeDetectorThread cE = new CannyEdgeDetectorThread(this.handImageFiltered,Float.parseFloat(jTextField1.getText()),Float.parseFloat(jTextField2.getText()), ImagesData.imagesB,gaussianKernelWidth,gaussianKernelRadius,contrastNormalized);
			
			cE.start();
		}else{
			int typeofgaussianblur = Integer.parseInt(new String((String) jComboBox1.getSelectedItem()));
		
			int thresholdval = (Integer)jSpinnerTV.getValue();
			boolean nonmaxsupenabled = jCheckBoxS.isSelected();
			boolean thresholdingenabled = jCheckBoxT.isSelected();
			int typeofoperator=0;
			if(jRadioButtonP.isSelected())
				typeofoperator = EdgeDetectionAlls.PREWITT;
			if(jRadioButtonRC.isSelected())
				typeofoperator = EdgeDetectionAlls.ROBERTSCROSS;
			if(jRadioButtonSimple.isSelected())
				typeofoperator = EdgeDetectionAlls.SIMPLE;
			if(jRadioButtonSobel.isSelected())
				typeofoperator = EdgeDetectionAlls.SOBEL3X3;
					
			
			EdgeDetectionAlls r = new EdgeDetectionAlls(handImageFiltered,typeofgaussianblur,typeofoperator,nonmaxsupenabled,thresholdingenabled,thresholdval, thresholdingenabled);
			r.start();
		}
		
		
		
	
		this.tabbedPane1.setSelectedIndex(1);
	}
	
	private ButtonGroup getButtonGroup1() {
		if(buttonGroup1 == null) {
			buttonGroup1 = new ButtonGroup();
		}
		
		

		
		
		return buttonGroup1;
	}
	
	private JRadioButton getJRadioButtonP() {
		if(jRadioButtonP == null) {
			jRadioButtonP = new JRadioButton();
			jRadioButtonP.setText("Prewitt");
			jRadioButtonP.setBounds(12, 84, 59, 23);
			buttonGroup1.add(jRadioButtonP);
		}
		return jRadioButtonP;
	}
	
	private JRadioButton getJRadioButtonSobel() {
		if(jRadioButtonSobel == null) {
			jRadioButtonSobel = new JRadioButton();
			jRadioButtonSobel.setText("Sobel");
			jRadioButtonSobel.setBounds(12, 109, 51, 23);
			buttonGroup1.add(jRadioButtonSobel);
		}
		return jRadioButtonSobel;
	}
	
	private JComboBox getJComboBox1() {
		if(jComboBox1 == null) {
			ComboBoxModel jComboBox1Model = 
				new DefaultComboBoxModel(
						new String[] { "0", "3" ,"5"});
			jComboBox1 = new JComboBox();
			jComboBox1.setModel(jComboBox1Model);
			jComboBox1.setBounds(344, 45, 56, 20);
		}
		return jComboBox1;
	}

}
