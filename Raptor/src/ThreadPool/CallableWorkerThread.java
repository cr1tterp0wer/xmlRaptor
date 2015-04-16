package ThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import sun.misc.Signal;
import RaptorThreadPool.ThreadSpawner;

public class CallableWorkerThread implements Callable<Object> {

	protected ThreadSpawner spawner;
	protected int workerID;
	
	public CallableWorkerThread(int id){
		workerID = id;
	}
	public CallableWorkerThread(int id, ThreadSpawner s){
	    spawner = s;
		workerID = id;
	}
	public CallableWorkerThread(int id, ThreadSpawner s, CountDownLatch l)
	{
	    spawner       = s;
	    workerID = id;
	}
	
	public void init(){}
	
	
	//Use the call method much like 'run()'
	//returns an Integer that represents the tasks number
	@Override
	public Object call() throws Exception {
		return null;
	}
	
	public int getWorkerNumber(){return workerID;}
}
