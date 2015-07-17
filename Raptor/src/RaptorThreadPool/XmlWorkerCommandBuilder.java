package RaptorThreadPool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

//Set the connection obj with setConnection before
public class XmlWorkerCommandBuilder {

    private String curTable;
    private String[] values;
    private String[] columns;
    private String   statement;
    private String   precompiledSet;
    private String   precompiledStatementStr;
    
    private LinkedList<String> commands;
    private Connection conn;
    private PreparedStatement psmt;

    
    public XmlWorkerCommandBuilder(){
        precompiledStatementStr = "INSERT INTO ";
    }
    
    public void buildInsertionStatement(String _table,String[] _keys, String[] _values){        
       columns  = _keys;
       values   = _values;
       curTable = _table;
       precompiledSet = precompiledSetBuilder(columns.length);
       
       precompiledStatementStr += curTable + " " + buildSet(columns) + " VALUES " + precompiledSet + ";";
       
       
       //init(psmt)
       try {psmt = (PreparedStatement) conn.prepareStatement(precompiledStatementStr);} catch (SQLException e){e.printStackTrace();}
       
       System.out.println(precompiledStatementStr);
       prepareStatement(psmt,values );
    }
    public void executeStatement(){
        if(psmt != null){
            try {psmt.executeUpdate();} catch (SQLException e) {e.printStackTrace();}
        }
    }
    
    
    //creates (a,b,c,d,e,...) sets for insertions stmts
    private String buildSet(String[] _values){
        
        StringBuilder builder = new StringBuilder();
        
        for(String value : _values){
            builder.append(value);
            builder.append(",");
        }
        
        builder.deleteCharAt(builder.length() - 1 );
        builder.insert(0, "(");
        builder.append(")");
        
        System.gc();
        return builder.toString();
    }
    
    //creates (?,?,?,?) for precompiled stmts
    private String precompiledSetBuilder(int numberOfArgs){
        
        String[] set = new String[numberOfArgs];
        for(int i=0;i<numberOfArgs;i++)
            set[i] = "?";
        return buildSet(set);
    }
    
    private void prepareStatement( PreparedStatement pt, String[] _values){
        for(int i=0;i<_values.length;i++){
            System.out.println(i);
            try{pt.setString(i+1, _values[i] );} catch (SQLException e) {e.printStackTrace();}
            System.out.print(_values[i] + " ::");
            System.out.println(pt.toString());
        }
      
    }
	
    public void setConnection(Connection _conn){conn = _conn;}
    public void setColumns(String[] _key){columns = _key;}
    public void setValues (String[] _values){values = _values;}
    

}

