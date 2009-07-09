package ui;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;




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
public class DialogColor extends javax.swing.JDialog {
	private JColorChooser jColorChooser;
	private JButton choose;

	private JPanel panel;

	EnlargeDialog enlargeDialog = null;
	JPanel panelColor;
	public DialogColor(JPanel panelColor) {
		
		super();
		this.panel=new JPanel();
	
		
		this.panelColor = panelColor;
		initGUI();
		this.setTitle("Choose Color");
		this.pack();
		this.setSize(424, 362);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setVisible(true);
		

	}


	public DialogColor(EnlargeDialog enlargeDialog, JPanel panelColor) {
		super();
		this.panel=new JPanel();
		this.enlargeDialog = enlargeDialog;
		
		this.panelColor = panelColor;
		initGUI();
		this.setTitle("Choose Color");
		this.pack();
		this.setSize(424, 362);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setVisible(true);
		
	}


	private void initGUI() {
		try {
			{
				this.getContentPane().setLayout(null);
				{
					jColorChooser = new JColorChooser();
					this.getContentPane().add(jColorChooser);
					jColorChooser.setBounds(-1, 6, 412, 290);
					
				}
				{
					choose = new JButton();
					this.getContentPane().add(choose);
					choose.setText("Choose");
					choose.setBounds(154, 301, 98, 28);
					choose.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/choose.gif")));
					choose.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							chooseMouseClicked(evt);
						}
					});
				}
			}
			this.setSize(424, 362);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void chooseMouseClicked(MouseEvent evt) {
		panel.setBackground(jColorChooser.getColor());
		panelColor.setBackground(jColorChooser.getColor());
		this.dispose();
		
	}

}
