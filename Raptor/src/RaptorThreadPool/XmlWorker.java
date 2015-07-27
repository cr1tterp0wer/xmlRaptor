package RaptorThreadPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import SQL.SQLConnector;

import com.mysql.jdbc.PreparedStatement;

public class XmlWorker implements Runnable {

    private String   insert01;
    private String   insert02;

    private String    parentObjectName;
    private String    tableName;
    private String    attributeID="";
    private String[]  values;
    private String[]  creds;
    private int       event;

    private File            file;
    private int             workerID;
    private SqlWorker       sqlWorker;
    private ThreadSpawner   spawner;
    private XMLInputFactory xmlInputFactory;
    private XMLStreamReader xmlStreamReader;
    private CommandBuilder  builder;
    private ArrayList<PreparedStatement> preparedStatements;
    private SQLConnector    conn;


    public XmlWorker(int workerNumber, ThreadSpawner s, String f){

        workerID = workerNumber;
        this.spawner =  s;
        this.file    = new File(f);
        parentObjectName = this.spawner.getManager().getConnector().getParentObject();
        xmlInputFactory  = XMLInputFactory.newInstance();   
        builder          = new CommandBuilder();
        builder.setConnection(this.spawner.getManager().getConnector().getConnection());
        //        conn = this.spawner.getManager().getConnector();
        creds = this.spawner.getManager().getConnector().getCreds();
        conn = new SQLConnector();
        
        connect();
        

    }

    private void connect(){
        conn.connect(creds[0], creds[1], creds[2], creds[3], creds[4], creds[5], creds[6]);
    }
    
    public void run(){       
        try{parseFile();}catch(Exception e){e.printStackTrace();}
        finish();
    }

    private void finish(){
        System.out.println(this +"::"+ workerID + "::Done");
        //     
        //        sqlWorker = new SqlWorker(workerID, spawner, preparedStatements);
        //        spawner.getSqlExecutor().execute(sqlWorker);
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
                values  = new String[xmlStreamReader.getAttributeCount()+1];

                //get the local name
                if(xmlStreamReader.getLocalName().equalsIgnoreCase(parentObjectName)) {
                    attributeID = xmlStreamReader.getAttributeValue(null, "ID"); //Attribute unique ID
                }

                tableName = xmlStreamReader.getLocalName(); //table name here

                insert01 = "INSERT INTO " + tableName + " (";
                insert02 = " VALUE (";


                for(int i=0;i<xmlStreamReader.getAttributeCount();i++){		
                    insert01 += xmlStreamReader.getAttributeLocalName(i) + ",";
                    insert02 += "'"+ xmlStreamReader.getAttributeValue(i) + "',";
                    values[i] = xmlStreamReader.getAttributeValue(i);
                }
                insert01 += "qID)";
                insert02 += "'" + attributeID + "');";
                // System.out.println("INSERTIONSTATEMENT::" +insert01 + insert02);

                try {
                    if(xmlStreamReader.getAttributeCount() >0)
                        conn.executeUpdate(conn.getConnection(), insert01+insert02);
                } catch (SQLException e1) {
                    int errorCode = e1.getErrorCode();
                    //System.out.println("ERRORCODE:::"+errorCode);

                    switch(errorCode){
                    case 1146: createTableExecuteStatement(insert01+insert02, tableName,   conn);
                    case 1064: sanitizeAndExecuteStatement( insert01, values, attributeID, conn);break;
                    case 1054: handleMissingColumnException(e1.getMessage(),tableName,     conn);break;
                    }
                }

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
        System.out.println("finished:"+this.toString());
    }

    private void handleMissingColumnException(String errorString, String tableName, SQLConnector conn){
        String[] ar = errorString.split(" ");
        String column= "";
        SQLConnector connector = conn;

        //get column name
        ar  = errorString.split("'");
        column = ar[1];
        System.out.println("xmlWorker::"+workerID+"-UNKNOWN COLUMN::" + column);
        //create <column> in <table>
        //try to re-execute the pstmt
        String update = "ALTER TABLE " + tableName + " ADD " + column + " TEXT;";
        try {
            connector.executeUpdate(conn.getConnection(),  update);
        } catch (SQLException e) {handleMissingColumnException(e.getMessage(), tableName, connector);}
    }

    private String[] sanitize(String[] dirtyValues){

        String tempValue = "";
        String[] newValues = new String[dirtyValues.length];
        ArrayList<String> values = new ArrayList<String>();
        if(dirtyValues.length >0){ 
            for(int i=0;i<dirtyValues.length;i++){
                if(dirtyValues[i] == null)
                    continue;
                tempValue = dirtyValues[i];
                tempValue =  tempValue.replaceAll(",","").replaceAll("'", "^").replaceAll("\\\\","-");
                tempValue = tempValue.replaceAll("\\r\\n|\\r|\\n", "-").replaceAll(",","-").replaceAll(";",".");

                values.add(tempValue);   
            }
            newValues = values.toArray(new String[values.size()]);
        }
        return newValues;
    }

    private void sanitizeAndExecuteStatement( String firstHalf, String[] _values, String ID, SQLConnector con ){

        String[] sanitizedValues = sanitize(_values);

        firstHalf += " VALUE (";
        for(int i=0;i<sanitizedValues.length;i++){
            firstHalf += "'"+ sanitizedValues[i] + "',";
        }

        firstHalf += "'" + ID + "');";
        
        try {con.executeUpdate(con.getConnection(), firstHalf);} catch (SQLException e) {System.out.println("COULD NOT EXECUTE::" + firstHalf);e.printStackTrace();}
    }

    private void createTableExecuteStatement(String statement, String table, SQLConnector con){
        try{con.executeUpdate(con.getConnection(),"CREATE TABLE IF NOT EXISTS " + table + " (pid int(11) KEY NOT NULL AUTO_INCREMENT);");
        con.executeUpdate(con.getConnection(), statement);
        } catch(Exception e){e.printStackTrace();}
    }
}

