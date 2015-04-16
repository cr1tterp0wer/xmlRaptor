package RaptorThreadPool;



import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import MAIN.RaptorThreadPoolManager;

public class ThreadSpawner {

    private RaptorThreadPoolManager manager;
    private volatile Stack<Object>  dataList;
    private Executor xmlExecutor;
    private ThreadPoolExecutor sqlExecutor;
    private BlockingQueue<Runnable> blockingQueue;
    private int      workerID = 0;
   
    //this should spawn all the necessary threads and create a new sql thread outright!
    
    public ThreadSpawner(int numberOfThreads, RaptorThreadPoolManager m){
        manager = m;
        dataList = new Stack<Object>();
        xmlExecutor = Executors.newFixedThreadPool(numberOfThreads);
        blockingQueue = new ArrayBlockingQueue<Runnable>(100);
        sqlExecutor   = new ThreadPoolExecutor(10,20,0, TimeUnit.MINUTES, blockingQueue);
        
        
    }
    public void init(){
        xmlExecutor.execute(new XmlWorker(workerID++, this, manager.filePool.getFileNameStack().pop() ));
        xmlExecutor.execute(new XmlWorker(workerID++, this, manager.filePool.getFileNameStack().pop() ));
        xmlExecutor.execute(new XmlWorker(workerID++, this, manager.filePool.getFileNameStack().pop() ));
        xmlExecutor.execute(new XmlWorker(workerID++, this, manager.filePool.getFileNameStack().pop() ));
    }

    
    public void start(){
        
        
        while(!(manager.filePool.getFileNameStack().isEmpty()) ){
            xmlExecutor.execute(new XmlWorker(workerID++, this, manager.filePool.getFileNameStack().pop() ));
        }
        shutdownXML();

    }
    
    
    public void shutdownXML(){
        ((ExecutorService) xmlExecutor).shutdown();
        
    }
    public void shutdownSql(){
        sqlExecutor.shutdown();
    }
    public Executor getSqlExecutor(){return sqlExecutor;}
    public RaptorThreadPoolManager getManager(){return manager;}
    
}
