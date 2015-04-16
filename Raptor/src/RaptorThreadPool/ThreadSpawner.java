package RaptorThreadPool;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import MAIN.RaptorThreadPoolManager;

public class ThreadSpawner {

    private RaptorThreadPoolManager rtpm;   
    private Executor          sqlThreads;
    private ExecutorService   xmlThreads;
    private int               numThreads;        
    private ArrayList<Future> sqlFutures;
    public int                workerID;          //Worker Thread ID
    public int                currentIndex = 0;
    
    //this should spawn all the necessary threads and create a new sql thread outright!
    
    public ThreadSpawner(int numberOfThreads, RaptorThreadPoolManager manager){
        this.rtpm        = manager;
        numThreads       = numberOfThreads; 
        sqlThreads       = Executors.newSingleThreadExecutor();
        xmlThreads       = Executors.newFixedThreadPool(numThreads);
        workerID         = 0;
        sqlFutures       = new ArrayList<Future>(numThreads);
    }
    
    public void init(){
        for(;currentIndex<numThreads;currentIndex++){
          sqlFutures.add(xmlThreads.submit(new XmlWorker( workerID++, this, this.rtpm.filePool.getFileNameStack().pop())) );
        }
        
    }
    
    //Spawns xmlThreads if the fileList still have files left to process
    public void  notifyAndSpawn(){
        int    workerID  = this.workerID++;
        
        System.out.println(sqlFutures.get(currentIndex).toString() + "::Current Index::" + currentIndex);
        
      
        //see if there are any files left to parse,spawn 
        if(!(rtpm.getFilePool().getFileNameStack().isEmpty())){
            sqlFutures.add(xmlThreads.submit(new XmlWorker( workerID++, this, this.rtpm.filePool.getFileNameStack().pop())) );
            currentIndex++;
            System.out.println(sqlFutures.get(currentIndex).toString() + "::Current Index::" + currentIndex);
            try {sqlThreads.execute((Runnable) sqlFutures.get(currentIndex).get());}
            catch (InterruptedException e){e.printStackTrace();}
            catch (ExecutionException e){e.printStackTrace();}
            
        }
        else{
            System.out.println("All Files Parsed::");
            xmlThreads.shutdown(); 
       }

    }
    
    
    
    
    
    
    
    
}
