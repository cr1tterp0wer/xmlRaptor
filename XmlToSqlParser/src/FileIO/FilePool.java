package FileIO;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class FilePool {
	private String  folderPath;
    private File    folder;
    //private File[]  files;                //array of files
    private ArrayList<File> files;
    private Stack<String>    fileNames;   //Stack of file names ::String
    private LinkedList<File> listOfFiles; //arrayList of ALL files in dir
    
    public FilePool(String dir){
        folderPath   = dir;
        files = new ArrayList<File>();
        getListOfFiles(folderPath,files);
        
        folder       = new File(folderPath);   
        //files        = folder.listFiles(); //put files in folder
        fileNames    = new Stack<String>();  
        listOfFiles  = new LinkedList<File>();
        getListOfParsableFiles();
    }
    
    private File[] getListOfFiles(String fp,ArrayList<File> fl ){
    	File d = new File(fp);
    	File[] fList = d.listFiles();
    	
    	for(File f: fList){
    		if(f.isFile())
    			fl.add(f);
    		else
    			getListOfFiles(f.getAbsolutePath(),fl);
    	}
    	
    	return fList;
    }
    
    private List<File> getListOfParsableFiles(){
        
        //put files into linked list
        for(int i=0;i<files.size();i++)
        	listOfFiles.add(files.get(i));
        
        //Filter out Directories
        for(File file: files){   
            if(file.isDirectory())
            	listOfFiles.remove(file);
            else
                continue;
        }
        
        //Filter out non-xml files
        if(!(listOfFiles==null) && !(listOfFiles.size() <= 0)){
            for(int i = 0; i < listOfFiles.size(); i++){
                if(((File) listOfFiles.get(i)).isFile() && getExtension(listOfFiles.get(i).toString()).equalsIgnoreCase("xml")){
                    fileNames.push(listOfFiles.get(i).toString());
                }
                else
                    listOfFiles.remove(i);
            }
        }
        return listOfFiles;
    }
    
    public List<File> getFiles(){  return listOfFiles; }  //returns list of File OBJS
    public Stack<String> getFileNameStack(){ return fileNames;}
    
    
//	##################Private methods##################  //
    private String getExtension(String ext){
        String extension = "";
        int i = ext.lastIndexOf('.');
        if (i >= 0) extension = ext.substring(i+1);
        
        return extension;
    }
    
}
