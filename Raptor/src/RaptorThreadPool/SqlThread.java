package RaptorThreadPool;


import java.util.Random;
import java.util.concurrent.CountDownLatch;

import ThreadPool.CallableWorkerThread;
import ThreadPool.Signal;

public class SqlThread extends CallableWorkerThread{

    private int workerID;
    private Signal signal;
    public SQLConnectorRaptor sqlConnector;
    
    public SqlThread(int workerNum, Signal s, CountDownLatch l){
        super(workerNum, s, l);
        workerID = workerNum;
        signal = s;
        latch  = l;
    }
    
  //INITIALIZE TO CONNECT TO A DATABASE
    public void init(String path, String xmlObject,String serverAddress,
            String port, String username,
            String pass, String dbName){
            sqlConnector.connect(path, xmlObject, serverAddress, port, username, pass, dbName);
    }
    
    @Override
    public Integer call(){
       // try {latch.await();} catch (InterruptedException e) {e.printStackTrace();}
        testCall();
        
        finish();
        return workerNumber;  //return the future object, should be an xmlBLOB
    }
    
    public void finish(){
        System.out.println(this.toString() + ":: Finished()");

    }
   
    public void testCall(){
        Random r    = new Random();
        
        for(int i=0;i< 100;i++){
            int   delta = r.nextInt(100);
            System.out.println("sql#"+ workerNumber +" :: " +i);
            try {
                Thread.sleep(delta);    
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }   
    }
    
}

