package RaptorThreadPool;

import MAIN.RaptorThreadPoolManager;
import ThreadPool.SQLConnector;

public class SQLConnectorRaptor extends SQLConnector{
	
	RaptorThreadPoolManager parentPoolManager;
	
	public SQLConnectorRaptor(){super();}
	public SQLConnectorRaptor(RaptorThreadPoolManager parent){
		super();
		parentPoolManager = parent;
	}

	
	@Override
	protected void errorConnecting(){
		System.out.println("THERE WAS AN ERROR CONNECTING");
		parentPoolManager.setValidInput(false);
	}
	@Override
	protected void successfulConnection(){
		parentPoolManager.setValidInput(true);
	}
}
