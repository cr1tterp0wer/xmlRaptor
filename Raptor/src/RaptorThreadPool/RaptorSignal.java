package RaptorThreadPool;

import java.util.concurrent.CountDownLatch;

import MAIN.RaptorThreadPoolManager;
import ThreadPool.CallableWorkerThread;
import ThreadPool.Signal;

public class RaptorSignal extends Signal{

    private RaptorThreadPoolManager rtpm;   
    private RaptorThreadPool        rtp;
    
    public RaptorSignal(int numberOfThreads, RaptorThreadPoolManager manager){
        super();
        this.rtpm = manager;
        rtp       = this.rtpm.getThreadPool();
    }
    
    //Spawns xmlThreads if the fileList still have files left to process
    public synchronized void  notifyAndSpawn(CountDownLatch dataLatch){
        int    workerID  = rtpm.workerNumber++;
        int    size      = rtpm.listOfFilesSize();
       
        //see if there are any files left to parse,spawn 
    	if(!(rtpm.getFilePool().getFileNameStack().isEmpty())){
    		
    		 String fileName  = rtpm.getFilePool().getFileNameStack().pop();
    		XmlThread x = (XmlThread)(rtp.addWorker(new XmlThread(workerID, this, fileName)));
    		rtp.submit(x);

        }
        else{
            System.out.println("All Files Parsed::");
            rtp.shutdown(); 
        }
    }
    
    
}
