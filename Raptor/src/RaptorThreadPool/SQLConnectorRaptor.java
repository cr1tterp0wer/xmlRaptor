package RaptorThreadPool;

import MAIN.RaptorThreadPoolManager;
import SQL.SQLConnector;

public class SQLConnectorRaptor extends SQLConnector{
	
	RaptorThreadPoolManager parentPoolManager;
	
	public SQLConnectorRaptor(){super();}
	public SQLConnectorRaptor(RaptorThreadPoolManager parent){
		super();
		parentPoolManager = parent;
	}
	
	@Override
	protected void errorConnecting(){
	    isConnected = false;
		System.out.println("THERE WAS AN ERROR CONNECTING");
		parentPoolManager.setValidInput(false);
	}
	@Override
	protected void successfulConnection(){
	    System.out.println("SUCCESS::Connected to db!");
        isConnected = true;
		parentPoolManager.setValidInput(true);
	}
	public boolean getIsConnected(){return this.isConnected;}
}
