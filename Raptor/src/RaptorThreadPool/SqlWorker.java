package RaptorThreadPool;

public class SqlWorker implements Runnable{

 
    private XmlData       tree;
    private int           workerID;
    private ThreadSpawner spawner;
    
    public SQLConnectorRaptor sqlConnector;

    //SHOULD SPAWN WITH XMLOBJECT
    public SqlWorker(int id, ThreadSpawner s, Object data){
        this.tree    = (XmlData)data;
        workerID     = id;
        spawner      = s;
        sqlConnector = spawner.getManager().getConnector();
    }
    
    @Override
    public void run(){
    	sqlInject();
    	
    	System.out.println(this + "::" + workerID);
    	finish();
    }

    public void finish(){
       spawner.incrementFinishedSql();
       spawner.shutdownSqlPool();
    }

    private void sqlInject(){
        sqlConnector.injectXmlBlob(this.tree);	
    }
    
}

