package Tree;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import Tree.XMLTree.Node;
import Tree.XMLTreeHandler;


public class XMLThread<T> {
	
	private XMLInputFactory xmlInputFactory;
	private XMLStreamReader xmlStreamReader;
	private int       event;
	private XMLObject element;
	private int       cid;
	private String    parentObjectName;
	private File      file;
	
	public  XMLTreeHandler      tree;
	
	public XMLThread( String objectName ){
		
		xmlInputFactory = XMLInputFactory.newInstance();
		cid=0;
		parentObjectName = objectName;
	}
}

//	
//	public void run(){
//		//figure out which file to take off the stack.pop()
//		//parse the file
//		//wait in queue for sqlThread to finish
//		//hand over the map to sqlThread
//		//reiterate
//			  while(!raptorThreadHandlerRef.isFileNamesEmpty())
//				  try{ parseFile(this.getValidFile());	}catch(Exception e){}
//	}
//	
//	private void parseFile(String file) throws XMLStreamException{
//		try {
//			xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(file));
//		}catch (FileNotFoundException e) {e.printStackTrace();} 
//		 catch (XMLStreamException    e) {e.printStackTrace();}		
//		Node<Object> root = new Node<Object>();
//		tree = new XMLTreeHandler(root);
//		element = new XMLObject();
//		event = xmlStreamReader.getEventType();
//		
//		while(xmlStreamReader.hasNext()){
//			
//			switch(event){ 
//				case XMLStreamConstants.START_ELEMENT:
//					
//					if(xmlStreamReader.getLocalName().equalsIgnoreCase(parentObjectName)){
//						element.setElementName(xmlStreamReader.getLocalName());
//					}
//					element.setElementName(xmlStreamReader.getLocalName());
//					
//					for(int i=0;i<xmlStreamReader.getAttributeCount();i++){
//						element.addAttribute(xmlStreamReader.getAttributeLocalName(i), xmlStreamReader.getAttributeValue(i));				
//					}
//					tree.addChild(new Node(element));
//					tree.moveToNextChild();
//					break;
//				case XMLStreamConstants.END_ELEMENT:
//					tree.moveToParent();
//					break;
//				default:
//					break;
//			}
//			try {
//				if(!xmlStreamReader.hasNext())
//					break;
//				event = xmlStreamReader.next();
//			}catch (XMLStreamException e) {e.printStackTrace();}
//		}
//		
//		//take the object and send it over to the sqlThread
//		
//		xmlStreamReader.close();
//		element = null;
//		System.gc();
//		System.out.println(this.getValidFile());
//		//printAllObjects();
//		
//	}
//	
//	
//    private void passData(){}   
//	
//	private String getValidFile(){ 
//		if(!raptorThreadHandlerRef.isFileNamesEmpty())
//		  return raptorThreadHandlerRef.popFile();
//		else
//			return "";
//	}
//	
//    public void printAllObjects(){
//    	for(int i=0;i<tree.getCurrentChildrenSize();i++){
//    		XMLObject e = (XMLObject) ((Node<Object>) tree.getChildAt(i)).getData();
//    			System.out.println("************ " + e.getElementName() + " ************" );
//    			System.out.println("ID:" + e.getID());
//    			e.printString();
//    	}
//    }
//    
//    public int getCID(){return cid;}
//}

/*
public void parse(String _fileName) throws XMLStreamException{
+               int event = xmlStreamReader.getEventType();
+
+               while(true){
+
+                       element = new XMLRaptorObject();  // Data-object
+
+                       switch(event){
+                       case XMLStreamConstants.START_ELEMENT:
+                               element.setElementName(xmlStreamReader.getLocalName());
+                               if(element.getElementName().equalsIgnoreCase(idElement))
+                                       id++;
+                               element.setID(id);
+
+                               for(int i=0; i < xmlStreamReader.getAttributeCount(); i++){
+                                       element.addAttribute(xmlStreamReader.getAttributeLocalName(i), xmlStreamReader.getAttributeValue(i));
+                               }      
+                               tree.addChild(new Node(element));
+                               tree.moveToNextChild();
+                               break;
+                       case XMLStreamConstants.END_ELEMENT:
+                               tree.moveToParent();
+                               break;
+                       default:
+                               break;
+                       }
+
+                       if(!xmlStreamReader.hasNext())
+                               break;
+                       event = xmlStreamReader.next();
+               }
+              
+               xmlStreamReader.close();
+               //printAllObjects();
+              
+       }*/

