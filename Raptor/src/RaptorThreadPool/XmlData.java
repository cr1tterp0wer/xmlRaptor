package RaptorThreadPool;

import java.util.ArrayList;
import java.util.HashMap;

public class XmlData<T> {

	//All blobs in an array
	private ArrayList<XmlBlob> data;
	
	public XmlData(){
		data = new ArrayList();
	}
	
	public void addData(XmlBlob e){
		data.add(e);
	}
	public int size(){return data.size();}
	public XmlBlob getElementAt(int index){return data.get(index);}
	public ArrayList<XmlBlob> getDataList(){return data;}
	
	//make a private class with the raw
	//hash<name:Attribute,value:Data >(); -> should be stored in an arrayList
	//LocalName -> filing, client, GlobalEntity,etc  -->corresponding table
	//elementName -> elementName
	
	public class XmlBlob{
		private HashMap<String,String > dataHash;
		private String elementName;
		private String parentID="";
		
		
		XmlBlob(){dataHash = new HashMap<String,String>();}

		public void   setElementName(String s){ elementName = s;}
		public String getElementName()        { return elementName;}
		public void   addAttribute(String name, String value){ dataHash.put(name, value);}
		public HashMap<String,String> getElements(){return dataHash;}
		public String getParentID(){return parentID;}
		public void   setParentID(String id){parentID = id;}
		
		
	}
}

