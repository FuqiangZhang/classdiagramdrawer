package edu.uta.codeGenerator.component;

import java.awt.Graphics2D;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.uta.codeGenerator.ui.layout.GraphViz;


public class DesignClassDiagram extends DCDComponent {

	public void addChild(DCDComponent child) {
		childList.put(child.getId(), child);
	}


	public void draw(Graphics2D graphic) {
		Graphics2D g2d = (Graphics2D) graphic;// ������g���ʹ�Graphicsת����Graphics2D
		this.drawBlock(graphic, this.getWidth(), this.getHeight(), 0, 0);
		this.setPosition(0, 0);
		for (DCDComponent child : childList.values()) {
			child.draw(g2d);
		}
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
					return e;
				}
			}
			return null;
		}
		return null;
	}

	public DCDComponent getComponentbyID(String id) {
		return this.childList.get(id);
	}

	@Override
	public void setName(String name) {
	}

	@Override
	public String toDOT() {
		GraphViz gv = new GraphViz();
		gv.addln(gv.start_graph());
		gv.addln("node[shape=record]");
		for (DCDComponent cla : getChild().values()) {
			gv.addln(cla.toDOT());
		}
		gv.addln(gv.end_graph());
		System.out.println(gv.getDotSource());
		return gv.getDotSource();
	}

	@Override
	public void parseDOT(String dot) {
		//[bb="0,0,1409.5,372"];
		String graphREX = "graph\\s*\\[bb=\"0,0,(\\S*),(\\S*)\"\\]";
		Pattern patternGraph = Pattern.compile(graphREX);
		Matcher matcherGraph = patternGraph.matcher(dot);
		float graph_height = 0;
		float graph_width = 0;
		// get the height and width of the diagram.
		if (matcherGraph.find()) {
			graph_height = Float.valueOf(matcherGraph.group(2)) + 150;
			graph_width = Float.valueOf(matcherGraph.group(1)) + 150;
		}
		this.width = graph_width;
		this.height = graph_height;
		for (DCDComponent child : getChild().values()) {
			child.parseDOT(dot);
		}
	}

}