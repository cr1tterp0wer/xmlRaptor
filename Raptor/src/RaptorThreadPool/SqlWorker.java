package RaptorThreadPool;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Future;

import MAIN.RaptorThreadPoolManager;
import ThreadPool.CallableWorkerThread;

public class SqlWorker extends CallableWorkerThread{

    private int workerID;
   
    private RaptorThreadPoolManager rtpm;
    private Object obj;
    
    public SQLConnectorRaptor sqlConnector;
   

    //SHOULD SPAWN WITH XMLOBJECT!
    public SqlWorker(int workerNum, ThreadSpawner s, Object obj){
        super(workerNum, s);
        workerID   = workerNum;
        spawner     = s;
        
        this.rtpm  = rtpm;
        this.obj   = obj;
    }
    
  //INITIALIZE TO CONNECT TO A DATABASE
    //TODO:GET THE CONNECTOR TO SPAWN!!!!
//    public void init(String path, String xmlObject,String serverAddress,
//            String port, String username,
//            String pass, String dbName){
//            sqlConnector.connect(path, xmlObject, serverAddress, port, username, pass, dbName);
//            System.out.println("SqlINIT()**************************************************");
//    }

    
    // Wait for Futures to start
    // 
    @Override
    public Void call(){
       // try {dataLatch.await();} catch (InterruptedException e) {e.printStackTrace();}
       // testCall();
        //if futures are empty latch.await, latch = new latch(1)
        //check list size
//        while(!rtpm.getFilePool().getFileNameStack().isEmpty()){
//        		try {
//					f.add((Integer) futures.pop().get()); //TODO: fix futures
//				} catch (InterruptedException e) {e.printStackTrace();}
//        		  catch (ExecutionException e) {e.printStackTrace();}
//        }


        finish();
        return null;  
    }
    
    public void finish(){
       System.out.println(obj.toString());
     
    }
   
    public void testCall(){
        Random r    = new Random();
        
        for(int i=0;i< 100;i++){
            int   delta = r.nextInt(100);
            System.out.println("sql#"+ this.workerID +" :: " +i);
            try {
                Thread.sleep(delta);    
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }   
    }

}

