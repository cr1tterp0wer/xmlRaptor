package ThreadPool;

public class Signal {

	  protected boolean hasDataToProcess = false;

	  public synchronized boolean hasDataToProcess(){
		  return this.hasDataToProcess;
	  }

	  public synchronized void setHasDataToProcess(boolean hasData){
	    this.hasDataToProcess = hasData;  
	  }
}