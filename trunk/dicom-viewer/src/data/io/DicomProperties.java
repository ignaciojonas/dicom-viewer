package data.io;

import java.util.Vector;

public class DicomProperties {

	private Vector<String> properties;
	private Vector<String> values;
	
	public DicomProperties() {
		properties=new Vector<String>();
		values = new Vector<String>();
		
	}
	public void addPropertie(String proper,String value){
		properties.add(proper);
		values.add(value);
	}
	public int size(){
		return properties.size();
	}
	public String getPropertie(int i){
		return properties.get(i);
	}
	public String getValue(int i){
		return values.get(i);
	}
}
