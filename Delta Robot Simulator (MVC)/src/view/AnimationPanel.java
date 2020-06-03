package view;

import java.awt.Color; 
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.*;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import robotModel.RobotModelImp.Point;
import robotModel.*;
public class AnimationPanel extends JPanel {		
		public static class DrawPanel extends JPanel{
		private LinkedList<Point> points = new LinkedList<Point>();
		public void paintComponent(Graphics g) {		
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		for(Point p : this.points)
		{
		g.setColor(p.getColor());
		if(p.getType().equals("SQUARE")){
		g.fillRect(p.getX(), p.getY(), p.getSize(), p.getSize());
		}
		else{
		g.fillOval(p.getX(), p.getY(), p.getSize(), p.getSize());
		}
		}			
		}		
		public void repaint(LinkedList<Point> points ) {
			this.points = points ;
			this.repaint();
		}
		}
}
