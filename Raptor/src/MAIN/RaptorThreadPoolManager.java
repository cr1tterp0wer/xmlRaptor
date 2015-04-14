package MAIN;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.Future;

import RaptorThreadPool.RaptorFileList;
import RaptorThreadPool.RaptorSignal;
import RaptorThreadPool.RaptorThreadPool;
import RaptorThreadPool.SqlThread;
import RaptorThreadPool.XmlThread;

public class RaptorThreadPoolManager {

	private final int        MIN_NUMBER_OF_WORKERS = 2;
	private final int        MAX_POSSIBLE_WORKERS  = 4;
	
	public volatile  LinkedList<Future> futures;               //futures to be accessed by sqlThread
	public int                     workerNumber;          //Worker Thread ID
	private RaptorThreadPool       threadPool; 
	private String[]               credentials;   
	private final int              NUM_OF_CREDS = 7;      //number of arguments credentials can have
	private boolean                validInput   = false;  //make sure input from user for credentials is valid
	
	private volatile RaptorFileList   filePool;  //Gets the list of files
	private volatile RaptorSignal     signal;
	private SqlThread                 sqlThread;
	
	
	public RaptorThreadPoolManager(){
		credentials      = new String[NUM_OF_CREDS];
		workerNumber     = 0;
	}
	
	
	//Initialize the first threads
	public void init(){
		inputDefualtCredentials();                       //get server credentials
		filePool   = new RaptorFileList(credentials[0]); //get all the necessary files
		
		System.out.println("NUMBER OF VALID FILES BEGIN::"+filePool.getFileNameStack().size());
			
		
		if(filePool.getFileNameStack().isEmpty())
        {
            throw new EmptyStackException();  //No files? No go.
        }
        else if(filePool.getFileNameStack().size() < MAX_POSSIBLE_WORKERS){
            //only launch one xml thread
            threadPool  = new RaptorThreadPool(MIN_NUMBER_OF_WORKERS);
            signal      = new RaptorSignal(MIN_NUMBER_OF_WORKERS, this);
            sqlThread   = new SqlThread(workerNumber++, signal, this); //Add the sql thread
            
            threadPool.addWorker(sqlThread);
            threadPool.addWorker(new XmlThread(workerNumber++,signal,filePool.getFileNameStack().pop() ));  //pre-load the threads into the pool
        }
        else{
            //Launch 3 xml threads
            threadPool  = new RaptorThreadPool(MAX_POSSIBLE_WORKERS);   
            signal      = new RaptorSignal(MAX_POSSIBLE_WORKERS, this);
            sqlThread   = new SqlThread(workerNumber++, signal, this); //Add the sql thread
            
            threadPool.addWorker(sqlThread);
            threadPool.addWorker(new XmlThread(workerNumber++, signal, filePool.getFileNameStack().pop() )); //pre-load the threads into the pool
            threadPool.addWorker(new XmlThread(workerNumber++, signal, filePool.getFileNameStack().pop() ));
            threadPool.addWorker(new XmlThread(workerNumber++, signal, filePool.getFileNameStack().pop() ));
            
        }
        initAllThreads();
	}
	
	public void begin(){
		futures = threadPool.submitAll();
	}
	
	public void end(){
	}
	
	public void    setValidInput(boolean b)        { validInput = b;}
	public boolean getValidInput()                 { return validInput;}
	public         RaptorThreadPool getThreadPool(){ return threadPool;}
	public int     listOfFilesSize()			   { return filePool.getFileNameStack().size(); }
	public         RaptorFileList getFilePool()    { return filePool;}
	
	
//	##################Private methods##################  //
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
