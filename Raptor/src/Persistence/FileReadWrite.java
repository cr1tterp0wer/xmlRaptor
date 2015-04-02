package Persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


//Checks for the Persistant file to pick up where the db left off
//files must have extension .fqr
public class FileReadWrite {

	private final String  db_STORE = "db_STORE.fqr" ;
	
	private String path;
	private File file;
	private ArrayList<String> listOfFiles;
	
	public FileReadWrite(String p){
		path = p;
		listOfFiles = new ArrayList<String>();
		file = new File(p);
		if(!file.exists()){
			System.out.println("Backup File Does Not Exist, creating new Backup File...");
			createFile();
		}
		
	}
	
	public void addCompletedFile(String fileName){
		
	    // you want to output to file
	    // BufferedWriter writer = new BufferedWriter(new FileWriter(file3, true));
	    // but let's print to console while debugging
	    BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			        writer.write(fileName);
			        writer.newLine();
			 
			 writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	}
	
	//Returns an Arraylist of file names stored in the db
	public ArrayList<String> getCompletedFiles(){
		listOfFiles.clear();
		BufferedReader br = null;
		 
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(file));
 
			while ((sCurrentLine = br.readLine()) != null) {
				listOfFiles.add(sCurrentLine);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return listOfFiles;
	}
	
	public boolean searchCompletedFiles(String str){
		boolean isFound = false;
		
		ArrayList<String> files = getCompletedFiles();
		for(int i = 0; i< files.size();i++){
			if(files.get(i).equalsIgnoreCase(str)){
				isFound = true;
				break;
			}
		}
		
		return isFound;
	}
	public void createDirAndFile(){
		//"creating directory" 'etc'
		//createFile("filename");
	}
	private void createFile(){
		//creating file "db_STORE.fqr"
		try {
			file.createNewFile();
		} catch (IOException e) {e.printStackTrace();}
	}
}
