package ThreadPool;

public class Signal {

	  protected boolean hasDataToProcess = false;  //Do the waiting threads have data to process?

	  public synchronized boolean hasDataToProcess(){
		  return this.hasDataToProcess;
	  }

	  public synchronized void setHasDataToProcess(boolean hasData){
	    this.hasDataToProcess = hasData;  
	  }
}