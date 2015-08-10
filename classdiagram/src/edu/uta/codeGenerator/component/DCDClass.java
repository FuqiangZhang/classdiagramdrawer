package edu.uta.codeGenerator.component;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.uta.codeGenerator.ui.layout.AutoClassLayout;

public class DCDClass extends DCDComponent {

	@Override
	public boolean equals(Object obj) {

		try {
			DCDClass other = (DCDClass) obj;
			return other.getName().equalsIgnoreCase(this.getName());
		} catch (RuntimeException e) {
			return false;
		}
	}

	private int attributeHeight = 0;
	private int methodHeight = 0;


	public DCDClass(String className) {
		width = className.length() * 7;
		this.xPos = 0;
		this.yPos = 0;
		setName(className);
		this.height = nameHeight + line + line + 13;
		this.setLayout(new AutoClassLayout());
	}

	public void addChild(DCDComponent child) {
		width = child.getWidth() < width ? width : child.getWidth();
		height = height + child.getHeight();
		this.childList.put(child.getId(), child);
		child.setId(this.getId() + (int) (childList.size() - 1));
	}

	public void setAccess(String visvible) {
		this.access = visvible;
	}

	public String getAccess() {
		return access;
	}

	public void movebyCell() {
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	@Override
	public void draw(Graphics2D graphic) {

		Graphics2D g2d = (Graphics2D) graphic;
		g2d.setStroke(new BasicStroke(2));

		float blockHeight = height;
		// draws the block
		drawBlock(g2d, width, blockHeight, xPos, yPos);
		// draws the class name
		drawString(g2d, getName(), xPos + 5, yPos + nameHeight);
		// between name and attribute
		drawLine(g2d, xPos, yPos + nameHeight + line, xPos + width, yPos
				+ nameHeight + line);
		// draws the class attributes
		for (DCDComponent child : childList.values()) {
			child.draw(g2d);
		}
		// between attribute and methods
		drawLine(graphic, xPos, yPos + nameHeight + line + this.attributeHeight
				+ line, xPos + width, yPos + nameHeight + line
				+ this.attributeHeight + line);
	}

	public int getAttributeHeight() {
		return attributeHeight;
	}

	public void setAttributeHeight(int attributeHeight) {
		this.attributeHeight = attributeHeight;
	}

	public int getMethodHeight() {
		return methodHeight;
	}

	public void setMethodHeight(int methodHeight) {
		this.methodHeight = methodHeight;
	}

	@Override
	public String toDOT() {

		String result = "";
		result = getId() + " [label=\"{" + getName() + "|";
		StringBuilder sbAll = new StringBuilder();
		StringBuilder sbAttr = new StringBuilder();
		StringBuilder sbMethod = new StringBuilder();
		for (DCDComponent child : getChild().values()) {

			if (child instanceof DCDAttribute) {
				sbAttr.append(child.toString());
				sbAttr.append("\\l");
			}
			if (child instanceof DCDMethod) {
				sbMethod.append(child.toString());
				sbMethod.append("\\l");
			}
		}

		sbAttr.append("sss");
		sbAttr.append("|");
		sbAll.append(result);
		sbAll.append(sbAttr);
		sbAll.append(sbMethod);
		sbAll.append("}\"]");
		return sbAll.toString();
	}

	@Override
	public void parseDOT(String dot) {
		String graphREX = "graph\\s*\\[bb=\"0,0,(\\S*),(\\S*)\"\\]";
		Pattern patternGraph = Pattern.compile(graphREX);
		Matcher matcherGraph = patternGraph.matcher(dot);
		float graph_height = 0;
		// get the height and width of the diagram.
		if (matcherGraph.find()) {
			graph_height = Float.valueOf(matcherGraph.group(2)) + 150;
		}

		String classREX = getId()
				+ "\\s*\\[height=(\\d+\\.?\\d*),[^\\]]*pos=\"(\\d+\\.?\\d+,\\d+\\.?\\d+)\",\\s*rects=\"([^\"]*)\",\\s*width=(\\d+\\.?\\d*)\\];";
		Pattern patternClass = Pattern.compile(classREX);
		Matcher m = patternClass.matcher(dot);
		if (m.find()) {
			String pose_Str = m.group(2);
			String rects = m.group(3);
			String[] pos = pose_Str.split(","); // 198.5,189.5

			String[] rectsPos = rects.split(" "); //

			float height[] = new float[3];
			float width = 0;

			for (int i = 0; i < rectsPos.length; i++) {
				String[] namePos = rectsPos[i].split(",");
				height[i] = Float.valueOf(namePos[3])
						- Float.valueOf(namePos[1]);
				width = Float.valueOf(namePos[2]) - Float.valueOf(namePos[0]);
			}
			float x = Float.valueOf(pos[0]);
			float y = graph_height - Float.valueOf(pos[1]);

			float h = 0;
			for (int i = 0; i < height.length; i++) {
				h = (h + height[i]);
			}
			// the location is the center of the class
			x = x - width / 2;
			y = y - h / 2;

			this.setPosition(x, y);
			this.setHeight(h);
			this.setWidth(width);
			this.setAttributeHeight((int) height[1]);
			this.setMethodHeight((int) height[2]);
		}
	}

}