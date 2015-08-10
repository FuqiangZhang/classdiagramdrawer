package edu.uta.codeGenerator.ui.layout;

import java.io.Serializable;


public class Position implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4301574703638214453L;
	private float x;
	private float y;
	
	
	public Position(float x,float y){
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
}
