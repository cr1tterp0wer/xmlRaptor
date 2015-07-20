package RaptorThreadPool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.ReflectiveOperationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mysql.jdbc.PreparedStatement;

public class SqlWorker implements Runnable{

 
    
    private int           workerID;
    private ThreadSpawner spawner;
    private ArrayList<PreparedStatement> ps;
    
    public SQLConnectorRaptor sqlConnector;

    //Object data = list of precompiled statements
    public SqlWorker(int id, ThreadSpawner s, ArrayList<PreparedStatement> _ps){
        workerID     = id;
        spawner      = s;
        ps = _ps;
        sqlConnector = spawner.getManager().getConnector();
        
        
    }
    
    @Override
    public void run(){
    	sqlInject();
    	finish();
    }

    public void finish(){
       System.out.println(this + "::" + workerID);
       spawner.incrementFinishedSql();
       spawner.shutdownSqlPool();
    }

    private void sqlInject(){
//        sqlConnector.injectXmlBlob(this.tree);	
        for(int i=0;i<ps.size();i++){
          try {ps.get(i).executeUpdate();
               //System.out.println(ps.get(i).toString());
          } catch (SQLException e) { 
              
              int errorCode = e.getErrorCode();
              System.out.println("ERROR CODE:" + errorCode + " ::CREATING NEW COLUMN!");
              switch(errorCode){
                  case 1054: handleMissingColumnException(e.getMessage(),i);
                  break;
              }
          }
        }
    }
  
     private void handleMissingColumnException(String errorString, int iterator){
        String[] ar = errorString.split(" ");
        String table = "";
        String column= "";
        
        //get tableName from ps
        ar = ps.get(iterator).toString().split("INSERT INTO");
        ar = ar[1].split(" ");
        table = ar[1];
        
        //get column name
        ar  = errorString.split("'");
        column = ar[1];
        //create <column> in <table>
        //try to re-execute the pstmt
        String update = "ALTER TABLE " + table + " ADD " + column + " TEXT;";
        try {
            sqlConnector.executeUpdate(sqlConnector.getConnection(),  update);
            ps.get(iterator).executeUpdate();
        } catch (SQLException e) {handleMissingColumnException(e.getMessage(), iterator);}
    }
    
    
}

