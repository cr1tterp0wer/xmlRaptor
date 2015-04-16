package RaptorThreadPool;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Future;

import MAIN.RaptorThreadPoolManager;
import ThreadPool.CallableWorkerThread;

public class SqlWorker implements Runnable{

    private int workerID;
   

    private Object obj;
    
    public SQLConnectorRaptor sqlConnector;
   

    //SHOULD SPAWN WITH XMLOBJECT!
    public SqlWorker(int workerNum, ThreadSpawner s, Object obj){
    	
        workerID   = workerNum;
        this.obj   = obj;
        
    }
    

    @Override
    public void run(){
    	testCall();
    	System.out.println(this);
    }

    public void finish(){
       System.out.println(this.toString());
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

