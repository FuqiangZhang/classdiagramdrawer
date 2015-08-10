package edu.uta.codeGenerator.command;

public interface CommandManager {
	public void callCommand(Command cmd);

	public void clearAllCommand();

	public void undo();

	public void redo();
}
