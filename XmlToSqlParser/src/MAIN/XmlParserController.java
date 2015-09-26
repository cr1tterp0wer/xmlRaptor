package MAIN;


import java.util.EmptyStackException;
import java.util.Scanner;

import FileIO.FilePool;
import Thread.ThreadPool;

public class XmlParserController {


	private String[]    credentials;   
	private final int   NUM_OF_CREDS = 7;      //number of arguments credentials can have
	private boolean     validInput   = false;  //make sure input from user for credentials is valid

	private volatile FilePool   filePool;      //Gets the list of files
	private volatile ThreadPool threadPool;

	public XmlParserController(){
		credentials = new String[NUM_OF_CREDS];
		
	}

	//Initialize the first threads
	public void init(){
		
		inputDefualtCredentials();                       //get server credentials
        filePool   = new FilePool(credentials[0]);
        threadPool = new ThreadPool(credentials, filePool);
        
		System.out.println("NUMBER OF VALID FILES BEGIN::"+filePool.getFileNameStack().size());

		if(filePool.getFileNameStack().isEmpty())  {      
			throw new EmptyStackException();  //No files? No go.
		}
	}

	public void begin(){

		if(getValidInput()){
			threadPool.run();
			
		}else
			System.out.println("FATAL ERROR!");
	}
	
	public void shutdown(){
		//TODO:close up the threads
		threadPool.shutdown();
	}

	public void    setValidInput(boolean b)        { validInput = b;}
	public boolean getValidInput()                 { return validInput;}
	public int     listOfFilesSize()			   { return filePool.getFileNameStack().size(); }
	

	//	##################Private methods##################  //
	private void inputDefualtCredentials(){

		credentials[0] = "./metadata/TEST/1999/";
		credentials[1] = "filing";
		credentials[2] = "localhost";
		credentials[3] = "3306";
		credentials[4] = "root";
		credentials[5] = "critterpower";       
		credentials[6] = "test";     
		validInput = true;
	}
	private void inputCredentials(){
		validInput = false;
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
			System.out.println("please enter XML OBJECT WRAPPER:");
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
		validInput=true;
	}
	
}