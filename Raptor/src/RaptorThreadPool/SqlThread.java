package RaptorThreadPool;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import MAIN.RaptorThreadPoolManager;
import ThreadPool.CallableWorkerThread;
import ThreadPool.Signal;

public class SqlThread extends CallableWorkerThread{

    private int workerID;
    private Signal signal;
    private RaptorThreadPoolManager rtpm;
    private LinkedList<Future> futures;
    private ArrayList<Integer> f;
    
    public SQLConnectorRaptor sqlConnector;
   
    public SqlThread(int workerNum, Signal s, RaptorThreadPoolManager rtpm){
        super(workerNum, s);
        workerID   = workerNum;
        signal     = s;
        
        this.rtpm  = rtpm;
        futures    = rtpm.getThreadPool().getFutures();
    }
    
  //INITIALIZE TO CONNECT TO A DATABASE
    public void init(String path, String xmlObject,String serverAddress,
            String port, String username,
            String pass, String dbName){
            sqlConnector.connect(path, xmlObject, serverAddress, port, username, pass, dbName);
    }

    
    // Wait for Futures to start
    // 
    @Override
    public Void call(){
       // try {dataLatch.await();} catch (InterruptedException e) {e.printStackTrace();}
       // testCall();
        //if futures are empty latch.await, latch = new latch(1)
        //check list size
        while(!rtpm.getFilePool().getFileNameStack().isEmpty()){
        		try {
					f.add((Integer) futures.pop().get()); //TODO: fix futures
				} catch (InterruptedException e) {e.printStackTrace();}
        		  catch (ExecutionException e) {e.printStackTrace();}
        }


        finish();
        return null;  
    }
    
    public void finish(){
        for(int i=0;i<f.size();i++)
        	System.out.println(f.get(i));
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

