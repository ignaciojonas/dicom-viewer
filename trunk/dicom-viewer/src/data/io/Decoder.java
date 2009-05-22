package data.io;


import java.io.FileReader;
import java.util.Vector;
import org.exolab.castor.xml.Unmarshaller;



public class Decoder {


	public static Vector decode(String fileName){
		Vector ret=null;
		try {
		      ret = (Vector)Unmarshaller.unmarshal(Vector.class, new FileReader(fileName));
		} catch (Exception e) {}
		    return ret;
	}
	
}
