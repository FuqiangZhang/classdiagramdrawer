package edu.uta.codeGenerator.ui.layout;


import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class CrossPoint {
	
	
	public static class CGLine {  
	    private boolean kExists;     
	  
	    public double k = 77885.201314f;  
	    public double b = 13145.207788f;  
	    public double extraX = 52077.881314f;  
	      
	      
	    /** 
	     * ���ǵ� k ����ʱ�Ĺ��췽��~ 
	     * @param k 
	     * @param b 
	     */  
	    public CGLine(double k, double b) {  
	        this.kExists = true;  
	        this.k = k;  
	        this.b = b;  
	    }  
	      
	    /** 
	     * ��֪���㣬��ֱ�ߵķ���~ 
	     * @param p1 
	     * @param p2 
	     */  
	    public CGLine(CGPoint p1, CGPoint p2) {  
	        if((p1.x - p2.x) != 0) {  
	            this.kExists = true;  
	            this.k = (p1.y - p2.y)/(p1.x - p2.x);  
	            this.b = (p1.y - p1.x * k);  
	        } else {  
	            // ����߽������֧����ʾֱ�ߴ�ֱ��x�ᣬб�ʲ����ڣ�����k��Ĭ��ֵ~  
	            this.kExists = false;  
	            this.extraX = p1.x;  
	        }  
	    }  
	      
	    /** 
	     * ��бʽ~ 
	     * @param p ĳ�� 
	     * @param k ���õ��ֱ�ߵ�б�� 
	     */  
	    public CGLine(double k, CGPoint p) {  
	        /** 
	         * (y-y') = k*(x-x') 
	         * ���γ�б��ʽΪ�� 
	         * y = k*x + y' - k*x' 
	         * k = k, b = y'-k*x' 
	         */  
	        this.kExists = true;  
	        this.k = k;  
	        this.b = p.y - k * p.x;  
	    }  
	      
	    /** 
	     * ���ǵ� k ������ʱ�Ĺ��췽��~ 
	     * @param extraX 
	     */  
	    public CGLine(double extraX) {  
	        this.kExists = false;  
	        this.extraX = extraX;  
	    }  
	      
	    @Override  
	    public String toString() {  
	        return "Line.toString() y = k*x + b, k=" + this.k +   
	                ", b=" + this.b +   
	                ", kExists=" + this.kExists +   
	                ", extraX=" + this.extraX;  
	    }  
	      
	    public boolean iskExists() {  
	        return kExists;  
	    }  
	    public void setkExists(boolean kExists) {  
	        this.kExists = kExists;  
	    }

		public double getK() {
			return k;
		}

		public void setK(double k) {
			this.k = k;
		}

		public double getB() {
			return b;
		}

		public void setB(double b) {
			this.b = b;
		}

		public double getExtraX() {
			return extraX;
		}

		public void setExtraX(double extraX) {
			this.extraX = extraX;
		}  
		
		
	}  
	
	public static void main(String args[]){
		CrossPoint c = new CrossPoint();
	}
	
	
	public static class CGPoint {  
		    public double x;  
		    public double y;  
		      
		    public CGPoint() {  
		          
		    }  
		    public CGPoint(double x, double y) {  
		        this.x = x;  
		        this.y = y;  
		    }  
		      
		    @Override  
		    public String toString() {  
		        return "x=" + this.x + ", y=" + this.y;  
		    }  
		}  
	
    private static CGPoint getCrossPoint(CGLine l1, CGLine l2) {  
        double x, y;  
        if(l1.iskExists() && l2.iskExists()) {  
            x = (l2.b - l1.b) / (l1.k - l2.k);  
            y = l1.k * x + l1.b;  
        } else if(!l1.iskExists() && l2.iskExists()) {  
            x = l1.extraX;  
            y = l2.k * x + l2.b;  
        } else if(l1.iskExists() && !l2.iskExists()) {  
            x = l2.extraX;  
            y = l1.k * x + l1.b;  
        } else {  
            x = 0;  
            y = 0;  
        }  
        return new CGPoint(x, y);  
    }  
    
    public static Point2D getCrossPoint(Line2D line, Rectangle2D re) {

		CGPoint leftUp = new CGPoint(re.getMinX(), re.getMinY());
		CGPoint rightUp = new CGPoint(re.getMaxX(), re.getMinY());
		CGPoint leftDown = new CGPoint(re.getMinX(), re.getMaxY());
		CGPoint rightDown = new CGPoint(re.getMaxX(), re.getMaxY());

		CGLine line_up = new CrossPoint.CGLine(leftUp, rightUp);
		CGLine line_down = new CGLine(leftDown, rightDown);
		CGLine line_left = new CGLine(leftUp, leftDown);
		CGLine line_right = new CGLine(rightUp, rightDown);

		CGLine cgline = new CGLine(new CGPoint(line.getX1(), line.getY1()),
				new CGPoint(line.getX2(), line.getY2()));

		CGPoint p1 = CrossPoint.getCrossPoint(cgline, line_up);
		CGPoint p2 = CrossPoint.getCrossPoint(cgline, line_down);
		CGPoint p3 = CrossPoint.getCrossPoint(cgline, line_left);
		CGPoint p4 = CrossPoint.getCrossPoint(cgline, line_right);

		CGPoint[] allPoints = new CGPoint[4];
		allPoints[0] = p1;
		allPoints[1] = p2;
		allPoints[2] = p3;
		allPoints[3] = p4;

		for (CGPoint p : allPoints) {
//			if ((p.x >= line.getX1() && p.x <= line.getX2())
//					||
//				( p.x>= line.getX2() && p.x<= line.getX1())
//				) {
//			return new Point2D.Double(p.x, p.y);
//			}
			if ((p.x > line.getX1() && p.x > line.getX2()) || (p.x < line.getX1() && p.x < line.getX2())) {
				continue;
			} 
			if ((p.y > p1.y && p.y > p2.y) || (p.y < p1.y && p.y < p2.y)) {
				continue;
			}
			return new Point2D.Double(p.x, p.y);
			
		}

		return null;

	}
}

