package MAIN;

import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import RaptorThreadPool.*;
import ThreadPool.Signal;
import ThreadPool.ThreadPool;

public class RaptorThreadPoolManager {

	private final int        MIN_NUMBER_OF_WORKERS = 2;
	private final int        MAX_POSSIBLE_WORKERS  = 4;
	
	private RaptorThreadPool threadPool;
	private int              workerNumber;
	private String[]         credentials;
	private final int        NUM_OF_CREDS = 7;
	private boolean          validInput   = false;
	private Future<String>[] futures;	
	private RaptorFileList   filePool;  //Gets the list of files
	private RaptorSignal     signal;
	private SqlThread        sqlThread;
	private CountDownLatch   latch;
	
	
	public RaptorThreadPoolManager(){
		credentials  = new String[NUM_OF_CREDS];
		workerNumber = 0;
		latch        = new CountDownLatch(1);
	}
	
	public void init(){
		inputDefualtCredentials();                       //get server credentials
		filePool   = new RaptorFileList(credentials[0]); //get all the necessary files
		int index =0;
		System.out.println("NUMBER OF VALID FILES::"+filePool.getNumValidFiles());
        if(filePool.getNumValidFiles() <= 0)
        {
            throw new EmptyStackException();  //No files? No go.
        }
        else if(filePool.getNumValidFiles() < MAX_POSSIBLE_WORKERS){
            //only launch one xml thread
            threadPool  = new RaptorThreadPool(MIN_NUMBER_OF_WORKERS);
            signal      = new RaptorSignal(MIN_NUMBER_OF_WORKERS, this);
            sqlThread   = new SqlThread(workerNumber++, signal,latch); //Add the sql thread
            
            threadPool.addWorker(sqlThread);
            threadPool.getWorkers().get(index++).init();
            threadPool.addWorker(new XmlThread(workerNumber++,signal,filePool.getListOfFileNames().pop(),latch));  //pre-load the threads into the pool
            threadPool.getWorkers().get(index++).init();
        }
        else{
            //Launch 3 xml threads
            threadPool  = new RaptorThreadPool(MAX_POSSIBLE_WORKERS);   
            signal      = new RaptorSignal(MAX_POSSIBLE_WORKERS, this);
            sqlThread   = new SqlThread(workerNumber++, signal,latch); //Add the sql thread
            
            threadPool.addWorker(sqlThread);
            threadPool.addWorker(new XmlThread(workerNumber++,signal, filePool.getListOfFileNames().pop(),latch)); //pre-load the threads into the pool
            threadPool.addWorker(new XmlThread(workerNumber++,signal, filePool.getListOfFileNames().pop(),latch));
            threadPool.addWorker(new XmlThread(workerNumber++,signal, filePool.getListOfFileNames().pop(),latch));
        }
        initAllThreads();
	}
	
	public void begin(){
	  threadPool.submitAll();
	}
	
	public void end(){	}
	
	public void generateWorker(){}
	
	
	public void setValidInput(boolean b){    validInput = b;}
	public boolean getValidInput()      { return validInput;}
	
//	##################################################################### Private methods
	private void inputCredentials(){
	    
	    while( !validInput){
            System.out.println("Would you like to launch xmlRaptor y/n");
            
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            if(input.charAt(0) != 'y'){
                System.out.println("Exiting...Goodbye");
                System.exit(1);
            }
            
            System.out.println("please enter PATH:");
            credentials[0]          = scan.nextLine();
            System.out.println("please enter XML OBJECT:");
            credentials[1]          = scan.nextLine();
            System.out.println("please enter SERVER ADDRESS:");
            credentials[2]          = scan.nextLine();
            System.out.println("please enter PORT:");
            credentials[3]          = scan.nextLine();
            System.out.println("please enter USERNAME:");
            credentials[4]          = scan.nextLine();
            System.out.println("please enter PASS:");
            credentials[5]          = scan.nextLine();
            System.out.println("please enter DATABASENAME:");
            credentials[6]          = scan.nextLine();
            
          //  sqlThread.init(credentials);
        }
	 }
	 private void inputDefualtCredentials(){

	     credentials[0] = "./metadata/TEST";
	     credentials[1] = "filing";
	     credentials[2] = "localhost";
	     credentials[3] = "3306";
	     credentials[4] = "root";
	     credentials[5] = "critterpower";       
	     credentials[6] = "test";       
	}
	private void initAllThreads(){
	    for(int i = 0;i<threadPool.getNumWorkers();i++)
	        threadPool.getWorkers().get(i).init();
	}
	
}
