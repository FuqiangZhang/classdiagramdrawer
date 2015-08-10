package edu.uta.codeGenerator.state;

import java.awt.Point;

public class EditDiagramController {

	public EditDiagramController() {
		cs = new InitState();
	}
	
	private ControllerState cs;
	
	public void selectBtnClicked(){
		cs = cs.selectBtnClicked();
		System.out.println(cs.getClass().getName());
	}
	
	public void stateBtnClicked(){
		cs = cs.stateBtnClicked();
		System.out.println(cs.getClass().getName());
	}
	
	public void transBtnClicked(){
		System.out.println(cs.getClass().getName());
		cs = cs.transBtnClicked();
		System.out.println(cs.getClass().getName());
	}
	
	public void mouseClicked(Point p){
		cs = cs.mouseClicked(p);
		System.out.println(cs.getClass().getName());
	}
	
	public void mouseDragged(Point p){
		cs = cs.mouseDragged(p);
	}
	
	public void mousePressed(Point p){
		cs = cs.mousePressed(p);
	}
	
	public void mouseReleased(Point p){
		cs = cs.mouseReleased(p);
	}
	public void mouseMoved(Point p){
		cs = cs.mouseMoved(p);
	}
}
