package test;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.uta.codeGenerator.component.DCDClass;
import edu.uta.codeGenerator.component.DesignClassDiagram;
import edu.uta.codeGenerator.controller.DiagramController;

public class Main {
	JFrame jf = new JFrame();
	
	public String TEMP_DIR = "/users/steve/Desktop";
	/** Where is your dot program located? It will be called externally. */
	public String DOT = "/usr/local/bin/dot"; // Linux
	
	public void init(){
		
		String TEMP_DIR = "/users/steve/Desktop";// set the temp path
		String DOT = "/usr/local/bin/dot"; // set the path of the Graphviz
		
		DesignClassDiagram dcd = new DesignClassDiagram();//create a class diagram
		/**
		 * create the class diagram elements
		 * */
		DCDClass c1 = new DCDClass("Manager");
		c1.setId("123");
		
		DCDClass c2 = new DCDClass("User");
		c2.setId("321");
		/**
		 * add the elements in to the diagram
		 * */
		dcd.addChild(c1);
		dcd.addChild(c2);
		// create the controller
		DiagramController dc = DiagramController.getInstance();
		//set the dot and temp_dir path
		dc.setDot(DOT);
		dc.setTempDir(TEMP_DIR);
		//set the class diagram to the controller
		dc.setDcd(dcd);
		//get the JPanel which will display the diagram on it
		JPanel p = dc.getPanel();
		//add the JPanel to your jframe and show it
		jf.add(p);
		jf.setVisible(true);
		
	}
	
	public static void main(String[] arg){
		Main m = new Main();
		m.init();
	}
}
