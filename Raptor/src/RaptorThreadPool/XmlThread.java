package RaptorThreadPool;

import java.io.File;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import ThreadPool.CallableWorkerThread;
import ThreadPool.Signal;

public class XmlThread extends CallableWorkerThread {
    
    private String path;
    private File   file;
	CountDownLatch dataLatch;
  
    public XmlThread(int workerNumber, Signal s, String f){
        super(workerNumber, s);
        this.signal = (RaptorSignal) s;
        this.file   = new File(f);
        
        
    }
   
    @Override
    public Integer call(){       
        testCall();
        this.finish();
        return new Integer(999999999);  //return the future object, should be an xmlBLOB
    }
    
    public void finish(){


        ((RaptorSignal)signal).notifyAndSpawn(dataLatch);

        if(dataLatch.getCount() > 0)
        	dataLatch.countDown();
        
    }
   
    public void testCall(){
        Random r    = new Random();
        
        for(int i=0;i< 100;i++){
            int   delta = r.nextInt(200);
            System.out.println("xml#"+ workerNumber +" :: " + file);
            try {
                Thread.sleep(delta);    
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }   
    }
}
