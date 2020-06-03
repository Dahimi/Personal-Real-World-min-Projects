package robotModel;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.util.concurrent.Phaser;

import javax.swing.SwingUtilities;

import controller.RobotControllerImp;
import robotData.ExternalInteraction;
import robotData.RobotType;
import robotModel.RobotModelImp.Point;
import robotData.*;
import view.DialogBoxView;

public class RobotModelImp implements RobotModel{
	private List<AnimationListener> animationListeners ;
	private List<ErrorListener> errorListeners ;
	private Robot robot = new Robot();
	private boolean importFromFile = false ;
	private boolean exportArduino ;
	private boolean exportToFile ;
	private boolean isRecording = false ;
	private String format ;
	private String exportFile , importFile ;
	private LinkedList<Point> points = new LinkedList<Point>();
	private Color pointerColor = Color.red ;
	private String pointerType = "CIRCLE";
	private int pointerSize = 15;
	private volatile boolean stopSimu = true , initializeSimu = false;
	private Phaser phaser = new Phaser(2);
	private Runnable simulationTask = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			for(int j = 0 ; j < 10 ; j++) {
				System.out.println("New process " + j + " state ini " + initializeSimu + " stopt" + stopSimu);
		     phaser.arriveAndAwaitAdvance(); 
			LinkedList<Point> printedPoints = new LinkedList<Point>();
			LinkedList<Point> iteratedList = new LinkedList<Point>(points);
				
			for(Point point : iteratedList) {
				if(initializeSimu) {
					points = new LinkedList<Point>();
					notifyAnimationListeners(points);
					System.out.println("initializing th eboard ");
					initializeSimu = false;
					break;
				}
				while(stopSimu) {
					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				printedPoints.add(point);
				notifyAnimationListeners(printedPoints);				
			}
			System.out.println("arrived parties " + phaser.getArrivedParties() + " phase" + phaser.getPhase());
			notifyAnimation();
			}
		}		
			
	};
	public RobotModelImp(RobotControllerImp robotControllerImp) {
		// TODO Auto-generated constructor stub
		animationListeners = new LinkedList<AnimationListener>();
		errorListeners = new LinkedList<ErrorListener>();
		new Thread(simulationTask).start();
	}
	@Override
	public void setRobot() {
		// TODO Auto-generated method stub		
	}
	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		if(isIllegalString(user.getUserName()) || isIllegalString(user.getEmail()) || isIllegalString(user.getPassword()))
			throw new IllegalArgumentException("Vous n'avez pas compléter les information sur l'utilisatuer  !");
		robot.setUser(user);
	}
	@Override
	public void setExternal(ExternalInteraction externalInteraction) {
		// TODO Auto-generated method stub
		if( isIllegalString(externalInteraction.getArduinoPort()) &&isIllegalString(externalInteraction.getExportFile() )) throw new IllegalArgumentException("Vous devez spécifié au moins un fichier ou un appareil extérieur  !");
		robot.setExternalInteraction(externalInteraction);
	}

	@Override
	public void setDimension(String base, String nacelle, String bras_sup, String bras_inf) {
		// TODO Auto-generated method stub
		try {
		double baseL = Double.parseDouble(base);
		double nacelleL = Double.parseDouble(nacelle);
		double bras_supL = Double.parseDouble(bras_sup);
		double bras_infL = Double.parseDouble(bras_inf);
		robot.setRobotDimension(new RobotDimension(baseL , nacelleL, bras_supL , bras_infL));
		}catch(Exception e) {
			throw new IllegalArgumentException("Les données sont manquantes ou non compatibles  !");
		}		
	}
	@Override
	public void setType(RobotType robotArticule) {
		// TODO Auto-generated method stub
		if(robotArticule == null) throw new IllegalArgumentException("vous n'avez pas spécifié le type du robot  !");
		robot.setTypeDeRobot(robotArticule);		
	}
	public String robotInfo() {
		return robot.toString();
	}
	private boolean isIllegalString(String str) {
		str = str.trim();
		return str == null ? true : str.length() == 0 ;
	}
	@Override
	public void setImportFromFile(boolean option) {
		// TODO Auto-generated method stub
		importFromFile = option;
	}
	@Override
	public void setExportArduino(boolean option) {
		// TODO Auto-generated method stub
		exportArduino =  option;
	}
	@Override
	public void setExportToFile(boolean option) {
		// TODO Auto-generated method stub
		exportToFile = option ;
	}
	@Override
	public void setFormat(String format) {
		// TODO Auto-generated method stub
		this.format = format;		
	}
	@Override
	public void setAnimation(String option) {
		// TODO Auto-generated method stub
		//System.out.println("L'animation est :" + option + "\nLe foramt est : " + format + "\n Importer d'un fichier" + importFromFile + "\nExporter à arduino " + exportArduino + "\nExporter vers fichier" + exportToFile );
		switch(option) {
		case "run" :stopSimu = false;
			if(phaser.getArrivedParties() == 1) {
				//System.out.println("arrived parties " + phaser.getArrivedParties() + " phase" + phaser.getPhase());
				phaser.arrive();
			}					    
			break;
		case "stop" : 			
			stopSimu  = true;
			break;
		case "Initialize" :
			initializeSimu = true ;
			break;
		}
	}
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
		public Point(){}
		
		public Point(int x, int y, int size, Color color, String type){
		this.size = size;
		this.x = x;
		this.y = y;
		this.color = color;
		this.type = type;
		}
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
	public void setPointerColor(Color c){
		this.pointerColor = c;
		}		
		public void setPointerType(String str){
		this.pointerType = str;
		}
	
	public void notifyAnimation() {
		// TODO Auto-generated method stub
		for(AnimationListener listener : animationListeners) listener.enableRun();
		
	}
	@Override
	public void registerAnimationListener(AnimationListener listener) {
		// TODO Auto-generated method stub
		animationListeners.add(listener);
		
	}
	@Override
	public void notifyAnimationListeners(LinkedList<Point> listPoints) {
		// TODO Auto-generated method stub
		for(AnimationListener listener : animationListeners) listener.updateAnimation(listPoints );
		
	}
	@Override
	public void deleteAnimationListener() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void registerErrorListener(ErrorListener listener) {
		// TODO Auto-generated method stub
		errorListeners.add(listener);
	}
	@Override
	public void notifyErrorListeners(String errorMessage) {
		// TODO Auto-generated method stub
		for(ErrorListener listener : errorListeners) listener.throwError(errorMessage);
		
	}
	@Override
	public void deleteErrorListener() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if(isRecording) {
		Point point = new Point(e.getX() - (pointerSize / 2), e.getY() -(pointerSize / 2), pointerSize, pointerColor, pointerType);
		points.add (point);
		notifyAnimationListeners(this.points);
		}
	}
	@Override
	public void mouseExited(MouseEvent mouseEvent) {
		// TODO Auto-generated method stub
		if(isRecording) {
		notifyErrorListeners("Mouvement impossible : Vous avez dépasser l'espace de travail !!");		
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(isRecording) {
		Point point = new Point(e.getX() - (pointerSize / 2), e.getY() -(pointerSize / 2), pointerSize, pointerColor, pointerType);
		points.add (point);
		notifyAnimationListeners(this.points);
		}
		}
	public void setRecording(boolean isRecording) {
		this.isRecording = isRecording;
	}
	
}
