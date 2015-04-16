package RaptorThreadPool;

import java.io.File;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import ThreadPool.CallableWorkerThread;

public class XmlWorker extends CallableWorkerThread {
    
    private String path;
    private File   file;
	CountDownLatch dataLatch;
	SqlWorker      sqlWorker;
  
    public XmlWorker(int workerNumber, ThreadSpawner s, String f){
        super(workerNumber, s);
        this.spawner =  s;
        this.file   = new File(f);
    }
   
    @Override
    public Runnable call(){       
        testCall();
//        this.finish();
        Object obj = new Object();
        sqlWorker  = new SqlWorker( this.workerID, this.spawner, obj );
        return sqlWorker;  //return the future object, should be an xmlBLOB
    }
    
    public void finish(){}
   
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
