package RaptorThreadPool;

import ThreadPool.CallableWorkerThread;
import ThreadPool.Signal;

public class RaptorSignal extends Signal{
	
	private CallableWorkerThread t;
	private boolean allThreadsAreDone = false;
	private   int     numThreads;
	private   int     numFinishedThreads = 0;
	
	public RaptorSignal(int numberOfThreads){
		super();
		numThreads = numberOfThreads;
	}
	
	public void setActiveThread(CallableWorkerThread cwt){t = cwt;}
	public CallableWorkerThread getActiveThread(){return t;}
	public void setNotify(){
		if(this.hasDataToProcess){
				try {
					synchronized(t){
						t.notify();	
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
	
	public synchronized void incrementAndPollThreads(){
		this.numFinishedThreads++;
		areAllThreadsDone();
	}
	
	public synchronized int  getNumberFinishedThreads(){return numFinishedThreads;}
	private synchronized boolean areAllThreadsDone(){
		//every xmlRaptor sends nofication here

		System.out.println("Areallthreadsdone()");
		if(numFinishedThreads >= numThreads){
			allThreadsAreDone  = true;
			System.out.println("WE ARE ALL DONE!");
			t.notify();
		}
		else
			allThreadsAreDone = false;
		
		return allThreadsAreDone;
	}
}









