package MAIN;

import Persistence.FileObject;


public class Raptor {

	static RaptorThreadPoolManager pool;
	
	public static void main(String[] args) {
//		pool = new RaptorThreadPoolManager();
//		pool.init();
//		pool.begin();
//		pool.end();
		
		
		//fileReadWrite inside FileOBJ, use for knowing where to begin parsing
		FileObject fileObj = new FileObject("./metadata/TEST/db_STORE.fqr");
		print(fileObj.canParseDirectory() + "");
		
		for(int i=0;i<fileObj.getSize();i++)
			print(fileObj.popFileNames());
	}
	public static void print(String s){
		System.out.println(s);
	}
}
