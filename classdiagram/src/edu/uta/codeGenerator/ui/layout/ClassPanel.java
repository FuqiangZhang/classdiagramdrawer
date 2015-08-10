package edu.uta.codeGenerator.ui.layout;
import javax.swing.*;

import edu.uta.codeGenerator.component.DesignClassDiagram;
import edu.uta.codeGenerator.state.EditDiagramController;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class ClassPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = -3676947599558001626L;

	private EditDiagramController editDiagramController;
	boolean dragFlag;
	boolean resizeFlag_Y;
	boolean resizeFlag_X;
	
	private DesignClassDiagram dcd;
	
	public void setClassDiagram(DesignClassDiagram dcd){
		this.dcd = dcd;
	}
	
	public ClassPanel() {
		this.addMouseListener(this);
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				editDiagramController.mouseDragged(new Point(e.getX(), e.getY()));
				
				dragFlag = true;
				if (resizeFlag_Y) {
					ClassPanel.this.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
					Point po = e.getPoint();
					Dimension di = ClassPanel.this.getPreferredSize();
					dcd.setHeight(ClassPanel.this.getY() + po.y);
					
					ClassPanel.this.revalidate();
					ClassPanel.this.repaint();
				}
				if(resizeFlag_X){
					ClassPanel.this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
					Point po = e.getPoint();
					dcd.setWidth(ClassPanel.this.getX() + po.x);
					
					ClassPanel.this.revalidate();
					ClassPanel.this.repaint();
				}
				
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				editDiagramController.mouseMoved(new Point(e.getX(), e.getY()));
				
				
				Point point = e.getPoint();
				ClassPanel.this.setCursor(Cursor.getDefaultCursor());
				resizeFlag_Y = false;
				resizeFlag_X = false;
				dragFlag = false;
				if (((ClassPanel.this.getY() + ClassPanel.this.getHeight() - point.y) < 16)
						&& ((ClassPanel.this.getY() + ClassPanel.this.getHeight() - point.y) > -16)) {
					ClassPanel.this.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
					resizeFlag_Y = true;
				} else {
					if(((ClassPanel.this.getX() + ClassPanel.this.getWidth() - point.x) < 50)
							&& ((ClassPanel.this.getX() + ClassPanel.this.getWidth() - point.x) > -50)){
						ClassPanel.this.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
						resizeFlag_X = true;
					}else{
						ClassPanel.this.setCursor(Cursor.getDefaultCursor());
					}
					
				}
			}

		});
		editDiagramController = new EditDiagramController();
	}

	static boolean flag = false;
	
	public static void setDraw(boolean flagz){
		flag = flagz;
	}
	
	public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			dcd.draw((Graphics2D) g);
			this.setPreferredSize(new Dimension((int) dcd.getWidth(), (int) dcd.getHeight()));
			setBounds(40, 10, (int) dcd.getWidth(), (int) dcd.getHeight());
			this.getParent().setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth(),(int)this.getPreferredSize().getHeight()+40));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == 3) {
			editDiagramController.selectBtnClicked();
			
		}
		if (e.getButton() == 1) {
			editDiagramController.mouseClicked(new Point(e.getX(), e.getY()));
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		editDiagramController.mousePressed(new Point(e.getX(), e.getY()));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		editDiagramController.mouseReleased(new Point(e.getX(), e.getY()));
		this.setCursor(Cursor.getDefaultCursor());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.setCursor(Cursor.getDefaultCursor());
	}
}