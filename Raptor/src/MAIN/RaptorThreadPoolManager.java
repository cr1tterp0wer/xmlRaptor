package MAIN;

import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Hashtable;

import RaptorThreadPool.RaptorFileList;
import RaptorThreadPool.SQLConnectorRaptor;
import RaptorThreadPool.ThreadSpawner;


public class RaptorThreadPoolManager {

    /* [K] Do we need a higher-level credential? Couldn't we store this in
     * the connector alone? */
	private String[]                  credentials;   
    // [K] Redundant, though, SQLConnection module already has this.
	private final int                 NUM_OF_CREDS = 7;      //number of arguments credentials can have
	private boolean                   validInput   = false;  //make sure input from user for credentials is valid
	private SQLConnectorRaptor        connector;
	public  volatile RaptorFileList   filePool;  //Gets the list of files
	private volatile ThreadSpawner    spawner;

	public RaptorThreadPoolManager(){
		credentials      = new String[NUM_OF_CREDS];

		connector        = new SQLConnectorRaptor(this);
	}

	//Initialize the first threads
    /* [K] An exception is being thrown here; why not return a bool? */
    public void init() {
		spawner          = new ThreadSpawner( this);
        /* [K] Consider implementing a simple configuration file for credential
         * information. */
		inputCredentials();                       //get server credentials
        // [K] not clear; what's credentials[0]?
		filePool   = new RaptorFileList(credentials[0]); //get all the necessary files

		System.out.println("NUMBER OF VALID FILES BEGIN::"+filePool.getFileNameStack().size());

		if(filePool.getFileNameStack().isEmpty())        
			throw new EmptyStackException();  //No files? No go.

        return;
	}

    /* [K] Seems like a redundant kind of copy of credentials, as noted */
	public void begin() {
		connector.connect(credentials[0], credentials[1],
                          credentials[2], credentials[3],
                          credentials[4], credentials[5],
				          credentials[6]);

		if(getValidInput()) {
			//INIT the spawner()!
			spawner.init();
			spawner.start();
		} else {
			System.out.println("FATAL ERROR!");
        }

        return;
	}

	public void end() {
		//TODO:close up the threads
	}

    /* Maybe consider providing a recursive input function that
     * only returns input when it's valid, as opposed to using a flag
     * with setters and getters? */
	public void    setValidInput(boolean b)        { validInput = b;}
	public boolean getValidInput()                 { return validInput;}
	public int     listOfFilesSize()			   { return filePool.getFileNameStack().size(); }
	public         RaptorFileList getFilePool()    { return filePool;}
	public SQLConnectorRaptor getConnector()       { return connector; }

	//	##################Private methods##################  //

    /* [K] Build a hashtable indexed from values[0] ... values[n] */
    private Hashtable<Integer, String> buildTable(String... values)
    {
        Hashtable<Integer, String> ht = new Hashtable<Integer, String>();

        Integer count = new Integer(0);
        for(String v : values) {
            ht.put(count++, v);
        }

        return ht;
    }

	private void inputCredentials() {

        Hashtable<Integer, String> inputs = buildTable(
            "path", "xmlObject", "address", "port",
            "username", "pass", "database"
        );

		Scanner scan = new Scanner(System.in);
        inputDefaultCredentials();

		while(!validInput) {

            for(int i : inputs.keySet())
            {
                System.out.println("Please enter " + inputs.get(i) +
                                   " [Default = " + credentials[i] + "]: ");
                String tmp = scan.nextLine();
                if(tmp.length() > 0) {
                    credentials[i] = tmp;
                }
            }

            // [K] Should perform some sort of sanity check for inputs?
            validInput = true;
			//  sqlThread.init(credentials);
		}

        return;
	}

	private void inputDefaultCredentials() {
		credentials[0] = "./metadata/protos/";
		credentials[1] = "filing";
		credentials[2] = "localhost";
		credentials[3] = "3306";
		credentials[4] = "kevr";
		credentials[5] = "testkevr";
		credentials[6] = "test";
	}
}
