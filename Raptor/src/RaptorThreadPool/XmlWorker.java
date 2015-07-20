package RaptorThreadPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.mysql.jdbc.PreparedStatement;

public class XmlWorker implements Runnable {

    private String    parentObjectName;
    private String    tableName;
    private String    attributeID="";
    private String[]  columns;
    private String[]  values;
    private int       event;

    private File            file;
    private int             workerID;
    private SqlWorker       sqlWorker;
    private ThreadSpawner   spawner;
    private XMLInputFactory xmlInputFactory;
    private XMLStreamReader xmlStreamReader;
    private CommandBuilder  builder;
    private ArrayList<PreparedStatement> preparedStatements;
    
    private long startTime, endTime;
    
    public XmlWorker(int workerNumber, ThreadSpawner s, String f){
        workerID = workerNumber;
        this.spawner =  s;
        this.file    = new File(f);
        parentObjectName = this.spawner.getManager().getConnector().getParentObject();
        xmlInputFactory  = XMLInputFactory.newInstance();   
        builder          = new CommandBuilder();
        builder.setConnection(this.spawner.getManager().getConnector().getConnection());
        
    }

    public void run(){       
//        startTime = System.currentTimeMillis();
        

        try{parseFile();}catch(Exception e){e.printStackTrace();}
        finish();
//        endTime = System.currentTimeMillis();
//        System.out.println("TIME IN MILLIS::" + (endTime - startTime));
    }

    private void finish(){
        System.out.println(this +"::"+ workerID + "::Done");
     
        sqlWorker = new SqlWorker(workerID, spawner, preparedStatements);
        spawner.getSqlExecutor().execute(sqlWorker);
    }

    private void parseFile() throws XMLStreamException{
        
        
        preparedStatements = new ArrayList<PreparedStatement>();
        try{
            xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(this.file)); //create the reader
        }catch(FileNotFoundException e){e.printStackTrace();}catch(XMLStreamException e ){e.printStackTrace();}

        event   = xmlStreamReader.getEventType();

        while(xmlStreamReader.hasNext()){
          

          switch(event){ 
            case XMLStreamConstants.START_ELEMENT:

                //init the column, val arrs
                columns = new String[xmlStreamReader.getAttributeCount()];
                values  = new String[xmlStreamReader.getAttributeCount()];
                 
                //get the local name
                if(xmlStreamReader.getLocalName().equalsIgnoreCase(parentObjectName)) {
                    attributeID = xmlStreamReader.getAttributeValue(null, "ID"); //Attribute unique ID
                }

                tableName = xmlStreamReader.getLocalName(); //table name here
               // System.out.println("TABLENAME::" + tableName);
           
                for(int i=0;i<xmlStreamReader.getAttributeCount();i++){		
                    // populate the columns, values array for the Builder     
                    columns[i] = xmlStreamReader.getAttributeLocalName(i);
                    //System.out.println("Table::" +tableName + " Columns::" + columns[i]);
                    values [i] = xmlStreamReader.getAttributeValue(i);  
                }
                
               //Don't be adding broken shit
               builder.buildInsertionStatement(tableName, columns, values);
               if(columns.length >0 && values.length > 0)
                   preparedStatements.add(builder.getPreparedStmt());
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


        System.gc();
        //printAllObjects();
    }

    //	private void printAllObjects(){
    //		for(int i=0;i<tree.size();i++){
    //			System.out.println(" NAME::"+tree.getElementAt(i).getElementName() );
    //			for( int k=0;k< tree.getDataList().size();k++){	
    //				 Iterator it =  tree.getElementAt(i).getElements().entrySet().iterator();
    //				while(it.hasNext()){
    //					Map.Entry pair = (Map.Entry)it.next();
    //					System.out.println("  -->" + pair.getKey() + " = " + pair.getValue());
    //			        it.remove();
    //				}
    //			}
    //		}
    //	}
}

