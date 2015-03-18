package RaptorThread;

import java.io.File;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Tree.XMLTreeHandler;
import RaptorThread.XMLThread;


public class RaptorThreadHandler  {

	private String   path;
	private String   serverAddress;
	private String   parentObject;
	private String   pass;
	private String   username;
	private String   dbName;
	private String   port;
	private Scanner  scan;
	private boolean  canParse;
	private XMLTreeHandler tree;
	
 	private ExecutorService executor;
	private Stack<String> fileNames;
	private File[]        listOfFiles;
	
	public RaptorThreadHandler(){
		scan     = new Scanner(System.in);
		canParse = false;
	}
	
	public void initDefaultInput(){
		path          = "./metadata/";
		parentObject  = "filing";
		serverAddress = "localhost";
		port          = "3306";
		username      = "root";
		pass          = "critterpower";
		dbName        = "web_app_development";	
	}
	
	public void getInput(){

		System.out.println("please enter PATH(skip if DROPPING ALL):");
		path          = scan.nextLine();
		System.out.println("please enter XML OBJECT(skip if DROPPING ALL):");
		parentObject  = scan.nextLine();
		System.out.println("please enter SERVER ADDRESS:");
		serverAddress = scan.nextLine();
		System.out.println("please enter PORT:");
		port          = scan.nextLine();
		System.out.println("please enter USERNAME:");
		username      = scan.nextLine();
		System.out.println("please enter PASS:");
		pass          = scan.nextLine();
		System.out.println("please enter DATABASENAME:");
		dbName        = scan.nextLine();
	}
	
	
	public void initDir(){
		
		File folder          = new File(path);
		listOfFiles          = folder.listFiles();
		fileNames            = new Stack();
		int numValidXMLfiles = 0;
		canParse             = false;
		
		if(!(listOfFiles==null) && !(listOfFiles.length == 0)){
			for(int i = 0; i < listOfFiles.length; i++){
				if(listOfFiles[i].isFile() && getExtension(listOfFiles[i].toString()).equalsIgnoreCase("xml")){
					fileNames.push(listOfFiles[i].toString());
					numValidXMLfiles++;
				}
			}
		}
		
		if(numValidXMLfiles<=0){
			canParse = false;
			return;
		}else{
			executor = Executors.newFixedThreadPool(4);
			canParse = true;
		}
	}
	
	public void parseAndInject(){
		if(!canParse){
			System.out.println("ERROR: NO FILES FOUND");
			return;
		}
		else{
			executor.execute(new XMLThread(this, parentObject));
			executor.execute(new XMLThread(this, parentObject));
			executor.execute(new XMLThread(this, parentObject));
			executor.execute(new XMLThread(this, parentObject));
			executor.shutdown();
		}
	}
	
	private String getExtension(String _str) {
		String extension = "";
		int i = _str.lastIndexOf('.');
		if (i >= 0) {
		    extension = _str.substring(i+1);
		}
		return extension;
	}
	
	public synchronized String popFile(){
		return fileNames.pop();
	}
	public synchronized boolean isFileNamesEmpty(){
		return fileNames.empty();
	}
	
	
	//TESTS-Assertions
	private void test(){
		assert path          != null;
		assert path          != "";
		assert serverAddress != null;
		assert serverAddress != "";
		assert parentObject  != null;
		assert parentObject  != "";
		assert pass          != null;
		assert pass          != "";
		assert username      != null;
		assert username      != "";
		assert dbName        != null;
		assert dbName        != "";
		assert port          != null;
		assert port          != "";
	}
}
