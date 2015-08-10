package edu.uta.codeGenerator.state;

import java.awt.Point;

public class ControllerState {

	public ControllerState() {
	}
	
	public ControllerState selectBtnClicked(){
		return InitState.getInstance();
	}
	
	public ControllerState stateBtnClicked(){
		return this;
	}
	
	public ControllerState transBtnClicked(){
		return this;
	}
	
	public ControllerState mouseClicked(Point p){
		return this;
	}
	
	public ControllerState mouseDragged(Point p){
		return this;
	}
	
	public ControllerState mousePressed(Point p){
		return this;
	}
	
	public ControllerState mouseReleased(Point p){
		return this;
	}
	
	public ControllerState mouseMoved(Point p){
		return this;
	}
}
