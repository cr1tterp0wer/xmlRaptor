package RaptorThreadPool;

import java.io.File;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import MAIN.RaptorThreadPoolManager;
import ThreadPool.CallableWorkerThread;

public class XmlWorker implements Runnable {
    
    private int    workerID;
    private String path;
    private File   file;
	private SqlWorker     sqlWorker;
	private ThreadSpawner spawner;
	private Object data;
	
    
    public XmlWorker(int workerNumber, ThreadSpawner s, String f){
        workerID = workerNumber;
        
        this.spawner =  s;
        this.file   = new File(f);
    }
   
    public void run(){       
        testCall();
//        this.finish();
     //   finish();
        
       data = new Object();
       // sqlWorker  = new SqlWorker( this.workerID, this.spawner, obj );
        finish();
       
    }
    
    
    
    private void finish(){
        System.out.println(this + "::Done");   
        sqlWorker = new SqlWorker(workerID, spawner, data);
        spawner.getSqlExecutor().execute(sqlWorker);
    }
   
    public void testCall(){
        Random r    = new Random();
        
        for(int i=0;i< 100;i++){
            int   delta = r.nextInt(200);
            System.out.println("xml#"+ workerID +" :: " + file);
            try {
                Thread.sleep(delta);    
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }   
    }
}
