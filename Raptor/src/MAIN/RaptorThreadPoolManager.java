package MAIN;

import java.util.EmptyStackException;
import java.util.Scanner;

import RaptorThreadPool.RaptorFileList;
import RaptorThreadPool.SQLConnectorRaptor;
import RaptorThreadPool.ThreadSpawner;


public class RaptorThreadPoolManager {

	private String[]                  credentials;   
	private final int                 NUM_OF_CREDS = 7;      //number of arguments credentials can have
	private boolean                   validInput   = false;  //make sure input from user for credentials is valid
	private SQLConnectorRaptor        connector;
	private final int                 MAX_NUM_WORKERS  = 4;
	public  volatile RaptorFileList   filePool;  //Gets the list of files
	private volatile ThreadSpawner    spawner;

	public RaptorThreadPoolManager(){
		credentials      = new String[NUM_OF_CREDS];

		connector        = new SQLConnectorRaptor(this);
	}

	//Initialize the first threads
	public void init(){
	    spawner          = new ThreadSpawner(MAX_NUM_WORKERS, this);
		inputDefualtCredentials();                       //get server credentials
		filePool   = new RaptorFileList(credentials[0]); //get all the necessary files
		
		System.out.println("NUMBER OF VALID FILES BEGIN::"+filePool.getFileNameStack().size());
		
		if(filePool.getFileNameStack().isEmpty())        
            throw new EmptyStackException();  //No files? No go.
		

	}
	
	public void begin(){
        connector.connect(credentials[0],credentials[1],credentials[2],
                credentials[3],credentials[4],credentials[5],
                credentials[6]);
        //INIT the spawner()!
        spawner.init();
        spawner.start();
	
		
	}
	
	public void end(){
	    //TODO:close up the threads
	}
	
	public void    setValidInput(boolean b)        { validInput = b;}
	public boolean getValidInput()                 { return validInput;}
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

	     credentials[0] = "./metadata/filingdb";
	     credentials[1] = "filing";
	     credentials[2] = "localhost";
	     credentials[3] = "3306";
	     credentials[4] = "root";
	     credentials[5] = "critterpower";       
	     credentials[6] = "test";       
	}
}
