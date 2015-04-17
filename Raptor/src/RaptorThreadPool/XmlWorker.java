package RaptorThreadPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import Tree.XMLObject;
import Tree.XMLTree.Node;
import Tree.XMLTreeHandler;
import MAIN.RaptorThreadPoolManager;

public class XmlWorker implements Runnable {
    
    private int    workerID;
    private String path;
    private File   file;
	private SqlWorker     sqlWorker;
	private ThreadSpawner spawner;
	private Object data;
	private XMLInputFactory xmlInputFactory;
	private XMLStreamReader xmlStreamReader;
    private int       event;
    private XMLObject element;
    private int       cid;
    private String    parentObjectName;
    
    public  XMLTreeHandler      tree;
    
    public XmlWorker(int workerNumber, ThreadSpawner s, String f){
        workerID = workerNumber;
        this.spawner =  s;
        this.file   = new File(f);
        //NEW THINGS


        
        
    }
   
    public void run(){       
        
       // try{parseFile();}catch(Exception e){e.printStackTrace();}
        
        data = new Object();
        finish();
    }
    
    private void finish(){
        System.out.println(this +"::"+ workerID + "::Done");   
        sqlWorker = new SqlWorker(workerID, spawner, data);
        spawner.getSqlExecutor().execute(sqlWorker);
    }

    
    private void parseFile() throws XMLStreamException{
        
        try{
        xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(this.file)); //create the reader
        }catch(FileNotFoundException e){e.printStackTrace();}catch(XMLStreamException e ){e.printStackTrace();}
        
        
        
        
        
        
        
    }
    
//    private void parseFile() throws XMLStreamException{
//       
//        
//        try {
//          xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(this.file));
//      }catch (FileNotFoundException e) {e.printStackTrace();} 
//       catch (XMLStreamException    e) {e.printStackTrace();}     
//      Node<Object> root = new Node<Object>();
//      tree = new XMLTreeHandler(root);
//      element = new XMLObject();
//      event = xmlStreamReader.getEventType();
//      
//      while(xmlStreamReader.hasNext()){
//          
//          switch(event){ 
//              case XMLStreamConstants.START_ELEMENT:
//                  
//                  if(xmlStreamReader.getLocalName().equalsIgnoreCase(parentObjectName)){
//                      element.setElementName(xmlStreamReader.getLocalName());
//                  }
//                  element.setElementName(xmlStreamReader.getLocalName());
//                  
//                  for(int i=0;i<xmlStreamReader.getAttributeCount();i++){
//                      element.addAttribute(xmlStreamReader.getAttributeLocalName(i), xmlStreamReader.getAttributeValue(i));               
//                  }
//                  tree.addChild(new Node(element));
//                  tree.moveToNextChild();
//                  break;
//              case XMLStreamConstants.END_ELEMENT:
//                  tree.moveToParent();
//                  break;
//              default:
//                  break;
//          }
//          try {
//              if(!xmlStreamReader.hasNext())
//                  break;
//              event = xmlStreamReader.next();
//          }catch (XMLStreamException e) {e.printStackTrace();}
//      }
//      
//      //take the object and send it over to the sqlThread
//      xmlStreamReader.close();
//      element = null;
//      System.gc();
//      printAllObjects();
//    }
//    
//    
//    
//  public void printAllObjects(){
//  for(int i=0;i<tree.getCurrentChildrenSize();i++){
//      XMLObject e = (XMLObject) ((Node<Object>) tree.getChildAt(i)).getData();
//          System.out.println("************ " + e.getElementName() + " ************" );
//          System.out.println("ID:" + e.getID());
//          e.printString();
//  }
//}
//
//public int getCID(){return cid;}
//    
    
    
    //Testing Purposes
//  public void testCall(){
//      Random r    = new Random();
//      
//      for(int i=0;i< 100;i++){
//          int   delta = r.nextInt(200);
//          //System.out.println("xml#"+ workerID +" :: " + file);
//          try {
//              Thread.sleep(delta);    
//          } catch (InterruptedException e) {
//              e.printStackTrace();
//          }
//      }   
//  }
//  
}
