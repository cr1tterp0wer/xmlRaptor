package RaptorThreadPool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

//Set the connection obj with setConnection before
public class CommandBuilder {

    private String   curTable;
    private String   attrID;
    private String[] values;
    private String[] columns;
    private String   precompiledStatementStr;
    private boolean validInsertion;
    
    private Connection conn;
    private PreparedStatement psmt;

    
    public CommandBuilder(){
        attrID = "";
        validInsertion = true;
    }
    
    public boolean buildInsertionStatement(String _table,String[] _keys, String[] _values){   
        
        assert(_table != null );
        assert(_keys != null );
        assert(_values != null );
        assert(_keys.length == _values.length);
       validInsertion = true; 
       psmt    = null;
       columns  = _keys;
       values   = _values;
       curTable = _table;
       
       if(columns.length > 0 && values.length > 0){
           precompiledStatementStr ="INSERT INTO "+  curTable + " " + buildSet(columns);
           precompiledStatementStr += " VALUES " + precompiledSetBuilder(columns.length) + ";";
            
           //init(psmt)
           try {psmt = (PreparedStatement) conn.prepareStatement(precompiledStatementStr);} catch (SQLException e){e.printStackTrace();}
           
          // System.out.println(precompiledStatementStr);
           prepareStatement(psmt,values );
           validInsertion = false;
       }
       return validInsertion;
    }
    
    public void executeStatement(){
        if(psmt != null){
            try {psmt.executeUpdate();} catch (SQLException e) {e.printStackTrace();}
        }
    }
    
    //creates (a,b,c,d,e,...) sets for insertions stmts
    private String buildSet(String[] _items){
        
        StringBuilder builder = new StringBuilder();
        String[] items = _items;
        
        for(String item : items){
            builder.append(item);
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
            try{pt.setString(i+1, _values[i] );} catch (SQLException e) {e.printStackTrace();}
            //System.out.print(_values[i] + " ::");
           // System.out.println(pt.toString());
        }
      
    }
	
    public void setAttrID(String id){attrID = id;}
    public void setConnection(Connection _conn){conn = _conn;}
    public void setColumns(String[] _key){columns = _key;}
    public void setValues (String[] _values){values = _values;}
    
    public String getAttrID(){return attrID;}
    public PreparedStatement getPreparedStmt(){return psmt;}
    public boolean getValidInsertion(){return validInsertion;}
}

