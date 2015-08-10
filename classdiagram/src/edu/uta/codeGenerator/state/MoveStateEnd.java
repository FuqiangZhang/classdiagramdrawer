package edu.uta.codeGenerator.state;

import java.awt.Point;

import edu.uta.codeGenerator.controller.DiagramController;


public class MoveStateEnd extends ControllerState{

	private static MoveStateEnd instance;
	
	private MoveStateEnd() {
		
	}
	
	public static MoveStateEnd getInstance(){
		if(instance == null){
			instance = new MoveStateEnd();
		}
		return instance;
	}
	
	@Override
	public ControllerState mouseReleased(Point p){
		// moved over
		DiagramController.getInstance().moveElementEnd();
		DiagramController.getInstance().setSelectedElement(null);
		return InitState.getInstance();
	}
	
	@Override 
	public ControllerState mouseDragged(Point p){
		// moving
		DiagramController.getInstance().moveElement(p);
		return this;
	}
}