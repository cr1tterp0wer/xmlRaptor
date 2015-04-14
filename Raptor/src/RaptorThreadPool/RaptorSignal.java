package RaptorThreadPool;

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
    
    //spawns xmlThreads if the fileList still have files left to process
    public synchronized void  notifyAndSpawn(){
        //workerNumber, signal, filePool.getListOfFileNames().pop(), 
        int workerID     = rtpm.workerNumber++;
        boolean isEmpty  = rtpm.isListOfFilesEmpty();
        String fileName  = rtpm.getFilePool().getListOfFileNames().pop();
         
        //see if there are any files left to parse,spawn 
        if(!isEmpty){
            rtpm.futures.add( rtp.submit(rtp.addWorker(new XmlThread(workerID, this, fileName))));       
        }
        else{
            System.out.println("EMPTYLIST");
            rtp.shutdown();
        }
    }
    
    
}
