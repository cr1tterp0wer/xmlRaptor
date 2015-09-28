package Thread;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import SQL.SQLConnector;
import SQL.SQLstatementAction;


public class SQLthread  implements Callable{

	private String[] args;
	private SQLConnector conn;
	private List<Future<String[]>> futures;
	
	
	public SQLthread(String[] argv){
		args = argv;
		 //give each XMLThread a connection!
        conn = new SQLConnector();
		futures = new LinkedList();
	}
	
	@Override
	public Object call(){
		conn.connect(args);
		this.injectSQLstatements();
        conn.closeConnection();
		return null;
	}
	
	public void injectSQLstatements(){

		for(Future<String[]> future: futures){
			try {
				String[] curUpdates = future.get();
				for(int i=0;i< curUpdates.length; i++){
					
					try{ SQLstatementAction.prepareMyStatement(curUpdates[i], conn); }
					
					catch (Exception e) {e.printStackTrace();}
				}
			}catch (InterruptedException e){e.printStackTrace();}
			 catch (ExecutionException   e){e.printStackTrace();}
		}
	}
	public void addToFutures(Future<String[]> f){
		this.futures.add(f);
	}
}
