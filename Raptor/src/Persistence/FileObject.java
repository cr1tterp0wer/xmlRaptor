package Persistence;

import java.io.File;
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
	
	public boolean canParseDirectory(){
		
		if(!(listOfFiles==null) && !(listOfFiles.length == 0)){
			for(int i = 0; i < listOfFiles.length; i++){
				if(listOfFiles[i].isFile() && getExtension(listOfFiles[i].toString()).equalsIgnoreCase("xml")){
					fileNames.push(listOfFiles[i].toString());
					numFiles++;
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
	
	public String popFileNames(){return fileNames.pop();}
	public int getSize(){return fileNames.size();}
	
}









