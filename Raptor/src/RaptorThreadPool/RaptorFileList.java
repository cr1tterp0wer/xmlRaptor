package RaptorThreadPool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class RaptorFileList {

    private String  folderPath;
    private File    folder;
    private int     numFiles;
    private File[]  files;                //array of files
    private Stack<String>    fileNames;   //Stack of file names ::String
    private ArrayList<File>  listOfFiles; //arrayList of ALL files in dir
    private boolean          canParse;    //can we parse this folder?
    private String           extension;   //file extension buffer
    
    public RaptorFileList(String dir){
        folderPath   = dir;
        folder       = new File(folderPath);   
        fileNames    = new Stack<String>();  
        numFiles     = 0;
        canParse     = false;
        
        files = folder.listFiles();
        listOfFiles  = toArrayList(files);
        getListOfParsableFiles();
    }
    
    private List<File> getListOfParsableFiles(){
        numFiles=0;
        
        for(File file: files){
            if(file.isDirectory())
                continue;
            else
                listOfFiles.add(file);
        }
        
        if(!(listOfFiles==null) && !(listOfFiles.size() <= 0)){
            for(int i = 0; i < listOfFiles.size(); i++){
                if(((File) listOfFiles.get(i)).isFile() && getExtension(listOfFiles.get(i).toString()).equalsIgnoreCase("xml")){
                    fileNames.push(listOfFiles.get(i).toString());
                    numFiles++;
                }
                else
                    listOfFiles.remove(i);
            }
        }
        return listOfFiles;
    }
    
    public int       getNumValidFiles(){ return numFiles;    }
    public List<File> getListOfFiles(){  return listOfFiles; }
    public Stack<String> getListOfFileNames(){ return fileNames;}
    private String getExtension(String ext){
        String extension = "";
        int i = ext.lastIndexOf('.');
        if (i >= 0) extension = ext.substring(i+1);
        
        return extension;
    }
    
    private ArrayList<File> toArrayList(File[] f){
        ArrayList<File> result = new ArrayList<File>();
        
        for(int i=0;i<f.length;i++)
            result.add(f[i]);
        
        return result;
    }
    
    
  
    
    
}
