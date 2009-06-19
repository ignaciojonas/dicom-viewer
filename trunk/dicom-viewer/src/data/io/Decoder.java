package data.io;


import java.io.FileReader;
import java.util.Vector;
import org.exolab.castor.xml.Unmarshaller;



public class Decoder {


	public static Project decode(String fileName){
		Project ret=null;
		try {
		      ret = (Project)Unmarshaller.unmarshal(Project.class, new FileReader(fileName));
		} catch (Exception e) {}
		    return ret;
	}
	
}
