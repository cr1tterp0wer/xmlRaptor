package Tree;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class XMLObject {
	
	String elementName;
	int cid;
	String parentId;
	LinkedHashMap <String, String> dictionary = new LinkedHashMap<String, String>();
	
	public XMLObject(){
		super();
	}
	public void setDictionary(LinkedHashMap<String, String> _dictionary){ this.dictionary = _dictionary;}
	public void addAttribute(String _key, String _value)      {this.dictionary.put(_key, _value);}
	public void setElementName(String _elementName)           {elementName = _elementName;}
	public void setParentID(String _parentID)                 {parentId = _parentID;}
	public void setID(int _ID){
		cid = _ID;
		dictionary.put("cid", cid+"");
	}
	
	public String getElementName(){return elementName;}
	public String getparentID()   {return parentId;}
	public int getID()            {return cid;}
	
	public String getAttributeValue(String _key) {return this.dictionary.get(_key);} 
	public Map getAllAttributes()                {return this.dictionary;}
	public int getAttributeCount()               {return this.dictionary.size();}
	
	public void removeAll()                      {this.dictionary.clear();}
	public void printString(){
	
		for(Map.Entry<String, String> e :dictionary.entrySet())
		{
		    System.out.println(e.getKey()+": "+e.getValue());
		}
	}
	public String[] getAllKeys(){
		String[] keys = new String[dictionary.size()];
		int i = 0;
		for(Map.Entry<String, String> e :dictionary.entrySet())
		{
			keys[i] = e.getKey();
		}
		
		return keys;
	}
	public String[] getAllValues(){
		String[] values = new String[dictionary.size()];
		int i = 0;
		for(Map.Entry<String, String> e :dictionary.entrySet())
		{
			values[i] = e.getValue();
		}
		
		return values;
	}
	public LinkedHashMap<String, String> getDictionary(){
		return dictionary;
	}
	public boolean removeAttribute(String _key){
		if(!this.dictionary.containsKey(_key))
			return false;
		 this.dictionary.remove(_key);
		 return true;
		}
}