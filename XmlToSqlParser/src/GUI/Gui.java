package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Gui {
	private Frame f;
	private JPanel panel;
	private String[] credentials;
	private boolean validInput;
	private JTextField pathField, wrapperField,
	                   addressField, portField, 
	                   userField, passField, dbnameField;
	private JLabel pathLabel,wrapperLabel,
	               addressLabel, portLabel,
	               userLabel,passLabel,dbnameLabel;
	
	private Container container;
	GridBagConstraints c;

	public Gui(){
		f     = new Frame("XML Raptor");
		panel = new JPanel();
		container = f.getContentPane();
		
		container.setLayout(new GridBagLayout());
		container.setBackground(UIManager.getColor("control"));		
		c = new GridBagConstraints();
	}

	public void init(){

		//Frame Setup
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable( false );
		f.setLocationRelativeTo(null);
		f.setPreferredSize(new Dimension(450,200));
		f.setBackground(Color.gray);
		
		
		panel.setLayout(new GridLayout(7,1));
		
	    //Content Setup
		
		   
        //labels
        pathLabel    = new JLabel("Path:");        
        wrapperLabel = new JLabel("Wrapper Class:");
        addressLabel = new JLabel("DB Address:");
        portLabel    = new JLabel("Port:");
        userLabel    = new JLabel("Username:");
        passLabel    = new JLabel("Password:");
        dbnameLabel  = new JLabel("DB Name:");
        

		//textfields
		pathField     = new JTextField();
	    wrapperField  = new JTextField();
        addressField  = new JTextField();
        portField     = new JTextField();
        userField     = new JTextField();
        passField     = new JTextField();
        dbnameField   = new JTextField();
       
    
        panel.add(pathLabel,c);
        panel.add(pathField,c);
        
        panel.add(wrapperLabel,c);
        panel.add(wrapperField,c);
        
        panel.add(addressLabel,c);
        panel.add(addressField,c);
        
        panel.add(portLabel,c);
        panel.add(portField,c);
        
        panel.add(userLabel,c);
        panel.add(userField,c);
        
        panel.add(passLabel,c);
        panel.add(passField,c);
        
        panel.add(dbnameLabel,c);
        panel.add(dbnameField,c);

		f.add(panel);
		f.pack();
		f.setVisible(true);

	}

	//	##################Private methods##################  //
	private void inputDefualtCredentials(){

		credentials[0] = "./metadata/TEST/1999/";
		credentials[1] = "filing";
		credentials[2] = "localhost";
		credentials[3] = "3306";
		credentials[4] = "root";
		credentials[5] = "critterpower";       
		credentials[6] = "test";     
		validInput = true;
	}
	private void inputCredentials(){
		validInput = false;
		while( !validInput){
			System.out.println("Would you like to launch xmlRaptor y/n");

			Scanner scan = new Scanner(System.in);
			String input = scan.nextLine();
			if(input.charAt(0) != 'y'){
				System.out.println("Exiting...Goodbye");
				System.exit(1);
			}

			System.out.println("please enter PATH:");
			credentials[0]          = scan.nextLine();
			System.out.println("please enter XML OBJECT WRAPPER:");
			credentials[1]          = scan.nextLine();
			System.out.println("please enter SERVER ADDRESS:");
			credentials[2]          = scan.nextLine();
			System.out.println("please enter PORT:");
			credentials[3]          = scan.nextLine();
			System.out.println("please enter USERNAME:");
			credentials[4]          = scan.nextLine();
			System.out.println("please enter PASS:");
			credentials[5]          = scan.nextLine();
			System.out.println("please enter DATABASENAME:");
			credentials[6]          = scan.nextLine();
		}
		validInput=true;
	}














}
