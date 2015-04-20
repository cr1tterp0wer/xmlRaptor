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
    private int       workerID = 0;
    private int       numberOfFiles;
    private final int MAX_NUM_WORKERS  = 4;
    private final int MIN_NUM_WORKERS  = 1;
    private int       finishedSql=0;

   
    //this should spawn all the necessary threads and create a new sql thread outright!
    
    public ThreadSpawner(RaptorThreadPoolManager m){
        manager     = m;
        dataList    = new Stack<Object>();
        
        blockingQueue = new ArrayBlockingQueue<Runnable>(100);
        sqlExecutor   = new ThreadPoolExecutor(10,100,0, TimeUnit.MINUTES, blockingQueue);
    }
    public void init(){
        numberOfFiles = manager.getFilePool().getFiles().size();
        
        if(numberOfFiles>=4){
        	xmlExecutor = Executors.newFixedThreadPool(MAX_NUM_WORKERS);
	        xmlExecutor.execute(new XmlWorker(workerID++, this, manager.filePool.getFileNameStack().pop() ));
	        xmlExecutor.execute(new XmlWorker(workerID++, this, manager.filePool.getFileNameStack().pop() ));
	        xmlExecutor.execute(new XmlWorker(workerID++, this, manager.filePool.getFileNameStack().pop() ));
	        xmlExecutor.execute(new XmlWorker(workerID++, this, manager.filePool.getFileNameStack().pop() ));
        }else{
        	xmlExecutor = Executors.newFixedThreadPool(MIN_NUM_WORKERS);
        	xmlExecutor.execute(new XmlWorker(workerID++, this, manager.filePool.getFileNameStack().pop() ));
        }
        
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
   
    
    public synchronized void shutdownSqlPool(){  
        //0-based
        if(finishedSql >= numberOfFiles-1){
            shutdownSql();
            System.out.println("FINISHEDsql::"+finishedSql+" NumberOfFiles::"+ numberOfFiles);
        }
    }
    public Executor getSqlExecutor(){return sqlExecutor;}
    public RaptorThreadPoolManager getManager(){return manager;}
    public synchronized void incrementFinishedSql(){finishedSql++;}
    public synchronized int getNumberOfFiles(){return numberOfFiles;}
    public synchronized int getNumFinishedSql(){return finishedSql;}
    
    private void shutdownSql(){
        sqlExecutor.shutdown();
    }
    
}
