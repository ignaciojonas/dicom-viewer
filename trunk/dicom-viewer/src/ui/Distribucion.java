package ui;






import java.awt.Color;
import java.util.Vector;
import javax.swing.BoxLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


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
public class Distribucion extends javax.swing.JDialog {


	private static final long serialVersionUID = 1L;
	private JPanel jPanel1;
	private JFreeChart distribution;
	private XYSeries dist;
	private XYSeriesCollection dataset;
	
	
	Vector<Integer> graph;
	public Distribucion(JFrame frame,Vector<Integer> graph) {
		super(frame);
		this.setTitle("Graph");

		this.graph = graph;
		dist=new XYSeries("Distribution Color");  
		for (int i = 0; i < graph.size(); i++) {
			dist.add(i,(new Color(graph.get(i))).getRed());
		}
		dataset=new XYSeriesCollection();
		dataset.addSeries(dist);
		distribution=ChartFactory.createXYLineChart("Gradient Graph","Pixel","Color",dataset,PlotOrientation.VERTICAL, true, true, false);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(400, 400));
			BoxLayout thisLayout = new BoxLayout(getContentPane(), javax.swing.BoxLayout.X_AXIS);
			getContentPane().setLayout(thisLayout);
			this.setSize(400, 400);
			{
				jPanel1 = new ChartPanel(distribution);
				
				jPanel1.setLayout(null);
				getContentPane().add(jPanel1);
				jPanel1.setBounds(12, 12, 600, 499);
			}

		} catch (Exception e) {			e.printStackTrace();
		}
	}
	
	public void resetDataset(Vector<Integer> graph){
		dist.clear();
		for (int i = 0; i < graph.size(); i++) {
			dist.add(i,(new Color(graph.get(i))).getRed());
		}
	}
}
