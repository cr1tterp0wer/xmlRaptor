package RaptorThreadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import MAIN.RaptorThreadPoolManager;

public class ThreadSpawner {

    private RaptorThreadPoolManager rtpm;   
    private Executor          sqlThreads;
    private ExecutorService   xmlThreads;
    private int               numThreads;        
    public int                workerID;          //Worker Thread ID
    
    //this should spawn all the necessary threads and create a new sql thread outright!
    
    public ThreadSpawner(int numberOfThreads, RaptorThreadPoolManager manager){
        this.rtpm        = manager;
        numThreads       = numberOfThreads; 
        sqlThreads  = Executors.newSingleThreadExecutor();
        xmlThreads       = Executors.newFixedThreadPool(numThreads);
        workerID         = 0;
    }
    
    public void init(){
        for(int i=0;i<numThreads;i++){
           xmlThreads.submit(new XmlWorker( workerID++, this, this.rtpm.filePool.getFileNameStack().pop() ));// futures.add( executor.submit(workers.get(i))); //submit the workers into the executor
           //sqlThread needs a new xmlReturn-blob :p
        
        
        }
    }
    
    //Spawns xmlThreads if the fileList still have files left to process
    public synchronized void  notifyAndSpawn(CountDownLatch dataLatch){
        int    workerID  = this.workerID++;
        int    size      = rtpm.listOfFilesSize();
       
        //see if there are any files left to parse,spawn 
       // if(!(rtpm.getFilePool().getFileNameStack().isEmpty())){
            
         //   String fileName  = rtpm.getFilePool().getFileNameStack().pop();
         //   XmlWorker x = (XmlWorker)(rtp.addWorker(new XmlWorker(workerID, this, fileName))); //xmlThread expects a "Signal" not a "Spawner"
            
            //TODO: Spawn an sql thread from this future
           // rtp.submit(x);
      //  }
      //  else{
       //     System.out.println("All Files Parsed::");
        //    rtp.shutdown(); 
       //}
        
//        private void submitXMLThreads(){
//            for(int i=0;i<MAX_NUM_WORKERS;i++){
//               xmlThreads.submit(new XmlWorker( workerID++, spawner, filePool.getFileNameStack().pop() ));// futures.add( executor.submit(workers.get(i))); //submit the workers into the executor
//            }
//        }
        
       
        
        
    }
    
    
    
    
    
    
    
    
}
