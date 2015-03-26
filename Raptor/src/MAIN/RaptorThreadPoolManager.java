package MAIN;

import RaptorThreadPool.*;
import ThreadPool.Signal;
import ThreadPool.ThreadPool;

public class RaptorThreadPoolManager {

	private final int NUMBER_OF_WORKERS = 4;
	 RaptorSignal     signal;
	private RaptorThreadPool threadPool;
	private SqlThread        sqlThread;
	private RaptorThread     xml01, xml02, xml03;
	
	public RaptorThreadPoolManager(){
		signal     = new RaptorSignal();
		threadPool = new RaptorThreadPool(NUMBER_OF_WORKERS);
	}
	
	public void init(){
		
		threadPool = new RaptorThreadPool();
		
		sqlThread = new SqlThread(0, signal);
		signal.setActiveThread(sqlThread);
		
		xml01     = new RaptorThread(1, signal);
		xml02     = new RaptorThread(2, signal);
		xml03     = new RaptorThread(3, signal);
		
		
		threadPool.addWorker(sqlThread);
		threadPool.addWorker(xml01);
		threadPool.addWorker(xml02);
		threadPool.addWorker(xml03);
	}
	
	public void begin(){
		threadPool.submitAll();
	}
	public void end(){
		threadPool.shutdown();
	}
	
	public void setRaptorSignal(Signal s){signal = (RaptorSignal)s;}
	public void setTheadPool(ThreadPool tp){threadPool = (RaptorThreadPool)tp;}
	
}
