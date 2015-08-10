package edu.uta.codeGenerator.command;

import java.io.Serializable;
import java.util.Vector;

public class CommandHistoryManager implements CommandManager,Serializable{


	private static final long serialVersionUID = 1L;

	Vector<Command> undoList = new Vector<Command>();
	Vector<Command> redoList = new Vector<Command>();

	public CommandHistoryManager() {}

	@Override
	public void callCommand(Command cmd) {
		undoList.add(cmd);
		cmd.execute();
	}

	@Override
	public void clearAllCommand() {
		undoList.clear();
		redoList.clear();
	}

	@Override
	public void undo() {
		if (undoList.size() <= 0)
			return;
		Command cmd = ((Command) (undoList.get(undoList.size() - 1)));
		cmd.undo();
		undoList.remove(cmd);
		redoList.add(cmd);
	}

	@Override
	public void redo() {
		if (redoList.size() <= 0)
			return;
		Command cmd = ((Command) (redoList.get(redoList.size() - 1)));
		cmd.execute();
		redoList.remove(cmd);
		undoList.add(cmd);
	}

	public Vector<Command> getUndoList() {
		return undoList;
	}

	public void setUndoList(Vector<Command> undoList) {
		this.undoList = undoList;
	}

	public Vector<Command> getRedoList() {
		return redoList;
	}

	public void setRedoList(Vector<Command> redoList) {
		this.redoList = redoList;
	}
}