package edu.uta.codeGenerator.component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import edu.uta.codeGenerator.ui.layout.CrossPoint;
import edu.uta.codeGenerator.ui.layout.Position;


public class DCDCall extends DCDRelationship{

	public DCDCall(){}
	@Override
	public void draw(Graphics2D g) {
        Graphics2D   g2d   =   (Graphics2D)g;     
        Stroke   st   =   g2d.getStroke();     
        Stroke   bs;     
        //LINE_TYPE_DASHED     
        bs =new BasicStroke(1,BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,0, new float[]{4,4},0);     
        g2d.setStroke(bs); 
		super.draw(g);
        g2d.setStroke(st);  
//		
		Position p1 = pointOnPath.get(pointOnPath.size() - 1);
		Position p2 = pointOnPath.get(pointOnPath.size() - 2);
		
		Line2D line = new Line2D.Double(new Point2D.Double(p1.getX(), p1.getY()),new Point2D.Double(p2.getX(),p2.getY()));
		Rectangle2D rect = new Rectangle2D.Double(this.getDestination().getxPos(), this.getDestination().getyPos(),this.getDestination().getWidth(),this.getDestination().getHeight());
		
		Point2D crossPoint = CrossPoint.getCrossPoint(line, rect);
		CrossPoint.CGLine lineMe = new CrossPoint.CGLine(new CrossPoint.CGPoint(p1.getX(), p1.getY()), new CrossPoint.CGPoint(p2.getX(), p2.getY()));


		if(crossPoint == null){
			return;
		}
		double rot_X = crossPoint.getX();
		double rot_Y = crossPoint.getY();
        
        Shape ponlygon1 = new Path2D.Double();
		int x = (int)rot_X;
		int y = (int) rot_Y;
		((Path2D)ponlygon1).moveTo(x - 5,(int) (y +Math.abs(p2.getY()-p1.getY())/(p2.getY()-p1.getY())*8));
		((Path2D)ponlygon1).lineTo(x, y);
		((Path2D)ponlygon1).lineTo(x + 5, (int) (y +Math.abs(p2.getY()-p1.getY())/(p2.getY()-p1.getY())*8));
		
		if(lineMe.iskExists()){
			double r = ((p2.getY()-p1.getY())) * (p2.getY()-p1.getY()) + ((p2.getX()-p1.getX())) * (p2.getX()-p1.getX());
			r = Math.sqrt(r);
			double ang = -Math.abs(p2.getY()-p1.getY())/(p2.getY()-p1.getY())*Math.asin((p2.getX()-p1.getX())/r);
			
			AffineTransform trans = new AffineTransform();
			trans.rotate(ang, rot_X, rot_Y);
			 ponlygon1 = trans.createTransformedShape(ponlygon1);
		}
		
		g2d.draw(ponlygon1);
	}
}
