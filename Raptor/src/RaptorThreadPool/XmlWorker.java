package RaptorThreadPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Map;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import RaptorThreadPool.XmlData.XmlBlob;

public class XmlWorker implements Runnable {

	private int    workerID;
	private File   file;
	private SqlWorker       sqlWorker;
	private ThreadSpawner   spawner;
	private XMLInputFactory xmlInputFactory;
	private XMLStreamReader xmlStreamReader;
	private int       event;
	private XmlData   tree;
	private XmlBlob   element;
	private String    parentObjectName;
	private String    attributeID="";

	public XmlWorker(int workerNumber, ThreadSpawner s, String f){
		workerID = workerNumber;
		this.spawner =  s;
		this.file    = new File(f);
		parentObjectName = this.spawner.getManager().getConnector().getParentObject();
		xmlInputFactory  = XMLInputFactory.newInstance();   
		tree             = new XmlData();
	}

	public void run(){       

		try{parseFile();}catch(Exception e){e.printStackTrace();}

		finish();
	}

	private void finish(){
		System.out.println(this +"::"+ workerID + "::Done");   
		sqlWorker = new SqlWorker(workerID, spawner, tree);
		spawner.getSqlExecutor().execute(sqlWorker);
	}

	private void parseFile() throws XMLStreamException{

		try{
			xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(this.file)); //create the reader
		}catch(FileNotFoundException e){e.printStackTrace();}catch(XMLStreamException e ){e.printStackTrace();}

		event   = xmlStreamReader.getEventType();
		
		while(xmlStreamReader.hasNext()){
			element = tree.new XmlBlob();
			
			switch(event){ 
			case XMLStreamConstants.START_ELEMENT:

				//get the local name
				//if you encounter another 'ParentObject' give it's attributes new cid#
				
				if(xmlStreamReader.getLocalName().equalsIgnoreCase(parentObjectName)) {
					attributeID = xmlStreamReader.getAttributeValue(null, "ID");
				}
				
				element.setElementName(xmlStreamReader.getLocalName());
				element.setParentID(attributeID);
				
				element.setElementName(xmlStreamReader.getLocalName());
				for(int i=0;i<xmlStreamReader.getAttributeCount();i++){				
					element.addAttribute(xmlStreamReader.getAttributeLocalName(i), xmlStreamReader.getAttributeValue(i));               
				}
				tree.addData(element);
				break;
			case XMLStreamConstants.END_ELEMENT:
				break;
			default:
				break;
			}
			try {
				if(!xmlStreamReader.hasNext())
					break;
				event = xmlStreamReader.next(); //next 'xml-node-element'
			}catch (XMLStreamException e) {e.printStackTrace();}
			
		}

		//take the object and send it over to the sqlThread
		xmlStreamReader.close();
		element = null;
		System.gc();
	    //printAllObjects();
	}

	private void printAllObjects(){
		for(int i=0;i<tree.size();i++){
			System.out.println(" NAME::"+tree.getElementAt(i).getElementName() );
			for( int k=0;k< tree.getDataList().size();k++){	
				 Iterator it =  tree.getElementAt(i).getElements().entrySet().iterator();
				while(it.hasNext()){
					Map.Entry pair = (Map.Entry)it.next();
					System.out.println("  -->" + pair.getKey() + " = " + pair.getValue());
			        it.remove();
				}
			}
		}
	}
}

