package edu.uta.codeGenerator.command;

import java.awt.Point;


import edu.uta.codeGenerator.component.DCDComponent;
import edu.uta.codeGenerator.component.DesignClassDiagram;

public class MoveCommand implements Command {
	public MoveCommand(DesignClassDiagram dc) {
		this.sd = dc;
	}

	private DesignClassDiagram sd;
	private DCDComponent movingElements;
	private Point startPoint;
	private Point moveDest;
	private Point endPoint;

	@Override
	public void execute() {
	}

	public void moveElement(Point p) {
		if(!sd.isInsect(p.x, p.y)){
			movingElements.setxPos(startPoint.x);
			movingElements.setyPos(startPoint.y);
		}else{
			this.moveDest = p;
			movingElements.setxPos(moveDest.x - startPoint.x
					+ movingElements.getxPos());
			movingElements.setyPos(moveDest.y - startPoint.y
					+ movingElements.getyPos());
			movingElements.autoLayout(); 
			startPoint = moveDest;
		}
		
	}

	@Override
	public void setElement(DCDComponent e) {
		movingElements = e;
		startPoint = new Point((int)movingElements.getxPos(),
				(int)movingElements.getyPos());
	}

	/**
	 * recover old data
	 * */
	@Override
	public void undo() {
		// movingElements.setxPos(oldData.hashCode());
		// movingElements.setPoint(oldData.get(i).getX(),
		// oldData.get(i).getY());
	}

	public DesignClassDiagram getSd() {
		return sd;
	}

	public void setSd(DesignClassDiagram sd) {
		this.sd = sd;
	}

	public DCDComponent getMovingElements() {
		return movingElements;
	}

	public void setMovingElements(DCDComponent movingElements) {
		this.movingElements = movingElements;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public Point getMoveDest() {
		return moveDest;
	}

	public void setMoveDest(Point moveDest) {
		this.moveDest = moveDest;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}


}
