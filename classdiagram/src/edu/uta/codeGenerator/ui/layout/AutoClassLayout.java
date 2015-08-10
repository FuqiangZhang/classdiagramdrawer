package edu.uta.codeGenerator.ui.layout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import edu.uta.codeGenerator.component.DCDAttribute;
import edu.uta.codeGenerator.component.DCDClass;
import edu.uta.codeGenerator.component.DCDComponent;
import edu.uta.codeGenerator.component.DCDMethod;

public class AutoClassLayout extends Layout implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6017701951740224969L;

	public ArrayList<DCDComponent>[] builtTree(DCDComponent parent) {
		ArrayList<DCDComponent> array[] = new ArrayList[2];

		Collection<DCDComponent> childs = parent.getChild().values();
		ArrayList<DCDComponent> attributes = new ArrayList<>();
		ArrayList<DCDComponent> methods = new ArrayList<>();

		for (DCDComponent child : childs) {
			if (child instanceof DCDAttribute) {
				attributes.add(child);
			}
			if (child instanceof DCDMethod) {
				methods.add(child);
			}
		}

		array[0] = attributes;
		array[1] = methods;
		return array;
	}

	@Override
	public void layoutContainer(DCDComponent parent) {
		autoLayoutInit(parent);
	}

	@Override
	public void autoLayoutInit(DCDComponent parent) {
		DCDClass cObj = (DCDClass) parent;
		ArrayList<DCDComponent>[] array = builtTree(parent);

		float attribtueStartY = cObj.getHeight() - cObj.getAttributeHeight()
				- cObj.getMethodHeight();
		float methodStartY = cObj.getHeight() - cObj.getMethodHeight();
		// attributes
		int i = 0;
		int j = 0;
		for (DCDComponent child : array[0]) {
			child.setxPos(cObj.getxPos());
			child.setyPos(cObj.getyPos() + attribtueStartY + i * 13 + 6);
			i++;
		}
		// methods
		for (DCDComponent child : array[1]) {
			child.setxPos(cObj.getxPos());
			child.setyPos(cObj.getyPos() + methodStartY + j * 13 + 6);
			j++;
		}
	}
}