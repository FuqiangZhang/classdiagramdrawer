package edu.uta.codeGenerator.component;

import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.uta.codeGenerator.ui.layout.Position;


public abstract class DCDRelationship extends DCDComponent {
	
	public DCDRelationship(){}

	private DCDClass source;
	private DCDClass destination;
	private String type;
	private ArrayList<Position> path;

	protected ArrayList<Position> pointOnPath = new ArrayList<Position>();

	public DCDClass getSource() {
		return source;
	}

	public void setSource(DCDClass source) {
		this.source = source;
	}

	public DCDClass getDestination() {
		return destination;
	}

	public void setDestination(DCDClass destination) {
		this.destination = destination;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void draw(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) g;
		
		pointOnPath.remove(0);
		pointOnPath.remove(pointOnPath.size() - 1);

		pointOnPath.add(0, new Position(source.getxPos() + source.getWidth()
				/ 2, source.getyPos() + source.getHeight() / 2));
		pointOnPath.add(new Position(destination.getxPos()
				+ destination.getWidth() / 2, destination.getyPos()
				+ destination.getHeight() / 2));
		// if there are 5 points on the path then :
		// the middle three points draw the curve
		// first two draw the line
		// last two draw the line
		if (pointOnPath.size() == 5) {
			// first two points draw the line
			g2d.draw(new Line2D.Double(pointOnPath.get(0).getX(), pointOnPath
					.get(0).getY(), pointOnPath.get(1).getX(), pointOnPath.get(
					1).getY()));
			// middle three draw the curve
			g2d.draw(new QuadCurve2D.Double(pointOnPath.get(1).getX(),
					pointOnPath.get(1).getY(), pointOnPath.get(2).getX(),
					pointOnPath.get(2).getY(), pointOnPath.get(3).getX(),
					pointOnPath.get(3).getY()));
			// last two points draw the line
			g2d.draw(new Line2D.Double(pointOnPath.get(3).getX(), pointOnPath
					.get(3).getY(), pointOnPath.get(4).getX(), pointOnPath.get(
					4).getY()));
		}
		// draw the curve
		if (pointOnPath.size() == 8) {
			g2d.draw(new CubicCurve2D.Double(pointOnPath.get(0).getX(),
					pointOnPath.get(0).getY(), pointOnPath.get(1).getX(),
					pointOnPath.get(1).getY(), pointOnPath.get(2).getX(),
					pointOnPath.get(2).getY(), pointOnPath.get(3).getX(),
					pointOnPath.get(3).getY()));
			g2d.draw(new CubicCurve2D.Double(pointOnPath.get(3).getX(),
					pointOnPath.get(3).getY(), pointOnPath.get(4).getX(),
					pointOnPath.get(4).getY(), pointOnPath.get(5).getX(),
					pointOnPath.get(5).getY(), pointOnPath.get(6).getX(),
					pointOnPath.get(6).getY()));
			g2d.draw(new Line2D.Double(pointOnPath.get(6).getX(), pointOnPath
					.get(6).getY(), pointOnPath.get(7).getX(), pointOnPath.get(
					7).getY()));
		}

		source.draw(g2d);
		destination.draw(g2d);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		DCDRelationship other = (DCDRelationship) obj;
		if (this.getSource().equals(other.getSource())
				&& this.getDestination().equals(other.getDestination())
				&& this.getType().equals(other.getType())) {
			return true;
		}
		return false;
	}

	@Override
	public void setName(String name) {
		// TODO DCD: implement DCDRelationship setName
	}

	public ArrayList<Position> getPath() {
		return path;
	}

	public void setPath(ArrayList<Position> path) {
		this.path = path;
	}

	public ArrayList<Position> getPointOnPath() {
		return pointOnPath;
	}

	public void setPointOnPath(ArrayList<Position> pointOnPath) {
		this.pointOnPath = pointOnPath;
	}

	@Override
	public String toDOT() {
		String source = getSource().getId();
		String dest = getDestination().getId();
		String result = source + "->" + dest;
		result = result + "[id=\""+ this.getId() + "\"]";
		System.out.println(result);
		return result ;
	}

	@Override
	public void parseDOT(String dot) {
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
		
		//OID8 -> OID7	 [id="8call",
		//pos="e,487.87,213.42 439.81,264.93 452.37,251.47 467.09,235.68 480.81,220.99"];
		//2687392 -> 5532338\s*\[id="19346501Inheritance",\s*pos="e,([^\]]*)"\];
		//	22089338 -> 2687392	 [id="6154579Inheritance",
		//pos="e,462.71,522.71 491.23,559.21 484.02,549.98 476.3,540.11 468.87,530.59"];
		String relationshipREX = source.getId() + " -> " + destination.getId()
				+ "\\s*\\[id=\""+this.getId()+"\",\\s*pos=\"e,([^\\]]*)\"\\];";
		
		System.out.println(relationshipREX);
		//OID8 -> OID6\s*\[id="9call",//s*pos="e,([^\]]*)"\];
		Pattern patternRelation = Pattern.compile(relationshipREX);
		Matcher matcherRelationship = patternRelation.matcher(dot);

		while (matcherRelationship.find()) {
			String pos = matcherRelationship.group(1); // positions of the
														// relationship
														// 257.03,67.956
														// 338.98,115.55
														// 316.18,102.31
														// 289.59,86.868
														// 265.94,73.13;

			this.getPointOnPath().clear();
			// add the point to the path
			// the first location in the string is the end point in the path
			String[] poses = pos.split(" ");
			for (int i = 1; i < poses.length; i++) {
				String p = poses[i];
				float x = Float.valueOf(p.split(",")[0]);
				float y = graph_height - Float.valueOf(p.split(",")[1]);
				Position positionFloat = new Position(x, y);
				getPointOnPath().add(positionFloat);
			}
			String p = poses[0];
			float x = Float.valueOf(p.split(",")[0]);
			// the y axis must be reversed.
			float y = graph_height - Float.valueOf(p.split(",")[1]);
			Position positionFloat = new Position(x, y);
			getPointOnPath().add(positionFloat);
		}

	}
	
}
