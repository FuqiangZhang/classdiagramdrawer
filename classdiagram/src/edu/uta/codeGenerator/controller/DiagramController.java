package edu.uta.codeGenerator.controller;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import edu.uta.codeGenerator.command.CommandHistoryManager;
import edu.uta.codeGenerator.command.MoveCommand;
import edu.uta.codeGenerator.component.DCDComponent;
import edu.uta.codeGenerator.component.DesignClassDiagram;
import edu.uta.codeGenerator.ui.layout.ClassPanel;
import edu.uta.codeGenerator.ui.layout.GraphViz;

public class DiagramController {
	DesignClassDiagram dcd;
	CommandHistoryManager chm = new CommandHistoryManager();
	private static DiagramController dc;

	private DiagramController() {
	}
	
	public void setTempDir(String tempdir){
		GraphViz.TEMP_DIR = tempdir;
	}
	public void setDot(String dot){
		GraphViz.DOT = dot;
	}
	
	ClassPanel cp = new ClassPanel();
	
	public ClassPanel getPanel(){
		cp.setClassDiagram(dcd);
		return cp;
	}

	public static DiagramController getInstance() {
		if (dc == null) {
			dc = new DiagramController();
		}
		return dc;
	}

	public void undo() {
		chm.undo();
	}

	public void redo() {
		chm.redo();
	}

	public void removeElement(DCDComponent e) {
	}

	private DCDComponent selectedElement;
	/***
	 * moving a element - begin
	 * */
	private MoveCommand moveCommand = null;

	// move start
	public void moveStart(Point p) {
		moveCommand = new MoveCommand(dcd);
		moveCommand.setElement(selectedElement);
	}

	public void moveElement(Point p) {
		if (selectedElement != null && moveCommand != null) {
			moveCommand.moveElement(p);
			cp.updateUI();
		}
	}

	public void moveElementEnd() {
		moveCommand.execute();
		chm.callCommand(moveCommand);
		cp.updateUI();
	}

	public DCDComponent getSelectedElement(int x_in, int y_in, int depth) {
		return dcd.getSelectedComponent(x_in, y_in, depth);
	}

	public DesignClassDiagram getClassDiagram() {
		return this.dcd;
	}

	public void setSelectedElement(DCDComponent se) {
		selectedElement = se;
	}

	public void setDcd(DesignClassDiagram dcd) {
		this.dcd = dcd;
		dcd.autoLayout();
	}

	public CommandHistoryManager getChm() {
		return chm;
	}

	public void setChm(CommandHistoryManager chm) {
		this.chm = chm;
	}

	public MoveCommand getMoveCommand() {
		return moveCommand;
	}

	public void setMoveCommand(MoveCommand moveCommand) {
		this.moveCommand = moveCommand;
	}

	public DCDComponent getSelectedElement() {
		return selectedElement;
	}
	
	@SuppressWarnings("resource")
	public void save(String path){
		try {
			FileOutputStream fos = new FileOutputStream(new File(path));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(dcd);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
