package ThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class CallableWorkerThread implements Callable<Integer> {

	protected Signal signal;
	protected int workerNumber;
	protected CountDownLatch latch;
	
	
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
	    latch        = l;
	}
	
	public void init(){}
	
	
	//Use the call method much like 'run()'
	//returns an Integer that represents the tasks number
	@Override
	public Integer call() throws Exception {
		return(workerNumber);
	}
	public int getWorkerNumber(){return workerNumber;}
}
