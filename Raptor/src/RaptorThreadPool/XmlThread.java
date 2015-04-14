package RaptorThreadPool;

import java.io.File;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import ThreadPool.CallableWorkerThread;
import ThreadPool.Signal;

public class XmlThread extends CallableWorkerThread {
    
    private String path;
    private File   file;
  
    
    public XmlThread(int workerNumber, Signal s, String f,  CountDownLatch l){
        super(workerNumber, s,l);
        this.signal = (RaptorSignal) s;
        this.file   = new File(f);
    }
    
    public XmlThread(int workerNumber, Signal s, String f){
        super(workerNumber, s);
        this.signal = (RaptorSignal) s;
        this.file   = new File(f);
    }
    
    
    @Override
    public Integer call(){       
        testCall();
        
        finish();
        return workerNumber;  //return the future object, should be an xmlBLOB
    }
    
    public void finish(){
        if(latch.getCount() > 0)
            latch.countDown();
        System.out.println("LatchCount::" +this.latch.getCount());
        
        //tell the signal we're done
        ((RaptorSignal)signal).notifyAndSpawn();
        
    }
   
    public void testCall(){
        Random r    = new Random();
        
        for(int i=0;i< 100;i++){
            int   delta = r.nextInt(100);
            System.out.println("xml#"+ workerNumber +" :: " +i);
            try {
                Thread.sleep(delta);    
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }   
    }
}
