package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import data.ImagesData;
import draw2D.filtering.MeanFilter;
import draw2D.filtering.RegionGrowing;
import draw2D.filtering.rg.Criterio;
import draw2D.filtering.rg.CriterioRGEntorno;
import draw2D.filtering.rg.CriterioRGGradiente;
import draw2D.filtering.rg.CriterioRGSimple;



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
public class RGDialog extends javax.swing.JDialog {
	
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
	private JSpinner jSpinner1;


	
	private JTabbedPane tabbedPane1;
	private JTextField jTextFieldK;
	private JButton jButton2;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JPanel jPanel2;
	private JSpinner jSpinnerMaxG;
	private JPanel jPanel1;
	private JTextField jTextFieldPG;
	private JCheckBox jCheckBoxGradiente;
	private JSpinner jSpinnerRadioEntorno;
	private JCheckBox jCheckBoxRGEntorno;
	private JCheckBox jCheckBoxRGSimple;
	private HandImage handImageFiltered;
	private HandImage handImage;
	public RGDialog(JFrame frame, JTabbedPane tabbedPane1, HandImage handImageFiltered,HandImage handImage) {
		super(frame);
		this.setTitle("RG Set");
		this.handImageFiltered=handImageFiltered;
		this.handImage=handImage;
		this.tabbedPane1=tabbedPane1;
		initGUI();
	}
	
	private void initGUI() {
		try {
			getContentPane().setLayout(null);
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1);
				jLabel1.setText("Radio: ");
				jLabel1.setBounds(10, 34, 40, 16);
			}
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1);
				jButton1.setText("Apply");
				jButton1.setBounds(164, 104, 59, 23);
				jButton1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton1ActionPerformed(evt);
					}
				});
			}
			{
				jSpinner1 = new JSpinner();
				getContentPane().add(jSpinner1);
				jSpinner1.setValue(3);
				jSpinner1.setBounds(56, 32, 39, 20);
							}
			{
				jCheckBoxRGSimple = new JCheckBox();
				getContentPane().add(jCheckBoxRGSimple);
				jCheckBoxRGSimple.setText("Simple RG");
				jCheckBoxRGSimple.setBounds(217, 12, 105, 23);
				jCheckBoxRGSimple.setSelected(true);
			}
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1);
				jPanel1.setBounds(4, 10, 188, 88);
				jPanel1.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				jPanel1.setOpaque(false);
			}
			{
				jCheckBoxRGEntorno = new JCheckBox();
				getContentPane().add(jCheckBoxRGEntorno);
				jCheckBoxRGEntorno.setText("Arround RG");
				jCheckBoxRGEntorno.setBounds(217, 39, 105, 23);
				jCheckBoxRGEntorno.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jCheckBoxRGEntornoActionPerformed(evt);
					}
				});
			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2);
				jLabel2.setText("K:");
				jLabel2.setBounds(128, 35, 10, 14);
			}
			{
				jSpinnerRadioEntorno = new JSpinner();
				getContentPane().add(jSpinnerRadioEntorno);
				jSpinnerRadioEntorno.setValue(3);
				jSpinnerRadioEntorno.setEnabled(false);
				jSpinnerRadioEntorno.setBounds(324, 40, 41, 19);
			}
			{
				jCheckBoxGradiente = new JCheckBox();
				getContentPane().add(jCheckBoxGradiente);
				jCheckBoxGradiente.setText("Gradient RG");
				jCheckBoxGradiente.setBounds(217, 67, 97, 23);
				jCheckBoxGradiente.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jCheckBoxGradienteActionPerformed(evt);
					}
				});
			}
			{
				jTextFieldK = new JTextField();
				getContentPane().add(jTextFieldK);
				jTextFieldK.setText("1.5");
				jTextFieldK.setBounds(142, 32, 42, 19);
			}
			{
				jTextFieldPG = new JTextField();
				getContentPane().add(jTextFieldPG);
				jTextFieldPG.setText("0.1");
				jTextFieldPG.setEnabled(false);
				jTextFieldPG.setBounds(355, 68, 39, 21);
			}
			{
				jLabel3 = new JLabel();
				getContentPane().add(jLabel3);
				jLabel3.setText("PG:");
				jLabel3.setBounds(324, 68, 21, 20);
			}
			{
				jButton2 = new JButton();
				getContentPane().add(jButton2);
				jButton2.setText("Clear Seeds");
				jButton2.setBounds(47, 62, 111, 23);
				jButton2.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/clearSeeds.gif")));
				jButton2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton2ActionPerformed(evt);
					}
				});
			}
			{
				jLabel4 = new JLabel();
				getContentPane().add(jLabel4);
				jLabel4.setText("MaxG:");
				jLabel4.setBounds(404, 68, 33, 21);
			}
			{
				jSpinnerMaxG = new JSpinner();
				getContentPane().add(jSpinnerMaxG);
				jSpinnerMaxG.setValue(254);
				jSpinnerMaxG.setEnabled(false);
				jSpinnerMaxG.setBounds(441, 68, 48, 21);
			}
			{
				jPanel2 = new JPanel();
				getContentPane().add(jPanel2);
				jPanel2.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
				jPanel2.setOpaque(false);
				jPanel2.setBounds(204, 10, 295, 88);
			}
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setSize(515, 160);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jButton1ActionPerformed(ActionEvent evt) {

		boolean error=false;
		int radio = ((Integer) jSpinner1.getValue()).intValue();
		int radioEntorno = ((Integer) jSpinnerRadioEntorno.getValue()).intValue();
		int maxG = ((Integer) jSpinnerMaxG.getValue()).intValue();;
		double K = 0;
		double pg=0;
		try{
			 K =Double.parseDouble(jTextFieldK.getText());}
		catch (Exception e){
			JOptionPane.showMessageDialog(this, "K must be double", "Error", JOptionPane.WARNING_MESSAGE);
			error=true;
		}
		try{
			if ((pg>=0)&&(pg<=1))
				pg=Double.parseDouble(jTextFieldPG.getText());
			else{
				JOptionPane.showMessageDialog(this, "PG must be double between 0 and 1", "Error", JOptionPane.WARNING_MESSAGE);
				error=true;
			}
		}catch(Exception r){
			JOptionPane.showMessageDialog(this, "PG must be double between 0 and 1", "Error", JOptionPane.WARNING_MESSAGE);
			error=true;
		}

		
		if (!error){
			Vector <Criterio> criterios =new Vector<Criterio>();
			
			if (jCheckBoxRGSimple.isSelected())
				criterios.add(new CriterioRGSimple(radio,K));
			if (jCheckBoxRGEntorno.isSelected())	
				criterios.add(new CriterioRGEntorno(radio,K,radioEntorno));
			if (jCheckBoxGradiente.isSelected())	
				criterios.add(new CriterioRGGradiente(radio,K,pg,maxG));
			
			this.tabbedPane1.setSelectedIndex(1);
			
			RegionGrowing rG = new RegionGrowing(this.handImage,this.handImageFiltered,ImagesData.imagesB,criterios);
			rG.start();
		}
	}
	
	private void jCheckBoxRGEntornoActionPerformed(ActionEvent evt) {
		jSpinnerRadioEntorno.setEnabled(jCheckBoxRGEntorno.isSelected());
	}
	
	private void jButton2ActionPerformed(ActionEvent evt) {
		handImage.clearSeeds();
	}
	
	private void jCheckBoxGradienteActionPerformed(ActionEvent evt) {
		jTextFieldPG.setEnabled(jCheckBoxGradiente.isSelected());
		jSpinnerMaxG.setEnabled(jCheckBoxGradiente.isSelected());
	}

}
