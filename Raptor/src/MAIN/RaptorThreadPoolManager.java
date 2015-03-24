package MAIN;

import RaptorThreadPool.*;
import ThreadPool.Signal;
import ThreadPool.ThreadPool;

public class RaptorThreadPoolManager {

	private final int NUMBER_OF_WORKERS = 4;
	private RaptorSignal signal;
	private RaptorThreadPool threadPool;
	
	public RaptorThreadPoolManager(){
		signal = new RaptorSignal();
		threadPool = new RaptorThreadPool(NUMBER_OF_WORKERS);
	}
	
	public void begin(){
		threadPool = new RaptorThreadPool();
		
		threadPool.addWorker(new SqlThread(0));
		threadPool.addWorker(new RaptorThread(1));
		threadPool.submitAll();
		threadPool.shutdown();
	}
	
	
	public void setRaptorSignal(Signal s){signal = (RaptorSignal)s;}
	public void setTheadPool(ThreadPool tp){threadPool = (RaptorThreadPool)tp;}
	
}
