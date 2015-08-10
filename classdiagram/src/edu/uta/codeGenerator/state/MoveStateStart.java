package edu.uta.codeGenerator.state;

import java.awt.Point;

import edu.uta.codeGenerator.controller.DiagramController;


public class MoveStateStart extends ControllerState {

	private static MoveStateStart instance;

	private MoveStateStart() {

	}

	public static MoveStateStart getInstance() {
		if (instance == null) {
			instance = new MoveStateStart();
		}
		return instance;
	}

	@Override
	public ControllerState mouseDragged(Point p) {
		DiagramController.getInstance().moveStart(p);
		return MoveStateEnd.getInstance();
	}

	@Override
	public ControllerState mouseReleased(Point p) {
		DiagramController.getInstance().setSelectedElement(null);
		return InitState.getInstance();
	}
}