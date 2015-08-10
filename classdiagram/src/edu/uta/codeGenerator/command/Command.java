package edu.uta.codeGenerator.command;

import edu.uta.codeGenerator.component.DCDComponent;

public interface Command {
	public void execute();

	public void undo();

	public void setElement(DCDComponent e);

}
