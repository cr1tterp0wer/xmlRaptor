package MAIN;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Thread.ThreadPool;

public class XmlToSqlParser {

	public static void main(String[] args){
    
		XmlParserController xmlController = new XmlParserController();
		xmlController.init();
		xmlController.begin();
		xmlController.shutdown();
	   
	}

}
