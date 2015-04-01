package RaptorThreadPool;

import java.util.Random;

import ThreadPool.CallableWorkerThread;
import ThreadPool.SQLConnector;
import ThreadPool.Signal;


public class SqlThread extends CallableWorkerThread{
	
	public SQLConnector sqlConnector;
	
	public SqlThread(int workerNumber){
		super(workerNumber);
		sqlConnector = new SQLConnector();
		initDefaultInput();
	}
	
	public SqlThread(int workerNumber, Signal s){
		super(workerNumber, s);
		sqlConnector = new SQLConnector();
		initDefaultInput();
	}
	
	//connect to db
	public void init(String path, String xmlObject,String serverAddress,
			String port, String username,
			String pass, String dbName){
		sqlConnector.connect(path, xmlObject, serverAddress, port, username, pass, dbName);
		
	}
	
	public void initDefaultInput(){
		sqlConnector.connect("./metadata/","filing","localhost",
							"3306", "root", "critterpower", 
							"web_app");	
	}
	
	public Integer call(){
		
		//should wait on init for the xmlObjects to populate the list<Futures> xmlobjects
		while(!signal.hasDataToProcess()){
				synchronized(this){
					System.out.println("SQL#"+ workerNumber +" :: " +" is waiting for signal");
					try {this.wait();} catch (InterruptedException e) {e.printStackTrace();}

			}
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
