package ThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class CallableWorkerThread implements Callable<Object> {

	protected Signal signal;
	protected int workerNumber;
	
	public CallableWorkerThread(int id){
		workerNumber = id;
	}
	public CallableWorkerThread(int id, Signal s){
		signal = s;
		workerNumber = id;
	}
	public CallableWorkerThread(int id, Signal s, CountDownLatch l)
	{
	    signal       = s;
	    workerNumber = id;
	}
	
	public void init(){}
	
	
	//Use the call method much like 'run()'
	//returns an Integer that represents the tasks number
	@Override
	public Object call() throws Exception {
		return null;
	}
	
	public int getWorkerNumber(){return workerNumber;}
}
