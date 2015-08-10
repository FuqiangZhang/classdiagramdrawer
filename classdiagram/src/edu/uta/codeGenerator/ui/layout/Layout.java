package edu.uta.codeGenerator.ui.layout;

import java.io.Serializable;
import java.util.ArrayList;

import edu.uta.codeGenerator.component.DCDComponent;

public abstract class Layout implements Serializable{

//	public abstract void addLayoutComponent(String name, DCDComponent comp);
//
//	public abstract void removeLayoutComponent(DCDComponent comp);
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2388876745008183184L;

	/**
	 * layout the container
	 * */
	public abstract void layoutContainer(DCDComponent parent);
	public abstract void autoLayoutInit(DCDComponent parent);
	

	public int getMaxHeight(ArrayList<DCDComponent> classesArray) {
		int arraysize = classesArray.size();
		int maxHeight = 0;
		for (int i = 0; i < arraysize; i++) {
			if (classesArray.get(i).getHeight() > maxHeight)
				maxHeight = (int) classesArray.get(i).getHeight();
		}
		return maxHeight;
	}

	public int getArrayHeight(ArrayList array) {
		int heightSize = 0;
		int arraySize = array.size();
		heightSize = 15 * arraySize;
		return heightSize;
	}

	public int getMaxWidth(ArrayList<DCDComponent> classesArray) {
		int arraysize = classesArray.size();
		int maxWidth = 0;
		for (int i = 0; i < arraysize; i++) {
			if (classesArray.get(i).getWidth() > maxWidth)
				maxWidth = (int) classesArray.get(i).getWidth();
		}
		return maxWidth;
	}

	public int findLongestStringInArray(ArrayList stringArray) {
		int longestString = 0;
		int tempStringLen = 0;
		int arraySize = stringArray.size();

		for (int i = 0; i < arraySize; i++) {
			tempStringLen = stringArray.get(i).toString().length();
			if (tempStringLen > longestString)
				longestString = tempStringLen;
		}
		return longestString;
	}
}
