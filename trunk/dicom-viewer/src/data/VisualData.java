package data;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import data.io.DicomProperties;

import ui.MainFrame;

public class VisualData {
	public static JLabel jLabelSBImages=null;
	public static JLabel jLabelFPS=null;
	public static JLabel jLabelNameImage=null;
	public static JProgressBar jProgressBar =null;
	public static MainFrame mf=null;
	
	public static  JTable jTable1=null;
	public static  boolean viewDicomProperties = false;
	public static  boolean viewSeeds = true;
	public static  boolean viewCircle = true;
	public static boolean viewCircleAll = true;

	public static Color initColor = Color.RED;
	public static Color enlargeColor = Color.GREEN;
	public static Color reduceColor = Color.CYAN;
	public static Color normal = Color.WHITE;
	
	public static void setjLabelSBImages(String text){
		if(jLabelSBImages!=null)
			jLabelSBImages.setText(text);
	}
	public static void setjLabelNameImage(String text){
		if(jLabelNameImage!=null)
			jLabelNameImage.setText(text);
	}
	
	public static void loadTable(int index) {
		
		if(jTable1!=null){
			if(ImagesData.imagesProperties.size()>0){
			DicomProperties dicomProp = ImagesData.imagesProperties.get(index-1);
			String[][] table = new String[dicomProp.size()][2];
			for(int i=0;i< dicomProp.size();i++){
				table[i][0]=dicomProp.getPropertie(i);
				table[i][1]=dicomProp.getValue(i);
			}
			TableModel jTable1Model = new DefaultTableModel(table,new String[] { "Propertie", "Value" });
			jTable1.setModel(jTable1Model);
		}
		}
	}

	
}
