package data.io;


import java.awt.Point;
import java.io.FileWriter;
import java.util.Vector;
import org.exolab.castor.xml.Marshaller;



 public  class Encoder {
	
public Encoder() {
	// TODO Auto-generated constructor stub
}


    public static boolean encode(Project project, String nameFile)
    {
        try
        {
           	String aux = nameFile;
            FileWriter faux = null;
           	String tmp[];
           	tmp=nameFile.split(".");
           	if(tmp.length>1)
           		{
           			if(tmp[1].compareTo("xml")!=0)
           				aux = aux +".xml" ;
           		}
           	else
           		aux = aux +".xml" ;
           	faux = new FileWriter(aux);
	        Marshaller.marshal(project, faux);
	        faux.close();
	        return true;
           
           
    	}
        catch(Exception e)
        {
        	System.err.println(e.toString());
        	
        	return false;
        }
    }
	
}