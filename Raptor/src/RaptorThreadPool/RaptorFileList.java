package RaptorThreadPool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class RaptorFileList {

    private String  folderPath;
    private File    folder;
    private File[]  files;                //array of files
    private Stack<String>    fileNames;   //Stack of file names ::String
    private LinkedList<File> listOfFiles; //arrayList of ALL files in dir
    
    public RaptorFileList(String dir){
        folderPath   = dir;
        folder       = new File(folderPath);   
        files        = folder.listFiles(); //put files in folder
        fileNames    = new Stack<String>();  
        listOfFiles  = new LinkedList();
        getListOfParsableFiles();
    }
    
    private List<File> getListOfParsableFiles(){
        
        //put files into linked list
        for(int i=0;i<files.length;i++)
        	listOfFiles.add(files[i]);
        
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
