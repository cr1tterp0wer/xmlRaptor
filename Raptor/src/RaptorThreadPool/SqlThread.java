package RaptorThreadPool;

import java.util.Random;

import MAIN.RaptorThreadPoolManager;
import ThreadPool.CallableWorkerThread;
import ThreadPool.SQLConnector;
import ThreadPool.Signal;


public class SqlThread extends CallableWorkerThread{
	
	public SQLConnectorRaptor sqlConnector;
	
	public SqlThread(int workerNumber){
		super(workerNumber);
		sqlConnector = new SQLConnectorRaptor();
	}
	
	public SqlThread(int workerNumber, Signal s){
		super(workerNumber, s);
		sqlConnector = new SQLConnectorRaptor();
	}
	public SqlThread(int workerNumber, Signal s, RaptorThreadPoolManager rtpm){
		super(workerNumber, s);
		sqlConnector = new SQLConnectorRaptor(rtpm);
	}



	//INITIALIZE TO CONNECT TO A DATABASE
	public void init(String path, String xmlObject,String serverAddress,
			String port, String username,
			String pass, String dbName){
		sqlConnector.connect(path, xmlObject, serverAddress, port, username, pass, dbName);
		
	}
	
	//INITIALIZE TO CONNECT TO A DATABASE
	//arguments index: 0->path, 1->xmlObject, 2->address, 3->port, 4->username, 5->pass, 6->dbName
	public void init(String[] args){
		if(args.length > 7){
			System.exit(1);
		}
		sqlConnector.connect(args[0],args[1],args[2],args[3],args[4],args[5],args[6]);
	}
	

	public Integer call(){
		
		//should wait on init for the xmlObjects to populate the list<Futures> xmlobjects
		while(!signal.hasDataToProcess()){
				synchronized(this){
					System.out.println("SQL#"+ workerNumber +" :: " +" is waiting for signal");
					try {this.wait();} catch (InterruptedException e) {e.printStackTrace();}
					

			}
				System.out.println();
		}
		
		System.out.println("SQL#"+ workerNumber +" :: READY!!!!!!!!!!!!!");
		
		//Do work here
		while(!((RaptorSignal)signal).areAllThreadsDone()){
			testCall();
		}
		
		//should close
		signal.setHasDataToProcess(false);
		
		
		sqlConnector.closeConnection();
		return workerNumber;
	}
	
	//just a test method to simulate work
	public void testCall(){
		Random r    = new Random();
		
		for(int i=0;i< 100;i++){
			int   delta = r.nextInt(100);
			System.out.println("SQL#"+ workerNumber +" :: " +i);
			try {
				Thread.sleep(delta);	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}
