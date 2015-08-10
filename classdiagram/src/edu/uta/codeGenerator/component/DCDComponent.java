package edu.uta.codeGenerator.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import edu.uta.codeGenerator.ui.layout.AutoLayout;
import edu.uta.codeGenerator.ui.layout.Layout;




public abstract class DCDComponent implements  Serializable{

	protected String id = "";
	protected float xPos;
	protected float yPos;
	protected float width;
	protected float height;
	protected int nameHeight = 15;
	protected int line = 1;
	protected Layout mLayout;

	protected String name = "";
	protected String access = "";
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAccess(String visvible) {
		this.access = visvible;
	}

	public String getAccess() {
		return access;
	}
	public DCDComponent() {
	}


	protected Hashtable<String,DCDComponent> childList = new Hashtable<String,DCDComponent>();
	
	public Hashtable<String,DCDComponent> getChild() {
		return childList;
	}

	public void setLayout(Layout layout) {
		this.mLayout = layout;
	}

	public void autoLayout() {
		if (mLayout == null){
			mLayout = new AutoLayout();
		}
			mLayout.autoLayoutInit(this);
		for (DCDComponent child : childList.values()) {
			child.autoLayout();
		}
	}


	public abstract String toDOT();
	public abstract void parseDOT(String dot);
	/**
	 * check whether the point is in the component
	 * */
	public boolean isInsect(int x, int y) {
		if (x >= xPos && x <= xPos + width && y >= yPos && y <= yPos + height) {
			return true;
		}
		return false;
	}

	/**
	 * get the selected component in the parent or itself
	 * */
	public DCDComponent getSelectedComponent(int x_in, int y_in, int depth) {
		if (this.isInsect(x_in, y_in)) {
			for (DCDComponent element : childList.values()) {
				DCDComponent e = element
						.getSelectedComponent(x_in, y_in, depth);
				if (e != null) {
					System.out.println(e);
					return e;
				}
			}
			return this;
		}
		return null;
	}

	/**
	 * draw methods -----start-----
	 * */
	public void draw(Graphics2D graphic) {
		drawString(graphic, toString(), xPos, yPos);
	}

	public void fill(Graphics2D graphic, Shape shape) {
		graphic.fill(shape);
	}

	public boolean drawImage(Graphics2D graphic, Image image, int x, int y) {
		boolean result = graphic.drawImage(image, x, y, null);
		return result;
	}

	public void changeColor(Graphics2D graphic, Color c) {
		graphic.setColor(c);
	}

	public void drawString(Graphics2D graphic, String text, float x, float y) {
		graphic.setFont(new Font("Tahoma", Font.BOLD, 12));
		graphic.drawString(text, x, y);
	}

	public void drawBlock(Graphics2D graphic, float w, float h, float x, float y) {
		graphic.draw(new Rectangle2D.Double(x, y,w,h));
		graphic.setColor(Color.white);
		graphic.fill(new Rectangle2D.Double(x, y,w,h));
		graphic.setColor(Color.black);
	}

	public void drawLine(Graphics2D graphic, float x1, float y1, float x2, float y2) {
		graphic.draw(new Line2D.Float(x1, y1, x2, y2));
	}

	/**
	 * draw methods -----end-----
	 * */

	/**
	 * setter getter methods
	 * */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}


	public void setHeight(float height) {
		this.height = height;
	}

	public void setPosition(float x, float y){
		this.xPos = x;
		this.yPos = y;
	}
	
	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public float getyPos() {
		return yPos;
	}


	public int getNameHeight() {
		return nameHeight;
	}

	public void setNameHeight(int nameHeight) {
		this.nameHeight = nameHeight;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public Layout getmLayout() {
		return mLayout;
	}

	public void setmLayout(Layout mLayout) {
		this.mLayout = mLayout;
	}

	public Hashtable<String, DCDComponent> getChildList() {
		return childList;
	}

	public void setChildList(Hashtable<String, DCDComponent> childList) {
		this.childList = childList;
	}

}
