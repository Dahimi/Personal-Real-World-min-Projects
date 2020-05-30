package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;


public class AnimationPanel extends JPanel {
	 public static class Point {
		//Couleur du point
		private Color color = Color.red ;
		//Taille
		private int size = 10;
		//position sur l'axe X : initialisé au dehors du conteneur
		private int x = -10;
		//Position sur l'axe Y : initialisé au dehors du conteneur
		private int y = -10;
		//Type de point
		private String type = "ROND";
		/**
		* Constructeur par défaut
		*/
		public Point(){}
		
		public Point(int x, int y, int size, Color color, String type){
		this.size = size;
		this.x = x;
		this.y = y;
		this.color = color;
		this.type = type;
		}
		//****************************************
		// ACCESSEURS
		//****************************************
		public Color getColor() {
		return color;
		}
		public void setColor(Color color) {
		this.color = color;
		}
		public int getSize() {
		return size;
		}
	
		public void setSize(int size) {
		this.size = size;
		}
		public int getX() {
		return x;
		}
		public void setX(int x) {
		this.x = x;
		}
		public int getY() {
		return y;
		}
		public void setY(int y) {
		this.y = y;
		}
		public String getType() {
		return type;
		}
		public void setType(String type) {
		this.type = type;
		}
		}
		
		public static class DrawPanel extends JPanel{
		//Couleur du pointeur
		private Color pointerColor = Color.red ;
		//Forme du pointeur
		private String pointerType = "CIRCLE";
		//Position X du pointeur
		private int posX = -10, oldX = -10;
		//Position Y du pointeur
		private int posY = -10, oldY = -10;
		//pour savoir si on doit dessiner ou non
		
		//Taille du pointeur
		private int pointerSize = 15;
		//Collection de points !
		private ArrayList<Point> points = new ArrayList<Point>();
		/**
		* Constructeur
		*/
		public DrawPanel(){
		this.addMouseListener(new MouseAdapter(){
		public void mousePressed (MouseEvent e){
			Point point = new Point(e.getX() - (pointerSize / 2), e.getY() -
					
					(pointerSize / 2), pointerSize, pointerColor, pointerType);
		points.add (point);
		System.out.println(point.getX() + "  " + point.getY()) ;
		repaint();
		}
		});
		this.addMouseMotionListener(new MouseMotionListener(){
		public void mouseDragged (MouseEvent e) {
	
		points.add (new Point(e.getX() - (pointerSize / 2), e.getY() -
		(pointerSize / 2), pointerSize, pointerColor, pointerType));
		repaint();
		}
		public void mouseMoved (MouseEvent e) {}
		});
		}
		/**
		* Vous la connaissez maintenant, celle-là ;)
		*/
		public void paintComponent(Graphics g) {
		
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		//On parcourt notre collection de points
		for(Point p : this.points)
		{
		//On récupère la couleur
		g.setColor(p.getColor());
		//Selon le type de point
		if(p.getType().equals("SQUARE")){
		g.fillRect(p.getX(), p.getY(), p.getSize(), p.getSize());
		}
		else{
		g.fillOval(p.getX(), p.getY(), p.getSize(), p.getSize());
		}
		
		}	
		
		}
		public void setPointerColor(Color c){
		this.pointerColor = c;
		}
		
		public void setPointerType(String str){
		this.pointerType = str;
		}
		}

}
