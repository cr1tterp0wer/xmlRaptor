package Thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import SQL.SQLstatementAction;




//
//
//@file    - Name of the current file to parse
//@reader  - xmlStream reader for parse 
//@factory - xmlreader wrapper
//@event   - int to track the xml tags <begin, end>
//@objectWrapper - The name of the xml element that wraps the data objs
//@attributeID   - The unique ID that the filing object was filed under
//@localName     - Name of the current xml element
//
//

public class XMLThread implements Callable{

	private File file;
	private XMLStreamReader reader;
	private XMLInputFactory factory;
	private int event;
	private String objectWrapper;
	private String attributeID;
	private String localTableName;
	private String[] args;
	private ArrayList<String> insertFirstHalf;
	private ArrayList<String> insertSecondHalf;
	

	public XMLThread(File file, String[] argv){
		this.file = file;
		this.args = argv;
        objectWrapper = args[1];
	}

	@Override
	public String[] call() throws Exception {
		
		return parseFile();
	}

	//parse file here
	private String[] parseFile() throws XMLStreamException{
		
		ArrayList<String> kkk = new ArrayList<String>();

		factory = XMLInputFactory.newInstance();
		try {
			reader = factory.createXMLStreamReader(new FileInputStream(this.file));
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		event   = reader.getEventType();

		while(reader.hasNext()){
			
			insertFirstHalf  = new ArrayList<String>();
			insertSecondHalf = new ArrayList<String>();
			
			switch(event){ 
			case XMLStreamConstants.START_ELEMENT:

				if(reader.getLocalName().equalsIgnoreCase(objectWrapper)) {
					attributeID = reader.getAttributeValue(null, "ID"); //Attribute unique ID (NOT ALL FILES WILL HAVE THIS FIX:!)
				}
				
				//Loop through elements to gather attribute data -> send somewhere else
				for(int i=0;i<reader.getAttributeCount();i++){		
					if( reader.getAttributeLocalName(i).trim().length() > 0 )
                     insertFirstHalf.add(reader.getAttributeLocalName(i));
					else
						insertFirstHalf.add("N/A");
					if( reader.getAttributeValue(i).trim().length() > 0 )
                      insertSecondHalf.add(reader.getAttributeValue(i));
					else
						insertSecondHalf.add("N/A");
				}

				localTableName = reader.getLocalName(); //table name here
				String fullsmt = SQLstatementAction.buildStatement(localTableName, attributeID, insertFirstHalf, insertSecondHalf);
				kkk.add(fullsmt);
				//System.out.println(fullsmt);

				break;

			case XMLStreamConstants.END_ELEMENT:
				break;
			default: break;
			}//end switch
			try {
				if(!reader.hasNext())
					break;
				event = reader.next(); //next 'xml-node-element'
			}catch (XMLStreamException e) {e.printStackTrace();}
		}//end while
		//close up
        reader.close();
        System.gc();
        System.out.println("finished:"+this.toString());
        
        String[] fff = new String[kkk.size()];
        fff = kkk.toArray(fff);
        return fff; 
        
	}
}



















