package edu.uta.codeGenerator.state;

import java.awt.Point;

import edu.uta.codeGenerator.component.DCDComponent;
import edu.uta.codeGenerator.controller.DiagramController;

public class InitState extends ControllerState {

	private static InitState init;

	public InitState() {
	}

	public static InitState getInstance() {
		if (init == null) {
			init = new InitState();
		}
		return init;
	}

	@Override
	public ControllerState mouseClicked(Point p) {
		DCDComponent selectElement = DiagramController.getInstance()
				.getSelectedElement(p.x, p.y, 0);

		if (selectElement != null) {
			DiagramController.getInstance().setSelectedElement(selectElement);
			return this;
		}
		return this;
	}

	@Override
	public ControllerState mousePressed(Point p) {
		DCDComponent selectElement = DiagramController.getInstance()
				.getSelectedElement(p.x, p.y, 1);
		if (selectElement != null) {
			DiagramController.getInstance().setSelectedElement(selectElement);
			return MoveStateStart.getInstance();
		}
		return this;
	}

	@Override
	public ControllerState mouseReleased(Point p) {
		DiagramController.getInstance().setSelectedElement(null);
		return this;
	}
}
