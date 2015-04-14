package RaptorThreadPool;


import java.util.Random;
import java.util.concurrent.CountDownLatch;

import MAIN.RaptorThreadPoolManager;
import ThreadPool.CallableWorkerThread;
import ThreadPool.Signal;

public class SqlThread extends CallableWorkerThread{

    private int workerID;
    private Signal signal;
    private RaptorThreadPoolManager rtp;
    private CountDownLatch dataLatch;
    
    public SQLConnectorRaptor sqlConnector;
    
    
    
    public SqlThread(int workerNum, Signal s, CountDownLatch l, RaptorThreadPoolManager rtpm){
        super(workerNum, s,l);
        workerID   = workerNum;
        signal     = s;
        dataLatch  = l;  //The dataLatch polling work
        rtp        = rtpm;
    }
    
  //INITIALIZE TO CONNECT TO A DATABASE
    public void init(String path, String xmlObject,String serverAddress,
            String port, String username,
            String pass, String dbName){
            sqlConnector.connect(path, xmlObject, serverAddress, port, username, pass, dbName);
    }
    
//    //If there is no data to process, wait for dataLatch
//    @Override
//    public void run() {
//        try {dataLatch.await();} catch (InterruptedException e) {e.printStackTrace();}
//        testCall();
//        finish();
//    }
    @Override
    public Void call(){
        try {latch.await();} catch (InterruptedException e) {e.printStackTrace();}
        testCall();
        
        finish();
        return null;  
    }
    
    public void finish(){
        
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

