package Persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class FileObject {
	
	private Stack<String> fileNames;
	private File[]        listOfFiles;
	private File    folder;
	private int     numFiles;
	private boolean canParse;
	private FileReadWrite fileIO;
	
	public FileObject(String p){
		
		folder      = new File(p);
		fileIO      = new FileReadWrite(p);
		listOfFiles = folder.listFiles();
		fileNames   = new Stack<String>();
		numFiles    = 0;
		canParse    = false;
		
	}
	
	public void parseDBStore(){
		
		numFiles=0;
		
		//connect to file, put contents in listOfFiles
		try{
			// Open the file
			FileInputStream fstream = new FileInputStream(folder);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
	
			String strLine;
	
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
			  System.out.println (strLine);
			}
	
			//Close the input stream
			br.close();
		
		}catch(IOException e){e.printStackTrace();}
		

		if(!(listOfFiles==null) && !(listOfFiles.length == 0)){
			for(int i = 0; i < listOfFiles.length; i++){
				if(listOfFiles[i].isFile() && getExtension(listOfFiles[i].toString()).equalsIgnoreCase("xml")){
					fileNames.push(listOfFiles[i].toString());
					numFiles++;
				}
			}
		}
	}
	
	public boolean canParseDirectory(){
		
		if(!(listOfFiles==null) && !(listOfFiles.length == 0)){
			for(int i = 0; i < listOfFiles.length; i++){
				if(listOfFiles[i].isFile() && getExtension(listOfFiles[i].toString()).equalsIgnoreCase("xml")){
					canParse = true;
				}
			}
		}
		
		if(numFiles > 0)
			canParse = true;
		else
			canParse = false;
		return canParse;
	}
	
	private String getExtension(String _str) {
		String extension = "";
		int i = _str.lastIndexOf('.');
		if (i >= 0) {
		    extension = _str.substring(i+1);
		}
		return extension;
	}
	
	public synchronized boolean isFileNamesEmpty(){
		return fileNames.empty();
	}
	
	//TODO: REMOVE FILENAMES.equals WHAT IS ALREADY IN db_STORE.fqr!!!!
	public void syncFiles(){
		Stack<String> tempStack = (Stack<String>)fileNames.clone();
		
		fileNames.push("lkajsdflkjs");
		System.out.println(fileNames.pop());
		
			tempStack.push("laksdjfl");
		    String r = tempStack.pop();
			System.out.println(r);
				
	}
	
	private List<String> stackToList(Stack<String> s){
		
		List<String> list = new ArrayList<String>();
		
		//for(int i=0;i<tempStack.capacity();i++)
			System.out.println(s.pop());
			//list.add(r.pop());
		return list;	
	}
	
	public String popFileNames(){return fileNames.pop();}
	public int getSize(){return fileNames.size();}
	
}









