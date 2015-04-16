package RaptorThreadPool;


import java.util.Random;
import java.util.concurrent.Executor;

public class SqlWorker implements Runnable{

 
    private Object obj;
    private int    workerID;
    private ThreadSpawner spawner;
    
    public SQLConnectorRaptor sqlConnector;
   

    //SHOULD SPAWN WITH XMLOBJECT!
    public SqlWorker(int id, ThreadSpawner s, Object obj){
        this.obj   = obj;
        workerID =id;
        spawner = s;
    
    }
    
    @Override
    public void run(){
    	testCall();
    	System.out.println(this);
    	finish();
    }

    public void finish(){
       spawner.incrementFinishedSql();
       spawner.shutdownSqlPool();
    }
   
    public void testCall(){
        Random r    = new Random();
        
        for(int i=0;i< 100;i++){
            int   delta = r.nextInt(100);
            System.out.println("sql#"+ workerID +" :: " +i);
            try {
                Thread.sleep(delta);    
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }   
    }

}

