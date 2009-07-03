package ui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerListModel;
import javax.swing.SwingUtilities;

import data.ImagesData;
import draw2D.filtering.MeanFilter;


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
public class MeanDialog extends javax.swing.JDialog {
	
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
	private HandImage handImageFiltered;
	public MeanDialog(JFrame frame, JTabbedPane tabbedPane1, HandImage handImageFiltered) {
		super(frame);
		this.setTitle("Mean Set");
		this.handImageFiltered=handImageFiltered;
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
				jLabel1.setBounds(12, 23, 40, 16);
			}
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1);
				jButton1.setText("Apply");
				jButton1.setBounds(12, 60, 83, 22);
				jButton1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/filtro.gif")));
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
				jSpinner1.setBounds(56, 21, 39, 19);
							}
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setSize(120, 130);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jButton1ActionPerformed(ActionEvent evt) {

//		if (ImagesData.imagesBFiltered.isEmpty())
//			ImagesData.cloneImagesBToImagesFiltered();
		MeanFilter mF = new MeanFilter(this.handImageFiltered,((Integer) jSpinner1.getValue()).intValue(), ImagesData.imagesB);
		mF.start();
	
		this.tabbedPane1.setSelectedIndex(1);
		
	
	}

}
